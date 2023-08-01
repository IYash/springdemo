package org.example.interceptor;

import com.google.common.collect.Sets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.AlterView;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cl.h
 * @since 2023/7/31
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
@Component
public class AuditInterceptor1 implements Interceptor {

    private static final String CREATED_BY = "created_by";
    private static final String UPDATED_BY = "updated_by";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String processSql = ExecutorPluginUtils.getSqlByInvocation(invocation);
        log.debug("schema替换前：{}", processSql);
        // 执行自定义修改sql操作
        // 获取sql
        String sql2Reset = processSql;
        Statements statements = CCJSqlParserUtil.parseStatements(processSql);
        for (Statement statement : statements.getStatements()) {
            try {
                if (statement instanceof Update) {
                    Update updateStatement = (Update) statement;
                    Table table = updateStatement.getTables().get(0);
                    if (table != null) {
                        List<Column> columns = updateStatement.getColumns();
                        List<String> columnNames = columns.stream().map(column -> column.getColumnName()).collect(Collectors.toList());
                        List<Expression> expressions = updateStatement.getExpressions();
                        updateStatement.accept(new StatementVisitorAdapter(){
                            @Override
                            @SneakyThrows
                            public void visit(Update update) {
                                if (!columnNames.contains(UPDATED_BY)) {
                                    columns.add(new Column(UPDATED_BY));
                                    expressions.add(CCJSqlParserUtil.parseExpression("\'UPDATED_BY\'"));
                                }
                            }
                        });
                    }
                }
                if (statement instanceof Insert) {
                    Insert insertStatement = (Insert) statement;
                    List<Column> columns = insertStatement.getColumns();
                    List<String> columnNames = columns.stream().map(column -> column.getColumnName()).collect(Collectors.toList());
                    Set<String> adds = Sets.newHashSet();
                    insertStatement.getItemsList().accept(new ItemsListVisitor() {
                        @Override
                        public void visit(SubSelect subSelect) {
                            throw new UnsupportedOperationException("Not supported yet.");
                        }

                        @SneakyThrows
                        @Override
                        public void visit(ExpressionList expressionList) {
                            if (!columnNames.contains(CREATED_BY)) {
                                columns.add(new Column(CREATED_BY));
                                expressionList.getExpressions().add(CCJSqlParserUtil.parseExpression("\'CREATED_BY\'"));
                            }
                            if (!columnNames.contains(UPDATED_BY)) {
                                columns.add(new Column(UPDATED_BY));
                                expressionList.getExpressions().add(CCJSqlParserUtil.parseExpression("\'CREATED_BY\'"));
                            }
                        }

                        @SneakyThrows
                        @Override
                        public void visit(MultiExpressionList multiExpressionList) {
                            for (ExpressionList expressionList : multiExpressionList.getExprList()) {
                                if (!columnNames.contains(CREATED_BY)) {
                                    if (adds.add(CREATED_BY)) {
                                        columns.add(new Column(CREATED_BY));
                                    }
                                    expressionList.getExpressions().add(CCJSqlParserUtil.parseExpression("\'CREATED_BY\'"));
                                }
                                if (!columnNames.contains(UPDATED_BY)) {
                                    if (adds.add(UPDATED_BY)) {
                                        columns.add(new Column(UPDATED_BY));
                                    }
                                    expressionList.getExpressions().add(CCJSqlParserUtil.parseExpression("\'CREATED_BY\'"));
                                }
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sql2Reset = statements.toString();
        log.info("schema替换后：{}", sql2Reset);
        // 替换sql
        ExecutorPluginUtils.resetSql2Invocation(invocation, sql2Reset);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}

package org.example.interceptor;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.insert.Insert;
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
//@Component
public class AuditInterceptor implements Interceptor {

    private static final String CREATED_BY = "created_by";
    private static final String UPDATED_BY = "updated_by";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String processSql = ExecutorPluginUtils.getSqlByInvocation(invocation);
        log.debug("schema替换前：{}", processSql);
        // 执行自定义修改sql操作
        // 获取sql
        String sql2Reset = processSql;
        //忽略sql中包含on conflict的情况
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
                        if (!columnNames.contains("updated_by")) {
                            columns.add(new Column(UPDATED_BY));
                            expressions.add(CCJSqlParserUtil.parseExpression("\'UPDATED_BY\'"));
                        }
                        updateStatement.setColumns(columns);
                        updateStatement.setExpressions(expressions);
                    }
                }
                if (statement instanceof Insert) {
                    Insert insertStatement = (Insert) statement;
                    List<Column> columns = insertStatement.getColumns();
                    List<String> columnNames = columns.stream().map(column -> column.getColumnName()).collect(Collectors.toList());
                    ItemsList itemsList0 = insertStatement.getItemsList();
                    if (itemsList0 instanceof ExpressionList) {
                        ExpressionList itemsList = (ExpressionList) itemsList0;
                        List<Expression> list = new ArrayList<>();
                        list.addAll(itemsList.getExpressions());
                        if (!columnNames.contains("created_by")) {
                            columns.add(new Column(CREATED_BY));
                            list.add(CCJSqlParserUtil.parseExpression("\'CREATED_BY\'"));
                        }
                        if (!columnNames.contains("updated_by")) {
                            columns.add(new Column(UPDATED_BY));
                            list.add(CCJSqlParserUtil.parseExpression("\'CREATED_BY\'"));
                        }
                        itemsList.setExpressions(list);
                        insertStatement.setItemsList(itemsList);
                    } else if (itemsList0 instanceof MultiExpressionList) {
                        MultiExpressionList itemsList = (MultiExpressionList) itemsList0;
                        List<ExpressionList> exL = itemsList.getExprList();
                        Set<String> adds = Sets.newHashSet();
                        for (int i = 0; i < exL.size(); i++) {
                            List<Expression> list = new ArrayList<>();
                            list.addAll(exL.get(i).getExpressions());
                            if (!columnNames.contains("created_by")) {
                                if (adds.add("created_by")) {
                                    columns.add(new Column(CREATED_BY));
                                }
                                list.add(CCJSqlParserUtil.parseExpression("\'CREATED_BY\'"));
                            }
                            if (!columnNames.contains("updated_by")) {
                                if (adds.add("updated_by")) {
                                    columns.add(new Column(UPDATED_BY));
                                }
                                list.add(CCJSqlParserUtil.parseExpression("\'CREATED_BY\'"));
                            }
                            exL.get(i).setExpressions(list);
                        }
                        insertStatement.setItemsList(itemsList);
                    }
                    insertStatement.setColumns(columns);
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

package org.example.interceptor;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Description:
 * @Author: cl.h
 * @Date: 2023/5/25
 */

@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
//@Component
@Slf4j
public class TableNameChangeInterceptor implements Interceptor {

    @Value("#{'${table.include.originTableNames}'.split(',')}")
    List<String> originTableNames ;
    static String suffix = "_copy1";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        boolean needChange = needChange();
        Object proceed = null ;
        try {
            if (!needChange){
                return invocation.proceed();
            }
            Object parameterObject = args[1];//传递的参数
            BoundSql boundSql = ms.getBoundSql(parameterObject);
            String sql = boundSql.getSql();
            String newSql = new String(sql);
            Statements statements = CCJSqlParserUtil.parseStatements(newSql);
            boolean needCopy = false;
            Map<String, String> symbolMap = Maps.newHashMap();
            int start = 1000;
            for(Statement statement:statements.getStatements()) {
                List<String> tableList = new TablesNamesFinder().getTableList(statement);
                Collections.sort(tableList, Comparator.comparingInt(String::length).reversed());
                for (String tableName : tableList) {
                    start = start+1;
                    if (includeTable(tableName)){
                        needCopy = true;
                        //用其他符号替代table
                        newSql = replaceTableName(newSql,tableName,symbolMap,start);
                    }
                }
            }
            //Statement statement = CCJSqlParserUtil.parse(newSql);
            if (needCopy) {
                //将其他符号用table替换回来
                for(Map.Entry<String, String> entry:symbolMap.entrySet()){
                    newSql = newSql.replaceAll(String.valueOf(entry.getKey()),entry.getValue());
                }
                BoundSql bs = new BoundSql(ms.getConfiguration(), newSql, boundSql.getParameterMappings(), parameterObject);
                for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
                    String property = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(property)) {
                        bs.setAdditionalParameter(property, boundSql.getAdditionalParameter(property));
                    }
                }
                MappedStatement newMs = copyMappedStatement(ms, new BoundSqlSqlSource(bs));
                //赋回给实际执行方法所需的参数中
                args[0] = newMs;
            }
            proceed = invocation.proceed();
            SqlCommandType sqlCommandType = ms.getSqlCommandType();
            if (sqlCommandType == SqlCommandType.SELECT) {
                if (Objects.nonNull(proceed) && proceed instanceof List && !CollectionUtils.isEmpty((List) proceed)) {
                    return proceed;
                } else {
                    // 回归原表查询
                    if (needChange && needOrigin()) {
                        args[0] = ms;
                        proceed = invocation.proceed();
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
        String str = (String)properties.get("originTableNameStr");
        originTableNames = Arrays.asList(str.split(","));
    }

    private boolean needChange() {
        ChangeConditionEnum condition = ChangeConditionHolder.getCondition();
        return condition.needChange();
    }

    private boolean needOrigin() {
        ChangeConditionEnum condition = ChangeConditionHolder.getCondition();
        return condition.needOrigin();
    }

    /***
     * 复制一个新的MappedStatement
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement copyMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(String.join(",", ms.getKeyProperties()));
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    /**
     * MappedStatement构造器接受的是SqlSource
     * 实现SqlSource接口，将BoundSql封装进去
     */
    public static class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    private String replaceTableName(String old, String table, Map<String, String> symbolMap, int start) {

        StringBuilder sb = new StringBuilder();
        int pos = 0;
        int index = old.indexOf(table);
        String newPattern = table + suffix;
        int patLen = table.length();
        String wrapStart = wrapStart(start);
        symbolMap.put(wrapStart, newPattern);
        while (index >= 0) {
            sb.append(old.substring(pos, index));
            sb.append(wrapStart);
            pos = index + patLen;
            index = old.indexOf(table, pos);
        }
        sb.append(old.substring(pos));
        old = sb.toString();
        return old;
    }
    private String wrapStart(Integer start){
        return String.format("xx%sxx",start);
    }
    private boolean includeTable(String table){
        return originTableNames.contains(table.toLowerCase());
    }
}

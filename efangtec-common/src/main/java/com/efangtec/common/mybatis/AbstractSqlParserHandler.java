package com.efangtec.common.mybatis;

import com.efangtec.common.mybatis.parser.ISqlParser;
import com.efangtec.common.mybatis.parser.ISqlParserFilter;
import com.efangtec.common.mybatis.parser.SqlInfo;
import com.efangtec.common.mybatis.parser.SqlParserHelper;
import com.efangtec.common.mybatis.utils.PluginUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.List;

/**
 * Created by Administrator on 2019-02-28.
 */
@Data
@Accessors(chain = true)
public abstract class AbstractSqlParserHandler {

    private List<ISqlParser> sqlParserList;
    private ISqlParserFilter sqlParserFilter;

    /**
     * 拦截 SQL 解析执行
     */
    protected void sqlParser(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        StatementHandler statementHandler = PluginUtils.realTarget(originalObject);
        metaObject = SystemMetaObject.forObject(statementHandler);
        if (null != metaObject) {
            if (null != this.sqlParserFilter && this.sqlParserFilter.doFilter(metaObject)) {
                return;
            }
            // SQL 解析
            if (CollectionUtils.isNotEmpty(this.sqlParserList)) {
                // @SqlParser(filter = true) 跳过该方法解析
                if (SqlParserHelper.getSqlParserInfo(metaObject)) {
                    return;
                }
                // 标记是否修改过 SQL
                int flag = 0;
                String originalSql = (String) metaObject.getValue(PluginUtils.DELEGATE_BOUNDSQL_SQL);
                for (ISqlParser sqlParser : this.sqlParserList) {
                    SqlInfo sqlInfo = sqlParser.parser(metaObject, originalSql);
                    if (null != sqlInfo) {
                        originalSql = sqlInfo.getSql();
                        ++flag;
                    }
                }
                if (flag >= 1) {
                    metaObject.setValue(PluginUtils.DELEGATE_BOUNDSQL_SQL, originalSql);
                }
            }
        }
    }
}


package com.efangtec.common.mybatis.tenant;


public interface TenantSchemaHandler {

    /**
     * 获取 schema 名
     * @return schema 名
     */
    String getTenantSchema();

    /**
     * 根据表名判断是否进行过滤
     * @param tableName 表名
     * @return 是否进行过滤
     */
    boolean doTableFilter(String tableName);
}

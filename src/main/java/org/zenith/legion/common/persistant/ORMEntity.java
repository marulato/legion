package org.zenith.legion.common.persistant;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Column;
import org.zenith.legion.common.persistant.annotation.NotColumn;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class ORMEntity {

    private String tableName;
    private String auditTableName;
    private String whereClause;
    private boolean auditColumns;
    private Map<String, String> fieldColumnMap;
    private Map<String, Boolean> primaryKeys;

    public static ORMEntity getInstance(Class<? extends BasePO> entityClass) {
        return new ORMEntity(entityClass);
    }


    public ORMEntity(Class<? extends BasePO> entityClass) {
        fieldColumnMap = new HashMap<>();
        primaryKeys = new HashMap<>();
        if (entityClass != null) {
            Field[] allFields = entityClass.getDeclaredFields();
            if (entityClass.isAnnotationPresent(Persistant.class)) {
                Persistant persistant = entityClass.getAnnotation(Persistant.class);
                tableName = persistant.tableName().toUpperCase();
                auditTableName = persistant.auditTableName().toUpperCase();
                auditColumns = persistant.auditColumns();
                whereClause = persistant.whereClause();
                for(Field field : allFields) {
                    field.setAccessible(true);
                    if (!field.isAnnotationPresent(NotColumn.class)) {
                        if (field.isAnnotationPresent(Column.class)) {
                            Column columnAnno = field.getAnnotation(Column.class);
                            fieldColumnMap.put(field.getName(), columnAnno.columnName().toUpperCase());
                        } else {
                            fieldColumnMap.put(field.getName(), NameConvertor.getColumn(field.getName()));
                        }
                    }
                    if (field.isAnnotationPresent(PrimaryKey.class)) {
                        PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
                        if (primaryKey.autoIncrement()) {
                            primaryKeys.put(field.getName(), true);
                        } else {
                            primaryKeys.put(field.getName(), false);
                        }
                    }
                }
            }
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAuditTableName() {
        return auditTableName;
    }

    public void setAuditTableName(String auditTableName) {
        this.auditTableName = auditTableName;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    public boolean isAuditColumns() {
        return auditColumns;
    }

    public void setAuditColumns(boolean auditColumns) {
        this.auditColumns = auditColumns;
    }

    public Map<String, String> getFieldColumnMap() {
        return fieldColumnMap;
    }

    public void setFieldColumnMap(Map<String, String> fieldColumnMap) {
        this.fieldColumnMap = fieldColumnMap;
    }

    public Map<String, Boolean> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(Map<String, Boolean> primaryKeys){
        this.primaryKeys = primaryKeys;
    }
}

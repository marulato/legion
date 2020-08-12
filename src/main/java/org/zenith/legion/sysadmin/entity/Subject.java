package org.zenith.legion.sysadmin.entity;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

@Persistant(tableName = "MC_SUBJECT")
public class Subject extends BasePO {

    @PrimaryKey(autoIncrement = false)
    private String subjectId;
    private String level1Id;
    private String level2Id;
    private String name;
    private String description;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getLevel1Id() {
        return level1Id;
    }

    public void setLevel1Id(String level1Id) {
        this.level1Id = level1Id;
    }

    public String getLevel2Id() {
        return level2Id;
    }

    public void setLevel2Id(String level2Id) {
        this.level2Id = level2Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

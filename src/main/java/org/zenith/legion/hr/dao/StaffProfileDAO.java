package org.zenith.legion.hr.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.zenith.legion.common.persistant.SimpleSQLGenerator;
import org.zenith.legion.hr.entity.Staff;

@Mapper
public interface StaffProfileDAO {

    @InsertProvider(type = SimpleSQLGenerator.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID")
    void createEmployee(Staff staff);

    Staff getStaffById(Long staffId);
}

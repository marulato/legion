package org.zenith.legion.hr.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zenith.legion.hr.entity.Staff;

@Mapper
public interface StaffProfileDAO {

    Staff getStaffById(String staffId);
}

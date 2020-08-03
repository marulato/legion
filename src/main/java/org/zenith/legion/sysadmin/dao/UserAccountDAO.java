package org.zenith.legion.sysadmin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zenith.legion.sysadmin.entity.UserAccount;
import org.zenith.legion.sysadmin.entity.UserRoleAssign;
import java.util.List;

@Mapper
public interface UserAccountDAO {

    UserAccount getUserByIdNo(String idNo);

    List<UserRoleAssign> getUserRoleAssignment(String idNo);

    List<UserRoleAssign> getActiveUserRoleAssignment(String idNo);
}

package org.zenith.legion.sysadmin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zenith.legion.sysadmin.entity.UserAccount;
import org.zenith.legion.sysadmin.entity.UserRole;
import org.zenith.legion.sysadmin.entity.UserRoleAssign;
import java.util.List;

@Mapper
public interface UserAccountDAO {

    UserAccount getUserByStaffNo(String staffNo);

    UserAccount getUserById(Long id);

    List<UserRoleAssign> getUserRoleAssignment(Long userAcctId);

    List<UserRoleAssign> getActiveUserRoleAssignment(Long userAcctId);

    UserRole getRoleById(String roleId);
}

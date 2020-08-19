package org.zenith.legion.sysadmin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zenith.legion.hr.entity.Department;
import org.zenith.legion.hr.entity.Position;
import org.zenith.legion.sysadmin.entity.District;
import org.zenith.legion.sysadmin.entity.MasterCode;
import org.zenith.legion.sysadmin.entity.Config;
import org.zenith.legion.sysadmin.entity.Subject;

import java.util.List;

@Mapper
public interface MasterCodeDAO {

    Config getConfig(String key);

    MasterCode getMasterCode(String type, String value);

    List<MasterCode> getAllMasterCodes();

    List<MasterCode> getMasterCodeByType(String type);

    List<District> getProvinces();

    List<District> getCitiesUnderProvince(Integer provinceId);

    List<District> getCitiesByLevel(Integer level);

    List<District> getCitiesByLevelAndParent(Integer level, Integer parentId);

    District getDistrictById(Integer id);

    List<Department> getAllDepartments();

    List<Position> getAllPositions();


}

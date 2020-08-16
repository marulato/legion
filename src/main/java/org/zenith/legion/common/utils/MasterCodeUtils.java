package org.zenith.legion.common.utils;

import org.zenith.legion.hr.entity.Department;
import org.zenith.legion.hr.entity.Position;
import org.zenith.legion.sysadmin.entity.District;
import org.zenith.legion.sysadmin.entity.MasterCode;
import org.zenith.legion.common.cache.CachePool;
import org.zenith.legion.common.cache.MasterCodeCache;
import org.zenith.legion.sysadmin.dao.MasterCodeDAO;

import java.util.ArrayList;
import java.util.List;

public class MasterCodeUtils {

    private static final MasterCodeCache cache = CachePool.getCache(MasterCodeCache.KEY, MasterCodeCache.class);
    private static final MasterCodeDAO masterCodeDAO = SpringUtils.getBean(MasterCodeDAO.class);

    public static MasterCode getMasterCode(String type, String value) {
        MasterCode masterCode = cache.get(type, value);
        if (masterCode == null) {
            masterCodeDAO.getMasterCode(type, value);
        }
        return masterCode;
    }

    public static List<MasterCode> getMasterCodeByType(String type) {
        if (StringUtils.isNotBlank(type)) {
            return cache.getAllByType(type);
        }
        return new ArrayList<>();
    }

    public static List<District> getProvinces() {
        return masterCodeDAO.getProvinces();
    }

    public static List<District> getCitiesUnderProvince(Integer provinceId) {
        return masterCodeDAO.getCitiesUnderProvince(provinceId);
    }

    public static District getDistrictById(Integer id) {
        return masterCodeDAO.getDistrictById(id);
    }

    public static List<District> getCitiesByLevelAndParent(Integer level, Integer parentId) {
        return masterCodeDAO.getCitiesByLevelAndParent(level, parentId);
    }

    public static List<Department> getAllDepartments() {
        return cache.getAllDepartments();
    }

    public static List<Position> getPositionsByDepartmentId(String deptId) {
        return cache.getPositionsByDepartmentId(deptId);
    }

    public static Department getDepartment(String deptId) {
        return cache.getDepartment(deptId);
    }

    public static Position getPosition(String positionId) {
        return cache.getPosition(positionId);
    }

}

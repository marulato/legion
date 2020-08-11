package org.zenith.legion.sysadmin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zenith.legion.common.base.MasterCode;
import org.zenith.legion.sysadmin.entity.Config;

import java.util.List;

@Mapper
public interface ConfigDAO {

    Config getConfig(String key);

    MasterCode getMasterCode(String type, String value);

    List<MasterCode> getMasterCodeByType(String type);
}

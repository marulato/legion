package org.zenith.legion.sysadmin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zenith.legion.sysadmin.entity.Config;

@Mapper
public interface ConfigDAO {

    Config getConfig(String key);
}

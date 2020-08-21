package org.zenith.legion.general.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.zenith.legion.common.persistant.SimpleSQLGenerator;
import org.zenith.legion.general.entity.FileNet;

@Mapper
public interface FileNetDAO {

    @InsertProvider(type = SimpleSQLGenerator.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID")
    void create(FileNet fileNet);

    FileNet getFileNetById(Long id);
}

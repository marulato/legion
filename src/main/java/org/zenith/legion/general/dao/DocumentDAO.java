package org.zenith.legion.general.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.zenith.legion.common.persistant.SimpleSQLGenerator;
import org.zenith.legion.general.entity.Document;

@Mapper
public interface DocumentDAO {

    @InsertProvider(type = SimpleSQLGenerator.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID")
    Long create(Document document);

    Document getDocumentById(Long id);
}

package org.zenith.legion.general.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.zenith.legion.common.persistant.SimpleSQLGenerator;
import org.zenith.legion.general.entity.FailedEmail;

@Mapper
public interface ExternalEmailDAO {

    @InsertProvider(type = SimpleSQLGenerator.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "failedEmailId", keyColumn = "FAILED_EMAIL_ID")
    void saveFailedEmail(FailedEmail failedEmail);
}

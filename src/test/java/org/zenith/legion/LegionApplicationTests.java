package org.zenith.legion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zenith.legion.common.persistant.SimpleSQLGenerator;
import org.zenith.legion.sysadmin.entity.BatchJob;
import org.zenith.legion.sysadmin.entity.BatchJobStatus;
import org.zenith.legion.sysadmin.service.BatchJobConfigurationService;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class LegionApplicationTests {

    @Test
    void contextLoads() throws Exception {

    }

    public static void main(String[] args) throws Exception {
        SimpleSQLGenerator simpleSQLGenerator = new SimpleSQLGenerator();
        System.out.println(simpleSQLGenerator.insert(new BatchJobStatus()));
    }


}

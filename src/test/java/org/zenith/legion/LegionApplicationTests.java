package org.zenith.legion;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.zenith.legion.common.persistant.SimpleSQLGenerator;
import org.zenith.legion.common.utils.DateUtils;
import org.zenith.legion.common.utils.JsonMapper;
import org.zenith.legion.common.utils.StringUtils;
import org.zenith.legion.common.validation.CommonValidator;
import org.zenith.legion.hr.dto.EmployeeRegistrationDto;
import org.zenith.legion.hr.entity.Department;
import org.zenith.legion.hr.entity.Position;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.*;

@SpringBootTest
class LegionApplicationTests {

    @Test
    void contextLoads() throws Exception {

    }

    public static void main(String[] args) throws Exception {
        EmployeeRegistrationDto dto = new EmployeeRegistrationDto();
        System.out.println(CommonValidator.doValidation(dto, null));
    }

    public static String encryptPassword(String pwd) {
        if (StringUtils.isNotBlank(pwd)) {
            String hash = pwd;
            for (int i = 0; i < 5; i++) {
                hash = DigestUtils.sha256Hex(hash);
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(hash);
        }
        return pwd;
    }

}

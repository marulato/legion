package org.zenith.legion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class LegionApplication {

    public static void main(String[] args) {
        SpringApplication.run(LegionApplication.class, args);
    }

}

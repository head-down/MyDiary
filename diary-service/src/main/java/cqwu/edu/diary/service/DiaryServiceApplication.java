package cqwu.edu.diary.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"cqwu.edu.diary.service.*","cqwu.edu.diary.common.*"})
public class DiaryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiaryServiceApplication.class, args);
    }

}

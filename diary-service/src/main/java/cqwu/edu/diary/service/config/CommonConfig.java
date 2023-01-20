package cqwu.edu.diary.service.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CommonConfig implements InitializingBean {

    @Value("${file.path}")
    private String filePath;

    public static String FILE_PATH;


    @Override
    public void afterPropertiesSet() {

        FILE_PATH = filePath;

    }

}
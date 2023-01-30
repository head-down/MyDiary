package cqwu.edu.diary.service.generator;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class FastGenerator {
    public static void main(String[] args) {

        FastAutoGenerator.create(new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/myDiary", "root", "123456"))
                .globalConfig(builder -> {
                    builder.author("jianghr") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("D:\\program\\MyDiary\\diary-service\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("cqwu.edu.diary") // 设置父包名
                            .moduleName("service") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\program\\MyDiary\\diary-service\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> builder
                        .addInclude("cqwu_user_diary") // 设置需要生成的表名
                        .addTablePrefix("sys_", "cqwu_")
                        .serviceBuilder().enableFileOverride()
                        .entityBuilder().enableFileOverride().enableLombok()
                        .mapperBuilder().enableFileOverride()
                        .controllerBuilder().enableFileOverride()
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}

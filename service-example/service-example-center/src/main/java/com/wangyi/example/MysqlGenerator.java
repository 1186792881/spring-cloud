package com.wangyi.example;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MysqlGenerator {

    public static void main(String[] args) {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = MysqlGenerator.class.getResource("/").getPath().replace("/classes","") + "/mybatis-generator";
        gc.setOutputDir(projectPath);
        gc.setSwagger2(true); // 添加swagger
        gc.setFileOverride(true); // 是否覆盖
        gc.setBaseResultMap(true); // XML ResultMap
        gc.setBaseColumnList(true); // XML columLis
        gc.setDateType(DateType.ONLY_DATE); // 日期类型为 java.util.Date
        gc.setServiceName("%sService");
        gc.setAuthor("wangyi");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://39.105.126.222:3306/service-example?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.wangyi.service.example");
        pc.setEntity("entity");
        pc.setMapper("dao");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setInclude("se_user"); // 需要生成的表, 不传生成所有
        mpg.setStrategy(strategy);

        mpg.execute();
    }

}

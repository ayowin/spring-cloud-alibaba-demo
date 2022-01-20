package com.seangull.dao;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @class: MybatisPlusGenerator
 * @brief: MybatisPlus代码生成器
 * @author: ouyangwei
 * @date: 2021.09.13
 * @desc:
 *      1. 将想要生成代码的表名赋值给tableNames，执行，其他变量可忽略
 *         或按需修。
 *      2. 执行完后，entity和mapper下会生成文件，将xxxMapper.xml移到
 *         resources/mapper下即可。
 *      3. 最后将tableNames赋值为""，以便下次使用。
 */
@SpringBootTest
public class MybatisPlusGenerator {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Test
    public void generate() throws Exception {
        /* 代码作者署名 */
        String author = "ouyangwei";

        /* 要执行代码生成的表，多个表用英文逗号隔开 */
        String tableNames = "product";

        /* 父包名 */
        String parentPackageName = "com.seangull";

        /* 模块包名，""为不需要模块 */
        String modulePackageName = "dao";

        /* controller生成与否 */
        boolean controllerIgnore = true;
        /* controller包名 */
        String controllerPackageName = "controller";
        /* controller文件名规则,""为默认命名 */
        String controllerNameFormat = "";

        /* service生成与否 */
        boolean serviceIgnore = true;
        /* service包名 */
        String servicePackageName = "service";
        /* service文件名规则,""为默认命名 */
        String serviceNameFormat = "%sService";
        /* service implement包名 */
        String serviceImplPackageName = "service.impl";
        /* service implement文件名规则,""为默认命名 */
        String serviceImplNameFormat = "";

        /* entity包名 */
        String entityPackageName = "entity";
        /* entity文件名规则,""为默认命名 */
        String entityNameFormat = "";

        /* mapper包名 */
        String mapperPackageName = "mapper";
        /* mapper文件名规则,""为默认命名 */
        String mapperNameFormat = "";
        /**
         *  mapper xml包名
         *  注意：本项目需要自行将生成的xml文件移动到classpath: mappers文件夹中
         *  即：resources/mappers
         *  对应application.properties或application.yml中mybatis plus的mapper-locations值
         *  原因：生成在src/main/java及其子目录下的所有非*.java文件，都不会参与编译、打包到发
         *      布目录中（即：target目录），推荐将所有的非*.java文件（配置、资源等）放到reso-
         *      urces下，以免导致配置、资源等文件找不到的异常。
         **/
        String mapperXmlPackageName = "mapper";
        /* mapper xml文件名规则,""为默认命名 */
        String mapperXmlNameFormat = "";
        /* 代码生成器 */
        AutoGenerator autoGenerator = new AutoGenerator();
        /* 全局配置 */
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor(author);
        /* 生成完成是否打开输出目录: xxx/src/main/java */
        globalConfig.setOpen(false);
        autoGenerator.setGlobalConfig(globalConfig);
        /**
         *  命名格式配置
         *  命名格式请查官方文档：
         *  https://mp.baomidou.com/config/generator-config.html
         **/
        globalConfig.setEntityName(entityNameFormat);
        globalConfig.setMapperName(mapperNameFormat);
        globalConfig.setXmlName(mapperXmlNameFormat);
        globalConfig.setControllerName(controllerNameFormat);
        globalConfig.setServiceName(serviceNameFormat);
        globalConfig.setServiceImplName(serviceImplNameFormat);
        /* 数据源配置 */
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setDriverName(driverClassName);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        autoGenerator.setDataSource(dataSourceConfig);

        /* 包配置 */
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(parentPackageName);
        packageConfig.setModuleName(modulePackageName);
        packageConfig.setEntity(entityPackageName);
        packageConfig.setMapper(mapperPackageName);
        packageConfig.setXml(mapperXmlPackageName);
        packageConfig.setController(controllerPackageName);
        packageConfig.setService(servicePackageName);
        packageConfig.setServiceImpl(serviceImplPackageName);
        autoGenerator.setPackageInfo(packageConfig);

        /* 模板配置 */
        TemplateConfig templateConfig = new TemplateConfig();
        if (controllerIgnore) {
            templateConfig.setController("");
        }
        if (serviceIgnore) {
            templateConfig.setService("");
            templateConfig.setServiceImpl("");
        }
        autoGenerator.setTemplate(templateConfig);

        /* 策略配置 */
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        /* 是否使用lombok */
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(tableNames.split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        autoGenerator.setStrategy(strategy);

        autoGenerator.execute();
    }

}

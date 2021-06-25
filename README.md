## 使用方法

0. 可以将本项目移入目标工程，如果是多模块工程，新建一个模块；如果是单一模块，直接把 `GeneratorDisplay.java`、`my`、`config`、`generator.properties`、`generatorConfig.xml` 这五个文件或文件夹移过去；

1. 在目标工程中引入 `tk.mybatis` 的依赖：

    ```xml
   <!-- MySQL 驱动  -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>

    <!-- mybatis 逆向生成工具  -->
    <!-- https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-core -->
    <dependency>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-core</artifactId>
        <version>1.3.7</version>
    </dependency>

    <!-- tk.mybatis -->
    <!-- https://mvnrepository.com/artifact/tk.mybatis/mapper-spring-boot-starter -->
    <dependency>
        <groupId>tk.mybatis</groupId>
        <artifactId>mapper</artifactId>
        <version>4.1.5</version>
    </dependency>
   
    <!-- mybatis 分页插件 -->
    <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper-spring-boot-starter</artifactId>
        <version>1.2.12</version>
    </dependency>
    ```
   
2. 目标工程中加入配置，依据实际情况调整:

   ```yaml
   # MySQL 的配置
   spring:
      datasource:
         type: com.zaxxer.hikari.HikariDataSource
         driver-class-name: com.mysql.cj.jdbc.Driver
         url: jdbc:mysql://localhost:3306/archives?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
         username: root
         password: woaiyujie
         hikari:
            minimum-idle: 5
            maximum-pool-size: 5
            auto-commit: true
            pool-name: DateSourceHikariCP
            connection-test-query: SELECT 1
   # tkMyBatis 配置
   mapper:
      mappers: com.shiyan.archives.my.mapper.MyMapper
      identity: MYSQL
   # mybatis的配置
   mybatis:
      type-aliases-package: com.shiyan.archives.pojo
      mapper-locations: classpath:mapper/*.xml
      configuration:
         map-underscore-to-camel-case: true
   # 打印 SQL 语句
   # 如果使用的是 logback
   logging:
      level:
         com.vito.dao接口所在包名: trace/debug
   # 如果使用的是 log4j
   # mybatis:
   #    configuration:
   #       log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
   ```

3. 配置 `generator.properties`。如果是多模块，在 `generator.properties`、`GeneratorDisplay.java` 的路径中加上 `moduleName`;

4. `generatorConfig.xml` - `<table>` 中指定要生成的数据表名称，如果全部生成，`tableName="%"`；

5. 执行 `GeneratorDisplay.java` 的 `main()`，生成代码；

6. 在自己的工程的启动类加入注解：
    
    ```java
    import tk.mybatis.spring.annotation.MapperScan;
    
    //这个路径表示的是 Mapper 接口所在的包路径，注意该注解使用的是 tk.mybatis 包下的注解
    @MapperScan(basePackages = "com.xxx.mapper")
    ```
   

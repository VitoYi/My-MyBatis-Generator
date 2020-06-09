## 使用方法

1. 配置 `generator.properties`；

2. `generatorConfig.xml` - `<table>` 中指定要生成的数据表名称，如果全部生成，`tableName="%"`；

3. 执行 `GeneratorDisplay.java` 的 `main()`，生成代码；

4. 将实体类、mapper、xml 和 `my 文件夹` 拷贝到自己的工程中去；

5. 在自己的工程中引入 `tk.mybatis` 的依赖：

    ```xml
    <dependency>
        <groupId>tk.mybatis</groupId>
        <artifactId>mapper-spring-boot-starter</artifactId>
        <version>2.1.5</version>
    </dependency>
    ```

6. 在自己的工程中配置 MyBatis:

    ```yaml
    # tkMyBatis 配置
    mapper:
      mappers: com.xxx.my.mapper.MyMapper
      identity: MYSQL
    ```
   
7. 在自己的工程的启动类加入注解：
    
    ```java
    import tk.mybatis.spring.annotation.MapperScan;
    
    //这个路径表示的是 Mapper 接口所在的包路径，注意该注解使用的是 tk.mybatis 包下的注解
    @MapperScan(basePackages = "com.vito.mapper”)
    ```
   

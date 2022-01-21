[toc]

# spring-cloud-alibaba-demo
一个 Spring Cloud Alibaba 版本的demo，整个工程为一个多模块maven工程，以下对各模块逐一介绍。



---



## nacos

* 阿里开源工程，官网：[https://nacos.io/zh-cn/index.html](https://nacos.io/zh-cn/index.html)。
* 注册中心、配置中心模块。
* 启动模块，生产时需要独立启动。

### 1.  启动
* 单机启动、集群启动根据需要二选一即可。建议系统负载量非常大时选用集群启动，除此之外选用单机启动即可。

* 单机模式与集群模式切换时，需要注意配置中心的内容，两个模式的配置持久化技术是相互独立的，因此在两个模式切换的时候，需要将配置中心的配置同步更新。

#### 1.1 单机启动
切换到nacos/bin目录下。

##### linux系统  
```shell
startup.sh -m standalone
```
##### windows系统  
```shell
startup.cmd -m standalone
```
#### 1.2 集群启动
* 集群启动依赖mysql，因此需要创建数据库并导入表结构和数据、修改application.properties中的mysql配置、配置cluster.conf。  
* 为所有nacos节点，搭建nginx负载均衡。  

**小有门槛，具体操作可参考以下链接：**  
[听句劝! Nacos集群搭建可以看看这篇教程](https://os.51cto.com/art/202103/649179.htm)  
[同一台机启动多个nacos的问题](https://blog.csdn.net/qq1623803207/article/details/120277919)  

### 2. 管理
* url示例: [http://127.0.0.1:8848/nacos/](http://127.0.0.1:8848/nacos/)，非部署机器和集群模式请替换正确的ip地址和端口号。
* 用户名和密码均为：nacos  

* **以下为demo所有节点在nacos配置中心的配置明细，第一次启动nacos需将所有配置添加到nacos配置列表：**    

    **common.yml**    
    ```yaml
    server:
      port: 78
    ```

    **dao.yml**  
    ```yaml
    spring:
      # 数据库配置：url、账号、密码、连接池等
      datasource:
        username: root
        password: 123456
        url: jdbc:mysql://localhost:3306/spring-cloud-alibaba-demo?serverTimezone=UTC&characterEncoding=UTF-8&useSSL=false
        driver-class-name: com.mysql.jdbc.Driver
        hikari:
          minimum-idle: 5
          maximum-pool-size: 15
          auto-commit: true
          idle-timeout: 30000
          max-lifetime: 1800000
          connection-timeout: 30000
          connection-test-query: select 1
    
      # redis配置：IP地址、端口号、密码、连接池等
      redis:
        host: 127.0.0.1
        port: 6379
        password:
        lettuce:
          pool:
            max-active: 8
            max-wait: -1
            max-idle: 8
            min-idle: 0
    
    # mybatis plus 配置
    mybatis-plus:
      mapper-locations: classpath*:mapper/*.xml
      # 全局配置
      global-config:
        # 0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
        id-type: 0
        # 字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
        field-strategy: 0
        # 驼峰下划线转换
        db-column-underline: true
        # 刷新mapper 调试神器
        refresh-mapper: true
        # 数据库大写下划线转换
        capital-mode: true
      # 自定义SQL注入器
      configuration:
        map-underscore-to-camel-case: true
        cache-enabled: true
    
    # 日志配置
    logging:
      level:
        # 将mapper的日志过滤等级设为debug：即显示debug及以上的日志，对mapper而言，能看到sql语句
        com.seangull.dao.mapper: debug
    
    server:
      port: 79

    ```
    **producer.yml**  
    ```yaml
    spring:
      # 数据库配置：url、账号、密码、连接池等
      datasource:
        username: root
        password: 123456
        url: jdbc:mysql://localhost:3306/spring-cloud-alibaba-demo?serverTimezone=UTC&characterEncoding=UTF-8&useSSL=false
        driver-class-name: com.mysql.jdbc.Driver
        hikari:
          minimum-idle: 5
          maximum-pool-size: 15
          auto-commit: true
          idle-timeout: 30000
          max-lifetime: 1800000
          connection-timeout: 30000
          connection-test-query: select 1
    
      # redis配置：IP地址、端口号、密码、连接池等
      redis:
        host: 127.0.0.1
        port: 6379
        password:
        lettuce:
          pool:
            max-active: 8
            max-wait: -1
            max-idle: 8
            min-idle: 0
    
    # mybatis plus 配置
    mybatis-plus:
      mapper-locations: classpath*:mapper/*.xml
      # 全局配置
      global-config:
        # 0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
        id-type: 0
        # 字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
        field-strategy: 0
        # 驼峰下划线转换
        db-column-underline: true
        # 刷新mapper 调试神器
        refresh-mapper: true
        # 数据库大写下划线转换
        capital-mode: true
      # 自定义SQL注入器
      configuration:
        map-underscore-to-camel-case: true
        cache-enabled: true
    
    # 日志配置
    logging:
      level:
        # 将mapper的日志过滤等级设为debug：即显示debug及以上的日志，对mapper而言，能看到sql语句
        com.seangull.dao.mapper: debug
    
    server:
      port: 81
    ```

    **consumer.yml**  
    ```yaml
    spring:
      # 数据库配置：url、账号、密码、连接池等
      datasource:
        username: root
        password: 123456
        url: jdbc:mysql://localhost:3306/spring-cloud-alibaba-demo?serverTimezone=UTC&characterEncoding=UTF-8&useSSL=false
        driver-class-name: com.mysql.jdbc.Driver
        hikari:
          minimum-idle: 5
          maximum-pool-size: 15
          auto-commit: true
          idle-timeout: 30000
          max-lifetime: 1800000
          connection-timeout: 30000
          connection-test-query: select 1
    
      # redis配置：IP地址、端口号、密码、连接池等
      redis:
        host: 127.0.0.1
        port: 6379
        password:
        lettuce:
          pool:
            max-active: 8
            max-wait: -1
            max-idle: 8
            min-idle: 0
    
    # mybatis plus 配置
    mybatis-plus:
      mapper-locations: classpath*:mapper/*.xml
      # 全局配置
      global-config:
        # 0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
        id-type: 0
        # 字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
        field-strategy: 0
        # 驼峰下划线转换
        db-column-underline: true
        # 刷新mapper 调试神器
        refresh-mapper: true
        # 数据库大写下划线转换
        capital-mode: true
      # 自定义SQL注入器
      configuration:
        map-underscore-to-camel-case: true
        cache-enabled: true
    
    # 日志配置
    logging:
      level:
        # 将mapper的日志过滤等级设为debug：即显示debug及以上的日志，对mapper而言，能看到sql语句
        com.seangull.dao.mapper: debug
    
    server:
      port: 82
    ```

    **gateway.yml**  
    ```yaml
    spring:
      cloud:
        sentinel:
          transport:
            dashboard: 127.0.0.1:8080
        gateway:
          routes:
            - id: produce
              uri: lb://producer
              predicates:
                - Path=/producer/**
              filters:
                - StripPrefix=1
            - id: consumer
              uri: lb://consumer
              predicates:
              - Path=/consumer/**
              filters:
              - StripPrefix=1
    
    server:
      port: 80
    ```
    
    
---



## common

* springboot工程。
* 公共模块。所有可重用的代码在本模块中编写，供所有节点调用。
* 依赖模块，生产时无需独立启动，仅作为编码时启动。



---



## dao

* springboot工程。
* dao(data access object)模块。对mysql、redis等数据的增删改查等操作在本模块中编码，供所有节点调用。
* 依赖模块，生产时无需独立启动，仅作为编码时启动。



---



## gateway

* springboot工程
* 网关模块。提供网关服务。
* 启动模块，生产时需要独立启动。

### 本demo示例配置如下：
```yaml
routes:
    - id: produce
      uri: lb://producer
      predicates:
        - Path=/producer/**
      filters:
        - StripPrefix=1
    - id: consumer
      uri: lb://consumer
      predicates:
      - Path=/consumer/**
      filters:
      - StripPrefix=1
```



---



## sentinel-dashboard

* 阿里开源工程，github: [https://github.com/alibaba/Sentinel](https://github.com/alibaba/Sentinel)。
* sentinel控制台。为整合了sentinel的模块（即：gateway模块），提供可视化界面，进行流控、服务降级、服务熔断等配置。
* 启动模块，生产时需要独立启动。

### 1. 启动
切换到sentinel-dashboard目录下。
```shell
# 直接启动，端口号为8080
java -jar sentinel-dashboard-${VERSION}.jar

# 指定端口号启动，端口号为xxx
java -Dserver.port=xxx -jar sentinel-dashboard-${VERSION}.jar  
```

* **${VERSION}** 为实际的版本号，本demo使用的是1.8.2版本。

### 2. 管理

* url示例: [http://127.0.0.1:8080](http://127.0.0.1:8080)，非部署机器请替换正确的ip地址和端口号。
* 用户名和密码均为：sentinel



---



## producer

* springboot工程
* 生产者模块。提供生产服务。
* 启动模块，生产时需要独立启动。

### 接口说明

```shell
url: /product/produce
method: post
request:
    {
        "id": 1,
        "quantity": 5
    }
response:
    {
        "result": "success"
    }
```



---



## consumer

* springboot工程
* 消费者模块。提供消费服务。
* 启动模块，生产时需要独立启动。

### 接口说明

```shell
url: /product/consume
method: post
request:
    {
        "id": 1,
        "quantity": 5
    }
response:
    {
        "result": "success"
    }
```



---



## ui

* 传统前端工程/vue/react/angular工程均可，按需搭建
* 前端模块
* 启动模块，生产时需要独立启动。  

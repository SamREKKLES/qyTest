# qyTest
### 1.开启zookeeper服务 cmd中zkServer.cmd

### 2.开启redis服务 cmd中redis-server.exe

### 3.运行tomcat 打开dubbo服务台 localhost:8089/dubbo-admin-2.5.4-SNAPSHOT

### 4.启动项目后

#### provider端druid监控：http://localhost:8082/druid/sql.html



#### 使用redis：针对读多写少环境，使用redis作为缓存加快查询效率。



#### 使用dubbo：将项目的消费层与服务层分离，服务层通过dubbo 建立service服务，并且到zookeeper注册中心上面注册，填写对应的zookeeper服务所在的IP及端口号。消费层通过到zookeeper上注册订阅相关服务来消费。

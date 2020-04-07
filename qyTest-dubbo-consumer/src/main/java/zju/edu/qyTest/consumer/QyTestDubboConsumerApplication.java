package zju.edu.qyTest.consumer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 启动项
 * @author zj
 * @date 2.7, 2020
 */
@SpringBootApplication
@ImportResource({ "classpath:config/spring-dubbo.xml" })
public class QyTestDubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QyTestDubboConsumerApplication.class, args);
    }

}

package zju.edu.qyTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 启动项
 * @author zj
 * @date 2.7, 2020
 */
@SpringBootApplication
@MapperScan("zju.edu.qyTest.mapper")
public class QytestApplication {

    public static void main(String[] args) {
        SpringApplication.run(QytestApplication.class, args);
    }

}

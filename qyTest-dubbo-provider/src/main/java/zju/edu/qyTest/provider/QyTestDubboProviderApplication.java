package zju.edu.qyTest.provider;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan("zju.edu.qyTest.provider.mapper")
@ImportResource({"classpath:config/spring-dubbo.xml"})
public class QyTestDubboProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(QyTestDubboProviderApplication.class, args);
	}
}

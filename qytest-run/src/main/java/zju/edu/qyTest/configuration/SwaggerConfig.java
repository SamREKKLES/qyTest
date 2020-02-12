package zju.edu.qyTest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置
 * @author zj
 * @date 2.10, 2020
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        //添加请求参数，把token作为头部请求参数传入后端。该功能模仿前端校验
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        parameterBuilder.name("token").description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameters.add(parameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("zju.edu.qyTest.controller"))
                .paths(PathSelectors.any())
                .build()
//                .globalOperationParameters(parameters)
                .apiInfo(new ApiInfoBuilder()
                        .description("qyTest 接口测试文档")
                        .contact(new Contact("zj",
                                "https://github.com/SamREKKLES",
                                "oneyearoneway@zju.edu.cn"))
                        .version("v1.0")
                        .title("API测试文档")
                        .license("Apache2.0")
                        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                        .build());
    }
}

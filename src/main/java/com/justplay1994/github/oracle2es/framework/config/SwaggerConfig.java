/*
 * MIT License
 *
 * Copyright (c) [2018] [oracle-elasticsearch]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.justplay1994.github.oracle2es.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurerAdapter implements SwaggerConfigInterfaces{


    /**
     * Swagger框架的自定义配置
     * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");

//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

//        registry.addResourceHandler("login.html")
//                .addResourceLocations("classpath:/static/");


        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    @Bean
    public Docket createRestApi()
    {
        Docket curSwaggerConfigDocket = new Docket(DocumentationType.SWAGGER_2)
                .groupName(SwaggerConfigInterfaces.SERVICE_GROUP_NAME_STR)                                             /** set SwaggerUI current project(Service) name */
                .useDefaultResponseMessages(false)                                             /** refuse the default response mode */
                .forCodeGeneration(true)                                                       /** set good type for API Document code */
                .select()                                                                      /** set the path where build the document */
                .apis(RequestHandlerSelectors.basePackage(SwaggerConfigInterfaces.SWAGGER_SCAN_BASE_PACKAGE_PATH))     /** set the path for the scanning of API,default as apis(RequestHandlerSelectors.any()) */
                .paths(PathSelectors.any())                                                    /** monitoring the api state */
                .build()
                .apiInfo(testApiInfo());
        return curSwaggerConfigDocket;
    }

    private ApiInfo testApiInfo() {
        return new ApiInfoBuilder()
                .title(SwaggerConfigInterfaces.SERVICE_GROUP_NAME_STR)
                .description(SwaggerConfigInterfaces.SERVICE_DESCRIPTION_STR)
                .version(SwaggerConfigInterfaces.SERVICE_FRAMEWORK_VERSION_STR)
                .termsOfServiceUrl(SwaggerConfigInterfaces.SERVICE_CONTACT_USER_URL_STR)
                .contact(new Contact(SwaggerConfigInterfaces.SERVICE_CONTACT_USERNAME_STR, SwaggerConfigInterfaces.SERVICE_CONTACT_USER_URL_STR, SwaggerConfigInterfaces.SERVICE_CONTACT_USER_MAIL_STR))
                .license(SwaggerConfigInterfaces.SERVICE_LICENSE_STR)
                .licenseUrl(SwaggerConfigInterfaces.SERVICE_LICENSE_URL)
                .build();
    }
}

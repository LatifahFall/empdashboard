package org.latifah.employeedashboardback.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.latifah.employeedashboardback.aspect.AuditAspect;
import org.latifah.employeedashboardback.service.AuditService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAspectJAutoProxy
//@PropertySource("classpath:application.properties")
@ComponentScan({
        "org.latifah.employeedashboardback.config",
        "org.latifah.employeedashboardback.service",
        "org.latifah.employeedashboardback.controller",
        "org.latifah.employeedashboardback",
        "org.latifah.employeedashboardback.aspect"
})
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    @Bean
    public AuditAspect auditAspect(AuditService auditService) {
        return new AuditAspect(auditService);
    }

//    @Bean
//    public LibreTranslateClient libreTranslateClient() {
//        return new LibreTranslateClient();
//    }

}
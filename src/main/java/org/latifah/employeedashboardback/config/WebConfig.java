package org.latifah.employeedashboardback.config;

import org.springframework.context.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.context.support.*;


@EnableWebMvc
@Configuration
// ajouter "org.latifah.employeedashboardback.filter" lorsqu'on ajoutera le jwt filter
@ComponentScan({"org.latifah.employeedashboardback.config","org.latifah.employeedashboardback.service","org.latifah.employeedashboardback.controller", "org.latifah.employeedashboardback"})
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/angular/");

    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
        rb.setBasenames(new String[] { "messages/messages", "messages/validation" });
        return rb;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}


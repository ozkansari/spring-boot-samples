package com.ozkansari.springboot.example.embeddedTomcatAjponly.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

@Profile(EnableEmbeddedTomcatAjpConfig.PROFILE)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EnableEmbeddedTomcatAjpConfig {

    String PROFILE = "enableEmbeddedTomcatAjpConfig";
    
}

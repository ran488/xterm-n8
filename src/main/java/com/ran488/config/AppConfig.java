package com.ran488.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:integration.xml","classpath:persistence.xml"})
public class AppConfig {

}

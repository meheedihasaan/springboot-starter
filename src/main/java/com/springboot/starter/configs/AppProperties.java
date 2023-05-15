package com.springboot.starter.configs;

import com.springboot.starter.constants.AppConstant.Environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private String name;

    private String backendUrl;

    public Environment getActiveProfile() {
        if(Environment.DEVELOPMENT.name().equals(activeProfile)) {
            return Environment.DEVELOPMENT;
        }

        if(Environment.PRODUCTION.name().equals(activeProfile)) {
            return Environment.PRODUCTION;
        }

        return Environment.STAGING;
    }

    public void setActiveProfile(String activeProfile) {
        this.activeProfile = activeProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackendUrl() {
        return backendUrl;
    }

    public void setBackendUrl(String backendUrl) {
        this.backendUrl = backendUrl;
    }
}

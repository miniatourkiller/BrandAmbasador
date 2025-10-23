package com.gym.GoldenGym.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "props.config")
public record Props(String fileStorePath) {
    
}

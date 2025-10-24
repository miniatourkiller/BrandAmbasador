package com.gym.GoldenGym;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.gym.GoldenGym.configs.RSACerts;
import com.gym.GoldenGym.utils.Props;

@SpringBootApplication
@EnableConfigurationProperties({Props.class, RSACerts.class})
public class GoldenGymApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldenGymApplication.class, args);
	}

	@Value("${marker.template.path}")
	private String templatePath;
	@Value("${google.client.id}")
	private String googleClientId;

	@Primary
	@Bean
	public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactory() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath(templatePath);
		return bean;
	}

	@Bean
	@SuppressWarnings({ "deprecation" })
	public GoogleIdTokenVerifier googleIdTokenVerifier() throws GeneralSecurityException, IOException {
		return new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
	} 

}

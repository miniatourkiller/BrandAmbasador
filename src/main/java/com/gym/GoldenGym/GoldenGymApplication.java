package com.gym.GoldenGym;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.gym.GoldenGym.utils.Props;

@SpringBootApplication
@EnableConfigurationProperties({Props.class})
public class GoldenGymApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldenGymApplication.class, args);
	}

	@Value("${marker.template.path}")
	private String templatePath;

	@Primary
	@Bean
	public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactory() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath(templatePath);
		return bean;
	}

}

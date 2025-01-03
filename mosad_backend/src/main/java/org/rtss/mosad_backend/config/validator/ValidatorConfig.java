package org.rtss.mosad_backend.config.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidatorConfig {

   @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
       return new LocalValidatorFactoryBean();
   }


}

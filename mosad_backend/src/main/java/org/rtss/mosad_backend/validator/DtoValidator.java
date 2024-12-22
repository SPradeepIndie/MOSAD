package org.rtss.mosad_backend.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.rtss.mosad_backend.config.validator.ValidatorConfig;
import org.rtss.mosad_backend.dto.user_dtos.UserRegistrationDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DtoValidator {

    private final ValidatorConfig validatorConfig;

    public DtoValidator(ValidatorConfig validatorConfig) {
        this.validatorConfig = validatorConfig;
    }

    public Validator getValidator() {
        return validatorConfig.localValidatorFactoryBean().getValidator();
    }
    public Set<String> validate(UserRegistrationDTO userRegistrationDTO) {
        Set<ConstraintViolation<UserRegistrationDTO>> violation = getValidator().validate(userRegistrationDTO);
        if(!violation.isEmpty()){
            return violation
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }




}

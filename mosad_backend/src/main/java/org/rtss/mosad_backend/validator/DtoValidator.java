package org.rtss.mosad_backend.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.rtss.mosad_backend.config.validator.ValidatorConfig;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.springframework.stereotype.Component;

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

    //Generic validator for validate dto classes
    public <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violation = getValidator().validate(dto);
        if(!violation.isEmpty()){
            Set<String> validationErrorMsg = violation
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new ObjectNotValidException(validationErrorMsg);
        }

    }
}

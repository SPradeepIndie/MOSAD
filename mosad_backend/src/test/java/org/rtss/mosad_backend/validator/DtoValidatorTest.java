package org.rtss.mosad_backend.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rtss.mosad_backend.config.validator.ValidatorConfig;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class DtoValidatorTest {

    private Validator validator;

    //Service which want to test
    private DtoValidator dtoValidator;

    // Inner DTO class for testing
    private static class TestUserDTO {
        @jakarta.validation.constraints.NotBlank(message = "property1 is Mandatory")
        @jakarta.validation.constraints.Size(min = 3, max = 30, message = "property1 should be between 3 and 30 characters long.")
        private String property1;

        @jakarta.validation.constraints.NotNull(message = "Email object can not be null")
        @jakarta.validation.constraints.Email(message = "Email should be valid (example@gmail.com)")
        private String email;

        @jakarta.validation.constraints.Pattern(
                regexp = "^(\\+?[0-9]{10,12})?$",
                message = "checkPattern number must be between 10 to 12 digits long and can optionally start with a '+' sign."
        )
        private String checkPattern;

        public TestUserDTO(String property1, String email, String checkPattern) {
            this.property1 = property1;
            this.email = email;
            this.checkPattern = checkPattern;
        }

        public TestUserDTO() {
        }

        public String getProperty1() {
            return property1;
        }

        public void setProperty1(String property1) {
            this.property1 = property1;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCheckPattern() {
            return checkPattern;
        }

        public void setCheckPattern(String checkPattern) {
            this.checkPattern = checkPattern;
        }
    }

    @BeforeEach
    void setUp() {
        ValidatorConfig validatorConfig = mock(ValidatorConfig.class);
        when(validatorConfig.localValidatorFactoryBean()).thenReturn(mock(LocalValidatorFactoryBean.class));
        dtoValidator = new DtoValidator(validatorConfig);
        validator = mock(Validator.class);
        when(dtoValidator.getValidator()).thenReturn(validator);
    }

    @Test
    void shouldNotThrowExceptionWhenDtoIsValid() {
        // Given
        TestUserDTO testUserDTO=new TestUserDTO(
                "property1",
                "property2@gmail.com",
                "0123456789"
        );

        // When
        dtoValidator.validate(testUserDTO);

        // Then
        verify(validator).validate(testUserDTO);
    }
    @Test
    void shouldThrowObjectNotValidExceptionWhenProperty1IsBlank() {
        // Given
        TestUserDTO testUserDTO=new TestUserDTO(
                "",
                "property2@gamil.com",
                "01234567as"
        );

        Set<ConstraintViolation<Object>> violations = new HashSet<>();
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("property1 is Mandatory");
        violations.add(violation);

        when(validator.validate(any())).thenReturn(violations);

        // When & Then
        ObjectNotValidException exception = assertThrows(ObjectNotValidException.class, () -> {
            dtoValidator.validate(testUserDTO);
        });

        // Verify that the exception contains the expected message.
        assertEquals(Set.of("property1 is Mandatory"), exception.getErrorMessages());
    }
    @Test
    void shouldThrowObjectNotValidExceptionWhenEmailIsNotValid() {
        // Given
        TestUserDTO testUserDTO=new TestUserDTO(
                "property1",
                "property2",
                "0123456789"
        );

        Set<ConstraintViolation<Object>> violations = new HashSet<>();
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("Email should be valid (example@gmail.com)");
        violations.add(violation);

        when(validator.validate(any())).thenReturn(violations);

        // When & Then
        ObjectNotValidException exception = assertThrows(ObjectNotValidException.class, () -> {
            dtoValidator.validate(testUserDTO);
        });

        // Verify that the exception contains the expected message.
        assertEquals(Set.of("Email should be valid (example@gmail.com)"), exception.getErrorMessages());
    }
    @Test
    void shouldThrowObjectNotValidExceptionWhenEmailIsNull() {
        // Given
        TestUserDTO testUserDTO=new TestUserDTO(
                "Property1",
                null,
                "01234567as"
        );

        Set<ConstraintViolation<Object>> violations = new HashSet<>();
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("Email object can not be null");
        violations.add(violation);

        when(validator.validate(any())).thenReturn(violations);

        // When & Then
        ObjectNotValidException exception = assertThrows(ObjectNotValidException.class, () -> {
            dtoValidator.validate(testUserDTO);
        });

        // Verify that the exception contains the expected message.
        assertEquals(Set.of("Email object can not be null"), exception.getErrorMessages());
    }
    @Test
    void shouldThrowObjectNotValidExceptionWhenPatternIsNotValid() {
        // Given
        TestUserDTO testUserDTO=new TestUserDTO(
                "property1",
                "property2@gamil.com",
                "01234567as"
        );

        Set<ConstraintViolation<Object>> violations = new HashSet<>();
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("checkPattern number must be between 10 to 12 digits long and can optionally start with a '+' sign.");
        violations.add(violation);

        when(validator.validate(any())).thenReturn(violations);

        // When & Then
        ObjectNotValidException exception = assertThrows(ObjectNotValidException.class, () -> {
            dtoValidator.validate(testUserDTO);
        });

        // Verify that the exception contains the expected message.
        assertEquals(Set.of("checkPattern number must be between 10 to 12 digits long and can optionally start with a '+' sign."), exception.getErrorMessages());
    }

}
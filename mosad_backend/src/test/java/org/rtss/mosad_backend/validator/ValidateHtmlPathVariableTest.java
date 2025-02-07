package org.rtss.mosad_backend.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateHtmlPathVariableTest {

    private ValidateHtmlPathVariable validateHtmlPathVariable;

    @BeforeEach
    void setUp() {
        validateHtmlPathVariable = new ValidateHtmlPathVariable();
    }
    @Test
    void shouldGiveInputWhichEscapedFromHTMLSpecialCharacters() {
        // Given
        String inputWithHTMLSpecialCharacters = "<div>Hello & Welcome to \"Java\"!</div>";

        // When
        String escapedInput = validateHtmlPathVariable.escapeHTMLSpecialCharacters(inputWithHTMLSpecialCharacters);

        // Then
        String expectedOutput = "&lt;div&gt;Hello &amp; Welcome to &quot;Java&quot;!&lt;/div&gt;";
        assertEquals(expectedOutput, escapedInput);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenInputIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> validateHtmlPathVariable.escapeHTMLSpecialCharacters(null),
                "escapeHTMLSpecialCharacters input cannot be null or empty");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenInputIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> validateHtmlPathVariable.escapeHTMLSpecialCharacters(null),
                "escapeHTMLSpecialCharacters input cannot be null or empty");
    }
}
package org.rtss.mosad_backend.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

@Component
public class ValidateHtmlPathVariable {

//    This class is the one who is responsible for sanitize the path variables in html request
    public String escapeHTMLSpecialCharacters(String input) {
        if(input==null || input.isEmpty()) {
            throw new IllegalArgumentException("escapeHTMLSpecialCharacters input cannot be null or empty");
        }
        return HtmlUtils.htmlEscape(input);
    }
}

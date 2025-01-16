package org.rtss.mosad_backend.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

@Component
public class ValidateHtmlPathVariable {

//    This class is the one who is responsible for sanitize the path variables in html request

    public String escapeHTMLspecailCharaters(String input) {
        return HtmlUtils.htmlEscape(input);
    }
}

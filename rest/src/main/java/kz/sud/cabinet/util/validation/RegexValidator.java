package kz.sud.cabinet.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator extends AParamValidator {
    private String pattern;

    public RegexValidator() {
        this.pattern = ".*";
    }

    public RegexValidator(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean validate(String param) {
        if(param == null) {
            return false;
        }
        else {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(param);
            return m.matches();
        }
    }
}

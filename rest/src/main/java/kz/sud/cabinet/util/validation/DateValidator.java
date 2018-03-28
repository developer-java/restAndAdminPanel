package kz.sud.cabinet.util.validation;

public class DateValidator extends AParamValidator {
    private String pattern;
    public DateValidator() {
        this.pattern = "yyyyMMdd";
    }

    public DateValidator(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean validate(String param) {
        return checkDate(param, pattern);
    }
}

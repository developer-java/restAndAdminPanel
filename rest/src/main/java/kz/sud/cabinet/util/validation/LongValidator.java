package kz.sud.cabinet.util.validation;

public class LongValidator extends AParamValidator {
    @Override
    public boolean validate(String param) {
        return checkLong(param);
    }
}

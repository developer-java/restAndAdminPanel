package kz.sud.cabinet.util.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class AParamValidator {
    public static boolean validate(String param, Class<? extends AParamValidator> clazz) {
        try {
            return clazz.newInstance().validate(param);
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public abstract boolean validate(String param);

    protected boolean checkLength(String param, int min, int max) {
        if(param == null) {
            return false;
        }
        else if(param.length() >= min && param.length() <= max) {
            return true;
        }
        return false;
    }

    protected boolean checkDate(String param, String pattern) {
        if(param == null) {
            return false;
        }
        else {
            try {
                new SimpleDateFormat(param).parse(param);
                return true;
            }
            catch(ParseException pe) {
                return false;
            }
        }
    }

    protected boolean checkLong(String param) {
        if(param == null) {
            return false;
        }
        else {
            try {
                Long.parseLong(param);
                return true;
            }
            catch(NumberFormatException nfe) {
                return false;
            }
        }
    }
}

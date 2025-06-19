package co.simplon.soninkrala.dtos.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomErrorResponse {

    private Map<String, ArrayList<String>> fieldErrors = new HashMap<>();

    private List<String> globalErrors;

    public CustomErrorResponse() {
        //
    }

    public List<String> getGlobalErrors() {
        return globalErrors;
    }

    public void setGlobalErrors(List<String> globalErrors) {
        this.globalErrors = globalErrors;
    }

    public Map<String, ArrayList<String>> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, ArrayList<String>> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}

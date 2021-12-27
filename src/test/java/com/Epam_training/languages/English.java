package com.Epam_training.languages;


public class English {
    private String errorWelcomeMessage;
    private String errorMessageValidation_A;
    private String errorMessageValidation_B;
    private String passwordAttribute;

    public English() {
        errorWelcomeMessage = "The welcome message has to have the word Hello, however, the message doesn't have that word or it doesn't show it";
        errorMessageValidation_A = "The error message has to have the text";
        errorMessageValidation_B = " and it doesn't have that message or it doesn't show it ";
        passwordAttribute = "The password field has the attribute type equal to password";
    }

    public String getErrorWelcomeMessage() {
        return errorWelcomeMessage;
    }

    public String getErrorMessageValidation_A() {
        return errorMessageValidation_A;
    }

    public String getErrorMessageValidation_B() {
        return errorMessageValidation_B;
    }

    public String getPasswordAttribute() {
        return passwordAttribute;
    }
}

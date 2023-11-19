package org.syscom.module_achat.exception.login;

public class PasswordIncorrectException extends RuntimeException{
    public PasswordIncorrectException(String message){
        super(message);
    }
}
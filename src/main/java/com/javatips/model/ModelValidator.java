package com.javatips.model;

import com.javatips.exceptions.JavaTipsInvalidModelException;

public interface ModelValidator {

    public void validateAndSelfCorrect() throws JavaTipsInvalidModelException;

}

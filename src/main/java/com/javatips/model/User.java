package com.javatips.model;

import org.springframework.data.annotation.Id;

import com.javatips.exceptions.JavaTipsInvalidModelException;

public class User implements ModelValidator {

    @Id
    private String email;

    private String firstName;

    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void validateAndSelfCorrect() throws JavaTipsInvalidModelException {
        if (this.firstName.isEmpty() || this.lastName.isEmpty() || this.email.isEmpty()) {
            throw new JavaTipsInvalidModelException("Fields cannot be blank!");
        }

        if (this.firstName == null || this.lastName == null || this.email == null) {
            throw new JavaTipsInvalidModelException("Fields cannot be null!");
        }
        correctFields();
    }

    private void correctFields() {
       // Standardise how data is persisted.
       String firstNameCorrected = correctName(firstName);
       String lastNameCorrected = correctName(lastName); 
       String emailCorrected = email.toLowerCase();
       this.firstName = firstNameCorrected;
       this.lastName = lastNameCorrected;
       this.email = emailCorrected;
    }

    private String correctName(String name) {
        StringBuilder result = new StringBuilder();
        result.append(Character.toTitleCase(name.charAt(0)))
              .append(name.substring(1));
        return result.toString();
    }
}

package com.autofx.autofxbackend.iam.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record PhoneNumber(String countryCode, Integer number) {
    public PhoneNumber() {this(null, null);}

    public PhoneNumber{
        if (countryCode == null || countryCode.isBlank()) throw  new IllegalArgumentException("Country code cannot be null or blank");
        if (number == null) throw  new IllegalArgumentException("Phone number cannot be null");
    }

    @JsonIgnore
    public String getPhoneNumber(){
        return  countryCode + " " + number.toString();
    }

}

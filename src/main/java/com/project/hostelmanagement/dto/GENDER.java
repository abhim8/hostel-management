package com.project.hostelmanagement.dto;

import java.util.Map;

public enum GENDER {
    MALE("MALE"),
    FEMALE("FEMALE");
    private String genderString;

    GENDER(String genderString) {
        this.genderString = genderString;
    }

    public String getGenderString() {
        return genderString;
    }

    private static final Map<String, GENDER> genderStringToGenderMap = Map.of(
            GENDER.MALE.genderString, GENDER.MALE,
            GENDER.FEMALE.genderString, GENDER.FEMALE
    );

    public static GENDER get(String genderString) {
        return genderStringToGenderMap.getOrDefault(genderString, MALE);
    }
}

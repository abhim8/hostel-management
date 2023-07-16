package com.project.hostelmanagement.dto;

import java.util.Map;

public enum IDTYPES {
    AADHAAR("AADHAAR"),
    VOTER_ID("VOTER_ID"),
    DRIVING_LICENCE("DL"),
    RATION_CARD("RATION_CARD"),
    PAN("PAN"),
    PASSPORT("PASSPORT");

    private String idTypeString;

    IDTYPES(String idTypeString) {
        this.idTypeString = idTypeString;
    }

    public String getIdTypeString() {
        return idTypeString;
    }

    private static final Map<String, IDTYPES> idTypeStringToIdTypeMap = Map.of(
            IDTYPES.AADHAAR.idTypeString, IDTYPES.AADHAAR,
            IDTYPES.VOTER_ID.idTypeString, IDTYPES.VOTER_ID,
            IDTYPES.DRIVING_LICENCE.idTypeString, IDTYPES.DRIVING_LICENCE,
            IDTYPES.RATION_CARD.idTypeString, IDTYPES.RATION_CARD,
            IDTYPES.PAN.idTypeString, IDTYPES.PAN,
            IDTYPES.PASSPORT.idTypeString, IDTYPES.PASSPORT
    );

    public static IDTYPES get(String idTypeString) {
        return idTypeStringToIdTypeMap.getOrDefault(idTypeString, AADHAAR);
    }

}

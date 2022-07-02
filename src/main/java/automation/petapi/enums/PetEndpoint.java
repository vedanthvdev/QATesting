package automation.petapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PetEndpoint {

    CREATE_PET("/pet"),
    GET_PET("/pet/{petId}"),
    DELETE_PET("/pet/{petId}"),
    UPDATE_PET("/pet");

    @Getter
    private final String value;
}

package automation.petservice.pet;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class PetClientService {

    private static final String CREATE_PET_ENDPOINT = "/pet";
    private static final String GET_PET_ENDPOINT = "/pet/{petId}";
    private static final String DELETE_PET_ENDPOINT = "/pet/{petId}";

    public void createPetRequest(Pet pet) {
        RestAssured
                .given()
                    .body(pet)
                .when()
                    .post(CREATE_PET_ENDPOINT)
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }

    public int getPetId(Pet pet) {
        return RestAssured
                .given()
                    .pathParam("petId", pet.getId())
                .when()
                    .get(GET_PET_ENDPOINT)
                .thenReturn()
                    .path("id");
    }

    public String getPetName(Pet pet) {
        return RestAssured
                .given()
                    .pathParam("petId", pet.getId())
                .when()
                    .get(GET_PET_ENDPOINT)
                .thenReturn()
                    .path("name");
    }

    public void deleteAllPets() {
        List<Integer> petIds = Arrays.asList(12345);
        for (Integer pet: petIds) {
            RestAssured
                    .given()
                        .pathParam("petId", pet)
                    .when()
                        .delete(DELETE_PET_ENDPOINT)
                    .then()
                        .statusCode(HttpStatus.SC_OK);
        }
    }
}

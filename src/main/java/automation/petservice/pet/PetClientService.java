package automation.petservice.pet;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.springframework.stereotype.Component;


@Component
public class PetClientService {

    public ValidatableResponse createPetRequest(CreatePet createPet) {
        return RestAssured
                .given()
//            .log().all()
                .header("Content-Type", "application/json")
                .body(createPet)
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .statusCode(200);
    }
}

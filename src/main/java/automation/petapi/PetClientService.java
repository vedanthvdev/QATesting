package automation.petapi;

import automation.context.ContextManager;
import automation.petapi.enums.PetEndpoint;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class PetClientService {

    private final ContextManager contextManager;
    private final List<Integer> petId = new ArrayList<>();

    public PetClientService(ContextManager contextManager){
        this.contextManager = contextManager;
    }

    public void createPetRequest() {
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        RestAssured
                .given()
                    .body(petServiceContext.getPet())
                .when()
                    .post(PetEndpoint.CREATE_PET.getValue())
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }

    public void updatePetRequest() {
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        RestAssured
                .given()
                    .body(petServiceContext.getPet())
                .when()
                    .put(PetEndpoint.UPDATE_PET.getValue())
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }

    public int getPet() {
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        return RestAssured
                .given()
                    .pathParam("petId", petServiceContext.getPet().getId())
                .when()
                    .get(PetEndpoint.GET_PET.getValue())
                .thenReturn()
                    .path("id");
    }

    public int getPet(int id) {
        return RestAssured
                .given()
                    .pathParam("petId", id)
                .when()
                    .get(PetEndpoint.GET_PET.getValue())
                .thenReturn()
                    .statusCode();
    }

    public void assertPetDeleted(){
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        RestAssured
                .given()
                    .pathParam("petId", petServiceContext.getPet().getId())
                .when()
                    .get(PetEndpoint.GET_PET.getValue())
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    public String getPetName() {
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        return RestAssured
                .given()
                    .pathParam("petId", petServiceContext.getPet().getId())
                .when()
                    .get(PetEndpoint.GET_PET.getValue())
                .thenReturn()
                    .path("name");
    }

    public void deletePet() {
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        RestAssured
                .given()
                    .pathParam("petId", petServiceContext.getPet().getId())
                .when()
                    .delete(PetEndpoint.DELETE_PET.getValue())
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }

    public void deleteAllPets(){
        List<Integer> petIds = petIdToDeleteAfterTest();
        if(!petIds.isEmpty()){
            for (Integer pet: petIds){
                if(getPet(pet)==HttpStatus.SC_OK) {
                    RestAssured
                            .given()
                                .pathParam("petId", pet)
                            .when()
                                .delete(PetEndpoint.DELETE_PET.getValue())
                            .then()
                                .statusCode(HttpStatus.SC_OK);
                }
            }
        }
    }

    public List<Integer> petIdToDeleteAfterTest(){
        int id = contextManager.getRequiredContext(PetServiceContext.class).getPet().getId();
        if(id!=0 && !petId.contains(id)) {
            petId.add(id);
        }
        return petId;
    }
}

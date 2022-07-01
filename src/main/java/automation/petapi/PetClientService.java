package automation.petapi;

import automation.context.ContextManager;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class PetClientService {

    private static final String CREATE_PET_ENDPOINT = "/pet";
    private static final String GET_PET_ENDPOINT = "/pet/{petId}";
    private static final String DELETE_PET_ENDPOINT = "/pet/{petId}";
    private static final String UPDATE_PET_ENDPOINT = "/pet";

    private final ContextManager contextManager;
    private List<Integer> petId = new ArrayList<Integer>();


    public PetClientService(ContextManager contextManager){
        this.contextManager = contextManager;
    }

    public void createPetRequest() {
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        RestAssured
                .given()
                    .body(petServiceContext.getPet())
                .when()
                    .post(CREATE_PET_ENDPOINT)
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }

    public void updatePetRequest() {
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        RestAssured
                .given()
                .body(petServiceContext.getPet())
                .when()
                .put(UPDATE_PET_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    public int getPetId() {
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        return RestAssured
                .given()
                    .pathParam("petId", petServiceContext.getPet().getId())
                .when()
                    .get(GET_PET_ENDPOINT)
                .thenReturn()
                    .path("id");
    }

    public void assertPetDeleted(){
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        RestAssured
                .given()
                    .pathParam("petId", petServiceContext.getPet().getId())
                .when()
                    .get(GET_PET_ENDPOINT)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    public String getPetName() {
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        return RestAssured
                .given()
                    .pathParam("petId", petServiceContext.getPet().getId())
                .when()
                    .get(GET_PET_ENDPOINT)
                .thenReturn()
                    .path("name");
    }

    public void deletePet() {
//        List<Integer> petIds = Arrays.asList(12345);
//        petIds.add(id);
//        for (Integer pet: petIds) {
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        RestAssured
                    .given()
                        .pathParam("petId", petServiceContext.getPet().getId())
                    .when()
                        .delete(DELETE_PET_ENDPOINT)
                    .then()
                        .statusCode(HttpStatus.SC_OK);
//        }
    }

    public void deleteAllPets(){
        List<Integer> petIds = petIdToDeleteAfterTest();
        if(!petIds.isEmpty()){
            for (Integer pet: petIds){
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

    public List<Integer> petIdToDeleteAfterTest(){
        int id = contextManager.getRequiredContext(PetServiceContext.class).getPet().getId();
        if(id!=0 && !petId.contains(id)) {
            petId.add(id);
        }
        return petId;
    }
}

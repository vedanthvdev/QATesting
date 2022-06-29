package automation.stepdefinition;

import automation.petservice.pet.CreatePet;
import automation.petservice.pet.PetClientService;
import automation.petservice.pet.submodules.CategoryName;
import automation.petservice.pet.submodules.TagName;
import com.google.common.collect.ImmutableList;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;

public class PetStoreApiSteps {

    private Response response;

    public PetStoreApiSteps() {

    }

    @When("the petstore endpoint is called")
    public void accountSpetstoreionEndpointIsCalled() {
        String[] a = {"photoURL"};

        CreatePet pet = new CreatePet.Builder()
                .withId(11111)
                .withCategoryName(new CategoryName.Builder()
                        .withId(0)
                        .withName("CategoryName")
                        .build())
                .withPetName("PetName")
                .withPhotoUrls(a)
                .withTagName(ImmutableList.of(new TagName.Builder()
                        .withId(0)
                        .withName("TagName")
                        .build()))
                .withStatus("Status")
                .build();

        PetClientService petClientService = new PetClientService();

        petClientService.createPetRequest(pet);

    }

    @Then("verify pet was created with correct data")
    public void verifyPetWasCreatedWithCorrectData() {
        Response response = RestAssured
                .given()
//                .log().all()
                .header("Content-Type", "application/json")
                .pathParam("petId","11111")
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        response
                .then()
                .assertThat()
                .body("name", equalTo("PetName"))
                .body("status", equalTo("Status"));
    }

    @Then("update the pet name and verify the update")
    public void updateThePetNameAndVerify() {
        Response response = RestAssured
                .given()
//                .log().all()
                .header("Content-Type", "application/json")
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        response
                .then()
                .assertThat()
                .body("name", equalTo("PetName"))
                .body("status", equalTo("Status"));
    }

}

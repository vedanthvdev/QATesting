package automation.core.framework;

import automation.context.ContextManager;
import automation.petapi.PetClientService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;


public class TestHooks extends BaseStepDef {
    private final PetClientService petClientService;
    private final ContextManager contextManager;

    public TestHooks(ContextManager contextManager, PetClientService petClientService, ContextManager contextManager1) {
        super(contextManager);
        this.petClientService = petClientService;
        this.contextManager = contextManager1;
    }

    @Before(order = 0)
    public void before() {
        contextManager.resetContext();
//        testContext.createWebDriver();
    }

    @Before
    public void apisetup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2";
        RestAssured.authentication.equals(getToken());
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", getToken())
                .setContentType(ContentType.JSON)
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }

    @After
    public void afterScenario(){
    contextManager.removeContext();
    }

    @After("@delete_all_pets")
    public void deletePets() {
        petClientService.deleteAllPets();
    }

    private String getToken() {
        return "special-key";
    }

}

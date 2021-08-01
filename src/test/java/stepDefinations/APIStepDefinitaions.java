package stepDefinations;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import libraries.APIEndPoints;
import libraries.ConfigReader;
import org.testng.Assert;
import requestBody.PostRequest;
import utils.TestBase;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class APIStepDefinitaions extends TestBase {
    private APIEndPoints resAPI;
    private ResponseSpecification resSpec;
    private Response response;

    @When("I submit GET request for POSTSAPI")
    public void iSubmitGETRequestForPOSTSAPI() {
        resAPI = APIEndPoints.valueOf("POSTSAPI");
        test.info("GET Request : "+RestAssured.baseURI+resAPI.getResource()+"/1");
        response = given().log().all().spec(requestSpecification())
                             .when().get(resAPI.getResource()+"/1")
                .then().log().all().extract().response();
        test.info("Response"+response.prettyPrint());
        world.put("statusCode", "" + response.statusCode());
    }

    @Then("^I verify statuscode as \"([^\"]*)\"$")
    public void i_verify_statuscode(String statusCode) throws Throwable {
        if(world.get("statusCode").equalsIgnoreCase(statusCode)) {
            test.pass("Expected Status Code#" + statusCode + " | Actual Status Code#" + world.get("statusCode"));
        }else{
            test.fail("Expected Status Code#" + statusCode + ",Actual Status Code#" + world.get("statusCode"));
            test.info(MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));
            Assert.assertTrue(world.get("statusCode").equalsIgnoreCase(statusCode));
        }
    }

    @When("I get baseurl for API")
    public void iGetBaseurlForAPI() throws IOException {
        RestAssured.baseURI =  ConfigReader.getConfigValue("APIBaseURL");
        test = reports.createTest("API Feature");
    }

    @And("I submit POST request for POSTSAPI")
    public void iSubmitPOSTRequestForPOSTSAPI() {
        resAPI = APIEndPoints.valueOf("POSTSAPI");
        String body = PostRequest.postRequestBody();
        test.info("POST Request : "+RestAssured.baseURI+resAPI.getResource());
        test.info("Request Body: ");
        test.info(body);
        response = given().log().all().spec(requestSpecification())
                .when().body(body).post(resAPI.getResource())
                .then().log().all().extract().response();
        test.info("Response"+response.prettyPrint());
        world.put("statusCode", "" + response.statusCode());
    }

    @And("I submit PUT request for POSTSAPI")
    public void iSubmitPUTRequestForPOSTSAPI() {
        resAPI = APIEndPoints.valueOf("POSTSAPI");
        String body = PostRequest.putRequestBody();
        test.info("PUT Request : "+RestAssured.baseURI+resAPI.getResource()+"/1");
        test.info("Request Body: ");
        test.info(body);
        response = given().log().all().spec(requestSpecification())
                .when().body(body).put(resAPI.getResource()+"/1")
                .then().log().all().extract().response();
        test.info("Response"+response.prettyPrint());
        world.put("statusCode", "" + response.statusCode());
    }

    @And("I submit DELETE request for POSTSAPI")
    public void iSubmitDELETERequestForPOSTSAPI() {
        resAPI = APIEndPoints.valueOf("POSTSAPI");
        test.info("DELETE Request : "+RestAssured.baseURI+resAPI.getResource()+"/1");
        response = given().log().all().spec(requestSpecification())
                .when().delete(resAPI.getResource()+"/1")
                .then().log().all().extract().response();
        test.info("Response"+response.prettyPrint());
        world.put("statusCode", "" + response.statusCode());
    }
}

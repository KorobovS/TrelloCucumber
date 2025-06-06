package api.cucumber.steps;

import api.base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.testng.Assert;

import static api.base.TestData.ListsTestData.baseListId;
import static api.base.TestData.response;
import static io.restassured.RestAssured.rootPath;

public class ListStepDefinition extends BaseTest {

    @Step("I create base list")
    @Given("I create base list")
    public void i_create_base_list_with_listId() {
        baseListId = getListsService().createList("Base list").body().jsonPath().getString("id");
    }

    @Step("I create a list with default options and name - {name}")
    @When("I create a list with default options and {string}")
    public void i_create_a_list_with_default_options_and_name(String name) {
        response = getListsService().createList(name);
    }

    @When("I create a list with {string} based on an existing one base list and set the position {string}")
    public void i_create_a_list_with_name_based_on_an_existing_one_base_list_and_set_the_position(String name, String position) {
        response = getListsService().createListWithPosition(name, position);
    }

    @Step("Check I got the list not null")
    @Then("I got the list")
    public void i_got_the_list() {
        Assert.assertNotNull(response.body().jsonPath().get(rootPath));
    }

    @Step("Check The list name field matches the {name}")
    @And("The list name field matches the {string}")
    public void the_list_name_field_matches_the_name(String expected) {
        Assert.assertEquals(response.body().jsonPath().getString("name"), expected);
    }

    @Step("Check the list position")
    @And("The list position matches the {string}")
    public void the_list_position_matches(String expected) {
        if (expected.equals("top")) {
            expected = "140737488322560";
        } else if (expected.equals("bottom")) {
            expected = "140737488404480";
        }
        Assert.assertEquals(response.body().jsonPath().getString("pos"), expected);
    }
}

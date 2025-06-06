package api.cucumber.steps;

import api.base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.Assert;

import static api.base.TestData.ListsTestData.baseListId;
import static api.base.TestData.ListsTestData.listId;
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
        listId = response.body().jsonPath().getString("id");
    }

    @Step("I get list")
    @When("I get list")
    public void i_get_list() {
        response = getListsService().getAList(listId);
    }

    @Step("I get list with fields = {value}")
    @When("I get list with {string}")
    public void i_get_list_with_fields(String value) {
        response = getListsService().getListWithFields(listId, value);
    }

    @Step("I create a list with name = {name} based on an existing one base list and set the position = {position}")
    @When("I create a list with {string} based on an existing one base list and set the position {string}")
    public void i_create_a_list_with_name_based_on_an_existing_one_base_list_and_set_the_position(String name, String position) {
        response = getListsService().createListWithPosition(name, position);
    }

    @Step("I get actions for a list")
    @When("I get actions for a list")
    public void i_get_actions_for_a_list() {
        response = getListsService().getActionsofAList(listId);
    }

    @Step("I get the board a list is on")
    @When("I get the board a list is on")
    public void i_get_the_board_a_list_is_on() {
        response = getListsService().getABoardAListIsOn(listId);
    }

    @Step("I get cards in a list")
    @When("I get cards in a list")
    public void i_get_cards_in_a_list() {
        response = getListsService().getCardsOnAList(listId);
    }

    @Step("Check response not null")
    @Then("I got the resources")
    public void i_got_the_resources() {
        Assert.assertNotNull(response.body().jsonPath().get(rootPath));
    }

    @Step("Check The list name field matches the {expected}")
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

    @Step("The list contains only these fields = {value}")
    @And("The list contains only these {string}")
    public void the_list_contains_only_these_fields(String value) {
        if (value.equals("all")) {
            return;
        }

        String[] arrayValue = value.split(",");
        for (String str : arrayValue) {
            Allure.step(String.format("Fields value %s", str));
            Assert.assertTrue(response.body().jsonPath().getString(rootPath).contains(str));
        }
    }
}

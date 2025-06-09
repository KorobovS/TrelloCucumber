package api.cucumber.steps;

import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.Assert;

import static api.base.TestData.ListsTestData.*;
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
        numberOfListsOnTheBoard = getBoardService().getListsOfABoard(TestData.BoardTestData.boardId).body().jsonPath().getList(rootPath).size();
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
        numberOfListsOnTheBoard = getBoardService().getListsOfABoard(TestData.BoardTestData.boardId).body().jsonPath().getList(rootPath).size();
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

    @Step("I archive the list from the board")
    @When("I archive the list from the board")
    public void i_archive_the_list_from_the_board() {
        response = getListsService().archiveAList(listId);
        archiveListId = response.body().jsonPath().getString("id");
    }

    @Step("I unarchive the list from the board")
    @When("I unarchive the list from the board")
    public void i_unarchive_the_list_from_the_board() {
        response = getListsService().unArchiveAList(archiveListId);
    }

    @Step("I update a {field} on a list with {value}")
    @When("I update a {string} on a list with {string}")
    public void i_update_a_field_on_a_list_with_value(String field, String value) {
        response = getListsService().updateFieldOfAList(listId, field, value);
    }

    @Step("I update a list change the {option} to a new {value}")
    @When("I update a list change the {string} to a new {string}")
    public void i_update_a_list_change_the_option_to_a_new_value(String option, String value) {
        String[] optionArr = option.split(", ");
        String[] valueArr = value.split(", ");

        for (int i = 0; i < optionArr.length; i++) {
            oldOptionValue.put(optionArr[i], response.body().jsonPath().getString(optionArr[i]));
            response = getListsService().updateOptionForList(listId, optionArr[i], valueArr[i]);
        }
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

    @Step("Check archive status {expected}")
    @And("Check archive status {string}")
    public void check_archive_status(String expected) {
        Assert.assertEquals(getListsService().getAList(listId).body().jsonPath().getString("closed"), expected);
    }

    @Step("The number of lists on the board has changed")
    @And("The number of lists on the board has changed")
    public void the_number_of_lists_on_the_board_has_changed() {
        int expected = numberOfListsOnTheBoard + 1;
        Assert.assertEquals(getBoardService().getListsOfABoard(boardId).body().jsonPath().getList(rootPath).size(), expected);
    }

    @Step("Check the {option} have new {value}")
    @And("Check the {string} have new {string}")
    public void check_the_option_have_new_value(String option, String value) {
        String[] optionArr = option.split(", ");
        String[] valueArr = value.split(", ");

        for (int i = 0; i < optionArr.length; i++) {
            Allure.step(String.format(optionArr[i] + " = " + valueArr[i]));
            Assert.assertNotEquals(response.body().jsonPath().getString(optionArr[i]), oldOptionValue.get(optionArr[i]));
            Assert.assertEquals(response.body().jsonPath().getString(optionArr[i]), valueArr[i]);
        }
    }
}

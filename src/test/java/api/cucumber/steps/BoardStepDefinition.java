package api.cucumber.steps;

import api.base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

import static api.base.TestData.BoardTestData.BOARD_NAME;
import static api.base.TestData.BoardTestData.boardId;
import static api.base.TestData.response;
import static io.restassured.RestAssured.rootPath;

public class BoardStepDefinition extends BaseTest {

    @Given("I am registered user in the Trello app")
    public void i_am_registered_user_in_the_trello_app() {

    }

    @When("I create a board with default options")
    public void i_create_a_board_with_default_options() {
        response = getBoardService().createBoard(BOARD_NAME);
        boardId = response.body().jsonPath().getString("id");
    }

    @When("I create a board with {string} access")
    public void i_create_a_board_with_public_access(String valueOption) {
        response = getBoardService().createCustomBoard(BOARD_NAME, "prefs_permissionLevel", valueOption);
        boardId = response.body().jsonPath().getString("id");
    }

    @When("I create a board with custom {string} and give {string}")
    public void i_create_a_board_with_custom_option(String option, String value) {
        response = getBoardService().createCustomBoard(BOARD_NAME, option, value);
        boardId = response.body().jsonPath().getString("id");
    }

    @When("I get resources on a board")
    public void i_get_resources_on_a_board() {
        response = getBoardService().getBoard(boardId);
    }

    @When("I get lists on a board")
    public void i_get_lists_on_a_board() {
        response = getBoardService().getListsOfABoard(boardId);
    }

    @When("I get members on a board")
    public void i_get_members_on_a_board() {
        response = getBoardService().getMembers(boardId);
    }

    @When("I get cards on a board")
    public void i_get_cards_on_a_board() {
        response = getBoardService().getCards(boardId);
    }

    @When("I get labels on a board")
    public void i_get_labels_on_a_board() {
        response = getBoardService().getLabelsOnBoard(boardId);
    }

    @When("I delete a board")
    public void i_delete_a_board() {
        response = getBoardService().deleteABoardFromService(boardId);
    }

    @When("I send an invitation to email")
    public void i_send_an_invitation_to_email() {
        response = getBoardService().inviteMemberToBoardViaEmail(boardId);
    }

    @Then("A board is created")
    public void a_board_is_created() {
        checkStatusCode();
    }

    @Then("I got resource boards")
    public void i_got_resource_boards() {
        checkStatusCode();
    }

    @Then("I got the resources by lists")
    public void i_got_the_resources_by_lists() {
        checkStatusCode();
    }

    @Then("I got the resources by members")
    public void i_got_the_resources_by_members() {
        checkStatusCode();
    }

    @Then("I got the resources by cards")
    public void i_got_the_resources_by_cards() {
        checkStatusCode();
    }

    @Then("I got the resources by labels")
    public void i_got_the_resources_by_labels() {
        checkStatusCode();
    }

    @Then("The board is removed")
    public void the_board_is_removed() {
        checkStatusCode();
    }

    @Then("Invitation sent by email")
    public void invitation_sent_by_email() {
        checkStatusCode();
    }

    @And("{int} lists presented on the board")
    public void three_lists_presented_on_the_board(int listSize) {
        response = getBoardService().getListsOfABoard(boardId);
        List<String> lists = response.body().jsonPath().getList(rootPath);

        Assert.assertEquals(lists.size(), listSize);
    }

    @And("A board has {string} access")
    public void a_board_has_public_access(String valueOption) {
        Assert.assertEquals(response.body().jsonPath().getString("prefs.permissionLevel"), valueOption);
    }

    @And("The board has an custom {string} with a given {string}")
    public void i_board_has_an_custom_option_with_a_given_value(String option, String value) {

        if (option.equals("defaultLists")) {
            Assert.assertEquals(getBoardService().getListsOfABoard(boardId).jsonPath().getList(rootPath).size(), 0);
        } else if (option.equals("defaultLabels")) {
            Assert.assertEquals(getBoardService().getLabelsOnBoard(boardId).jsonPath().getList(rootPath).size(), 0);
        } else {
            Assert.assertEquals(response.body().jsonPath().getString(option.replace('_', '.')), value);
        }
    }

    private void checkStatusCode() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}

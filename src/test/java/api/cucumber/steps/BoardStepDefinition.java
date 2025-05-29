package api.cucumber.steps;

import api.base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static api.base.TestData.BoardTestData.*;
import static api.base.TestData.response;
import static io.restassured.RestAssured.rootPath;

public class BoardStepDefinition extends BaseTest {

    @Given("I am registered user in the Trello app")
    public void i_am_registered_user_in_the_trello_app() {

    }

    @When("I create a board with default options")
    public void i_create_a_board_with_default_options() {
        response = getBoardService().createBoard(boardName);
        boardId = response.body().jsonPath().getString("id");
    }

    @When("I create a board with {string} access")
    public void i_create_a_board_with_public_access(String valueOption) {
        response = getBoardService().createCustomBoard(boardName, "prefs_permissionLevel", valueOption);
        boardId = response.body().jsonPath().getString("id");
        boardUrl = response.body().jsonPath().getString("url");
        boardDesc = response.body().jsonPath().getString("desc");
    }

    @When("I create a board with custom {string} and give {string}")
    public void i_create_a_board_with_custom_option(String option, String value) {
        response = getBoardService().createCustomBoard(boardName, option, value);
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

    @When("I send an invitation to email with {string} and {string}")
    public void i_send_an_invitation_to_email_with_option_and_value(String option, String value) {
        response = getBoardService().inviteMemberToBoardViaEmailWithOptionAndValue(boardId, option, value);
    }

    @When("I am requesting {string} data")
    public void i_am_requesting_field_data(String field) {
        if (field.equals("prefs/background")) {
            response = getBoardService().getBoard(boardId);
            return;
        }
        response = getBoardService().getAField(boardId, field);
    }

    @When("I update {string} to the new {string} on the board")
    public void i_am_updating_the_field_on_board(String field, String value) {
        response = getBoardService().updateFieldBoard(boardId, field, value);
        boardDesc = response.body().jsonPath().getString("desc");
        boardName = response.body().jsonPath().getString("name");
        boardBackground = response.body().jsonPath().getString("prefs.background");
        System.out.println(response.body().jsonPath().getString("prefs.background") + "***");
    }

    @Then("A board is created")
    public void a_board_is_created() {
        Assert.assertNotNull(getBoardService().getBoard(boardId).body().jsonPath().get(rootPath));
        Assert.assertEquals(response.body().jsonPath().getString("id"), boardId);
    }

    @Then("I got resource boards")
    public void i_got_resource_boards() {
        Assert.assertNotNull(response.body().jsonPath().get(rootPath));
    }

    @Then("I got the resources by lists")
    public void i_got_the_resources_by_lists() {
        Assert.assertNotNull(getBoardService().getListsOfABoard(boardId).body().jsonPath().get(rootPath));
        Assert.assertEquals(getBoardService().getListsOfABoard(boardId).body().jsonPath().getList(rootPath).size(), 3);
    }

    @Then("I got the resources by members")
    public void i_got_the_resources_by_members() {
        Assert.assertNotNull(getBoardService().getMembers(boardId).body().jsonPath().get(rootPath));
        Assert.assertEquals(getBoardService().getMembers(boardId).body().jsonPath().getList(rootPath).size(), 1);
    }

    @Then("I got the resources by cards")
    public void i_got_the_resources_by_cards() {
        Assert.assertNotNull(getBoardService().getCards(boardId).body().jsonPath().get(rootPath));
        Assert.assertEquals(getBoardService().getCards(boardId).body().jsonPath().getList(rootPath).size(), 0);
    }

    @Then("I got the resources by labels")
    public void i_got_the_resources_by_labels() {
        Assert.assertNotNull(getBoardService().getLabelsOnBoard(boardId).body().jsonPath().get(rootPath));
        Assert.assertEquals(getBoardService().getLabelsOnBoard(boardId).body().jsonPath().getList(rootPath).size(), 6);
    }

    @Then("The board is removed")
    public void the_board_is_removed() {
        Assert.assertEquals(getBoardService().getBoard(boardId).asString(), "The requested resource was not found.");
        Assert.assertThrows(RuntimeException.class, () -> getBoardService().getBoard(boardId).body().jsonPath().getString(rootPath));
    }

    @Then("Invitation sent by email")
    public void invitation_sent_by_email() {
        checkStatusCode();
    }

    @Then("Invitation sent by email with {string} and {string}")
    public void invitation_sent_by_email_with_option_and_value(String option, String value) {
//        System.out.println(response.body().jsonPath().getList("members").get(1));
//        System.out.println(response.body().jsonPath().getString("."));
        response = getBoardService().inviteMemberToBoardViaEmailWithOptionAndValue(boardId, option, value);
        Map<String, String> member = (Map<String, String>) response.body().jsonPath().getList("members").get(1);

        Assert.assertEquals(member.get(option), value);
    }

    @Then("I got {string} with {string}")
    public void i_got_data_key_value(String field, String value) {

        switch (field) {
            case ("name"):
                value = boardName;
                break;
            case ("dateLastActivity"):
                value = null;
                break;
            case ("desc"):
                value = boardDesc;
                break;
            case ("url"):
                value = boardUrl;
                break;
            case ("prefs/background"):
                Assert.assertEquals(response.body().jsonPath().getString("prefs.background"), value);
                return;
            default:
                System.out.println("Error field");
        }

        Assert.assertEquals(response.body().jsonPath().getString("_value"), value);
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

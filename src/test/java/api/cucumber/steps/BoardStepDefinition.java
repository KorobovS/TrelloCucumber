package api.cucumber.steps;

import api.base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

import static api.base.TestData.BoardTestData.BOARD_NAME;
import static api.base.TestData.BoardTestData.boardId;
import static io.restassured.RestAssured.rootPath;

public class BoardStepDefinition extends BaseTest {

    private Response response;

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

    @Then("A board is created")
    public void a_board_is_created() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("I got resource boards")
    public void i_got_resource_boards() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("I got the resources by lists")
    public void i_got_the_resources_by_lists() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("I got the resources by members")
    public void i_got_the_resources_by_members() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("I got the resources by cards")
    public void i_got_the_resources_by_cards() {
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Then("I got the resources by labels")
    public void i_got_the_resources_by_labels() {
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @And("Three lists presented on the board")
    public void three_lists_presented_on_the_board() {
        response = getBoardService().getListsOfABoard(boardId);
        List<String> lists = response.body().jsonPath().getList(rootPath);

        Assert.assertEquals(lists.size(), 3);
    }

    @And("A board has {string} access")
    public void a_board_has_public_access(String valueOption) {
        Assert.assertEquals(response.body().jsonPath().getString("prefs.permissionLevel"), valueOption);
    }
}

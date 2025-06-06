package api.cucumber.steps;

import api.base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import static api.base.TestData.BoardTestData.boardId;
import static api.base.TestData.LabelsTestData.labelId;
import static api.base.TestData.response;
import static io.restassured.RestAssured.rootPath;

public class LabelStepDefinition extends BaseTest {

    @When("I create a label with a {string} and a {string} on the board")
    public void i_create_a_label_with_a_parameter_and_a_value_on_the_board(String name, String color) {
        Response response = getLabelsService().createLabel(name, color, boardId);
        labelId = response.body().jsonPath().get("id");
    }

    @When("I get label on a board")
    public void i_get_label_on_a_board() {
        response = getLabelsService().getLabel(labelId);
    }

    @When("I update label name {string} and color {string}")
    public void  i_update_label_name_name_and_color_color(String name, String color) {
        response = getLabelsService().updateLabel(labelId, name, color);
    }

    @When("I get labels on a board with {string} = {string}")
    public void i_get_labels_on_a_board_with_parameter_value(String parameter, String value) {
        response = getLabelsService().getLabelWithParameter(labelId, parameter, value);
    }

    @When("I update label {string} with {string}")
    public void i_update_label_parameter_with_value(String parameter, String value) {
        response = getLabelsService().updateFieldLabel(labelId, parameter, value);
    }

    @When("I delete label")
    public void i_delete_label() {
        response = getLabelsService().deleteLabel(labelId);
    }

    @Then("I got label with {string} = {string}")
    public void i_got_label_with_parameter_value(String parameter, String value) {
        if (value == null || value.isEmpty() || value.equals("\"\"")) {
            Assert.assertNull(response.body().jsonPath().getString(parameter));
        } else {
            Assert.assertEquals(response.body().jsonPath().getString(parameter), value);
        }
    }

    @And("I got a label with the specified {string} and {string}")
    public void i_got_a_label_with_the_specified_name_and_color(String name, String color) {
        response = getLabelsService().getLabel(labelId);
        Assert.assertEquals(response.body().jsonPath().getString("name"), name);
        Assert.assertEquals(response.body().jsonPath().getString("color"), color);
    }
}

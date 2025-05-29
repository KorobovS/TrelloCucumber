package api.cucumber.steps;

import api.base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import static api.base.TestData.BoardTestData.boardId;
import static api.base.TestData.LabelsTestData.labelId;

public class LabelStepDefinition extends BaseTest {

    @When("I create a label with a {string} and a {string} on the board")
    public void i_create_a_label_with_a_parameter_and_a_value_on_the_board(String name, String color) {
        Response response = getLabelsSteps().createLabel(name, color, boardId);
        labelId = response.body().jsonPath().get("id");
    }

    @And("I got a label with the specified {string} and {string}")
    public void i_got_a_label_with_the_specified_name_and_color(String name, String color) {
        Response response = getLabelsSteps().getLabel(labelId);
        Assert.assertEquals(response.body().jsonPath().getString("name"), name);
        Assert.assertEquals(response.body().jsonPath().getString("color"), color);
    }
}

package api.cucumber.runners;

import api.controllers.BoardService;
import io.cucumber.java.After;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import static api.base.TestData.BoardTestData.boardId;

@CucumberOptions(
        features = "src/test/resources/features",
//        tags = "@RU",
        glue = {"api.cucumber.steps", "api.cucumber.runners"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)

public class CucumberRunnerTest extends AbstractTestNGCucumberTests {

    @After
    public void deleteBoard() {
        BoardService boardService = new BoardService();
        boardService.deleteBoard(boardId);
    }
}

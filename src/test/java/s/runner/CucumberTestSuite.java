package s.runner;

//import io.cucumber.junit.CucumberOptions;
//import net.serenitybdd.cucumber.CucumberWithSerenity;
//import org.junit.runner.RunWith;
//
//@RunWith(CucumberWithSerenity.class)
//@CucumberOptions(
//        features = "src/test/resources/features/search/student.feature",
//        glue = {"starter/stepdefinitions"},  // Fixed to match your actual package structure
//        plugin = {
//                "html:target/cucumber-reports/html",
//                "json:target/cucumber-reports/json/report.json",
//                "junit:target/cucumber-reports/junit/report.xml"
//        }
//)
//public class CucumberTestSuite {
//}


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
//import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features/search/student.feature",
        glue = "src/test/java/starter/stepdefinitions/StudentStepDefinitions",
        tags = "@regression"
)
public class CucumberTestSuite {
}
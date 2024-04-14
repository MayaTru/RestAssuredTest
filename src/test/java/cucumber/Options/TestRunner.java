package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features", glue = {"StepDefinations"}, tags = "@AddPlaceAPI", plugin = "json:target/jsonReports/cucumber_Report.json")
//tags = "@DeletePlaceAPI" -> to be placed in cucumberOptions to run specific tags 
public class TestRunner {

}

package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty","json:target/cucumber.json", "rerun:target/rerunFail.txt"},
        publish = true,
        features = "@target/rerun.txt",
        glue = "src/test/java/step_definitons"
)
public class FailedTestRunner {
}

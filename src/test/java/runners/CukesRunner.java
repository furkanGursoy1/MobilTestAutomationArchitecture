package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty","json:target/cucumber.json", "rerun:target/rerun.txt"},
        publish = true,
        monochrome = true,
        features = "src/test/resources/features",
        glue = "step_definitons", // senaryoların adımlarının bulundugu paket veya dizin yolu
        dryRun = false, //true olursa ici dolu olmayan stepler icin method olusturur
        tags = "@regression" // hangi senaryoların calıstırılacagını belirler


)
public class CukesRunner {
}
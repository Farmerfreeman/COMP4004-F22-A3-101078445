import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features="src/main/test/resources",
                glue= {"com.example.COMP4004A3.StepDefs"})
public class RunCucumberTest {
}

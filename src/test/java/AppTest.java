import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;


public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
    public void rootTest() {
      goTo("http://localhost:4567/");
      assertThat(pageSource()).contains("Hair Salon");
    }

    @Test
     public void stylistIsCreatedTest() {
       goTo("http://localhost:4567/");
       click("a", withText("Add A New Stylist"));
       fill("#newStylistName").with("Paul Mitchell");
       submit(".btn");
       assertThat(pageSource()).contains("Paul Mitchell");
     }

     @Test
     public void multipleStylistsAreCreated() {
       Stylist myStylist = new Stylist("Paul Mitchell");
       myStylist.save();
       Stylist mySecondStylist = new Stylist("José Eber");
       mySecondStylist.save();
       goTo("http://localhost:4567/");
       assertThat(pageSource()).contains("Paul Mitchell");
       assertThat(pageSource()).contains("José Eber");
     }

     @Test
     public void clientIsCreatedTest() {
       Stylist myStylist = new Stylist("Paul Mitchell");
       myStylist.save();
       goTo("http://localhost:4567/");
       click("a", withText("Paul Mitchell"));
       click("a", withText("Make An Appointment"));
       fill("#newClientName").with("Chloe Kardashian");
       submit(".btn");
       assertThat(pageSource()).contains("Chloe Kardashian");
     }

     @Test
     public void multipleTasksAreCreatedTest() {
       Stylist myStylist = new Stylist("Paul Mitchell");
       myStylist.save();
       Client firstClient = new Client("Chloe Kardashian", myStylist.getId());
       firstClient.save();
       Client secondClient = new Client("Kim Kardashian", myStylist.getId());
       secondClient.save();
       goTo("http://localhost:4567/" + myStylist.getId());
       assertThat(pageSource()).contains("Chloe Kardashian");
       assertThat(pageSource()).contains("Kim Kardashian");
     }

}

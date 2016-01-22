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

    //  @Test
    //  public void multipleCategoriesAreCreated() {
    //    Category myCategory = new Category("Household chores");
    //    myCategory.save();
    //    Category mySecondCategory = new Category("Grocery shopping");
    //    mySecondCategory.save();
    //    goTo("http://localhost:4567/categories");
    //    assertThat(pageSource()).contains("Household chores");
    //    assertThat(pageSource()).contains("Grocery shopping");
    //  }
     //
    //  @Test
    //  public void taskIsCreatedTest() {
    //    goTo("http://localhost:4567/");
    //    click("a", withText("Add a New Category"));
    //    fill("#name").with("Household chores");
    //    submit(".btn");
    //    click("a", withText("Household chores"));
    //    click("a", withText("Add a new task"));
    //    fill("#description").with("Sweep floor");
    //    submit(".btn");
    //    assertThat(pageSource()).contains("Sweep floor");
    //  }
     //
    //  @Test
    //  public void multipleTasksAreCreatedTest() {
    //    goTo("http://localhost:4567/");
    //    click("a", withText("Add a New Category"));
    //    fill("#name").with("Household chores");
    //    submit(".btn");
    //    click("a", withText("Household chores"));
    //    click("a", withText("Add a new task"));
    //    fill("#description").with("Sweep floor");
    //    submit(".btn");
    //    click("a", withText("Add a new task"));
    //    fill("#description").with("Vacuum the rug");
    //    submit(".btn");
    //    assertThat(pageSource()).contains("Sweep floor");
    //    assertThat(pageSource()).contains("Vacuum the rug");
    //  }

}

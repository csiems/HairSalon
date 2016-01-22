import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Client.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Client firstClient = new Client("Kim Kardashian", 1);
    Client secondClient = new Client("Kim Kardashian", 1);
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Client myClient = new Client("Kim Kardashian", 1);
    myClient.save();
    assertTrue(Client.all().get(0).equals(myClient));
  }

  @Test
    public void find_findClientInDatabase_true() {
      Client myClient = new Client("Kim Kardashian", 1);
      myClient.save();
      Client savedClient = Client.find(myClient.getId());
      assertTrue(myClient.equals(savedClient));
    }

  @Test
    public void update_changesClientInDatabase_true() {
      Client myClient = new Client("Kim Kardashian", 1);
      myClient.save();
      myClient.update("Chloe Kardashian");
      assertEquals("Chloe Kardashian", myClient.getName());
    }

  @Test
  public void delete_removesClientInDatabase_true() {
    Client myClient = new Client("Kim Kardashian", 1);
    myClient.save();
    myClient.delete();
    assertEquals(0, Client.all().size());
  }

  @Test
    public void getStylistType_retrievesStylistTypeofClientFromDatabase_cuisineList() {
      Stylist myStylist = new Stylist("Paul Mitchell");
      myStylist.save();
      Client myClient = new Client("Chloe Kardashian", myStylist.getId());
      myClient.save();
      assertEquals("Paul Mitchell", myClient.getStylistName());
    }
}

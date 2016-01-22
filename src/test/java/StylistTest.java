import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Stylist.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Stylist firstStylist = new Stylist("José Eber");
    Stylist secondStylist = new Stylist("José Eber");
    assertTrue(firstStylist.equals(secondStylist));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Stylist myStylist = new Stylist("José Eber");
    myStylist.save();
    assertTrue(Stylist.all().get(0).equals(myStylist));
  }

  @Test
  public void find_findStylistInDatabase_true() {
    Stylist myStylist = new Stylist("José Eber");
    myStylist.save();
    Stylist savedStylist = Stylist.find(myStylist.getId());
    assertTrue(myStylist.equals(savedStylist));
  }

  @Test
  public void update_changesStylistInDatabase_true() {
    Stylist myStylist = new Stylist("José Eber");
    myStylist.save();
    myStylist.update("Paul Mitchell");
    assertEquals("Paul Mitchell", myStylist.getName());
  }

  @Test
  public void delete_removesStylistInDatabase_true() {
    Stylist myStylist = new Stylist("José Eber");
    myStylist.save();
    myStylist.delete();
    assertEquals(0, Stylist.all().size());
  }

    // @Test
    //   public void getClients_retrievesAllClientsFromDatabase_clientsList() {
    //     Stylist myStylist = new Stylist("José Eber");
    //     myStylist.save();
    //     Client firstClient = new Client("Kim Kardashian", myStylist.getId());
    //     firstClient.save();
    //     Client secondClient = new Client("Chloe Kardashian", myStylist.getId());
    //     secondClient.save();
    //     Client[] clients = new Client[] { firstClient, secondClient };
    //     assertTrue(myStylist.getClients().containsAll(Arrays.asList(clients)));
    //   }

}

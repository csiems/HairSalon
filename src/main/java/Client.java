import org.sql2o.*;
import java.util.List;

public class Client {
  private int mId;
  private String mName;
  private int mStylistId;

  public Client (String name, int stylistId) {
    this.mName = name;
    this.mStylistId = stylistId;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public int getStylistId() {
    return mStylistId;
  }

  @Override
  public boolean equals(Object otherClient){
    if (!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getName().equals(newClient.getName()) &&
             this.getId() == newClient.getId() &&
             this.getStylistId() == newClient.getStylistId();
    }
  }

  //C
  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (name, stylist_id) VALUES (:name, :stylist_id)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .addParameter("stylist_id", this.mStylistId)
        .executeUpdate()
        .getKey();
    }
  }

  //R
  public static List<Client> all() {
    String sql = "SELECT id AS mId, name AS mName, stylist_id AS mStylistId FROM clients";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  public static Client find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, stylist_id AS mStylistId FROM clients WHERE id=:id";
      Client myClient = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
      return myClient;
    }
  }

  public String getStylistName() {
    return Stylist.find(mStylistId).getName();
  }

  //U
  public void update(String newName) {
    this.mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET name = :newName WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  //D
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

}

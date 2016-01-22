import java.util.Arrays;
import java.util.List;

import org.sql2o.*;

public class Stylist {
  private int mId;
  private String mName;


  public Stylist (String name) {
    this.mName = name;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  @Override
  public boolean equals(Object otherStylist){
    if(!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getName().equals(newStylist.getName()) &&
        this.getId() == newStylist.getId();
    }
  }

  //C
  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists (name) VALUES (:name)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .executeUpdate()
        .getKey();
    }
  }

  //R
  public static List<Stylist> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM stylists";
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public static Stylist find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM stylists WHERE id=:id";
      Stylist myStylist = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
    return myStylist;
    }
  }

  public List<Client> getClients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, stylist_id AS mCuisineId FROM restaurants WHERE stylist_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeAndFetch(Client.class);
    }
  }

  //U
  public void update(String newName) {
    this.mName = newName;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET name = :newName WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  //D
  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stylists WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

}

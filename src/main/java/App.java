import java.lang.*;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


//home page
    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

//add new stylist
    get("/newstylist", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/newstylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist newStylist = new Stylist(request.queryParams("newStylistName"));
      newStylist.save();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

//view stylist page
    get("/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("template", "templates/stylistpage.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

//update a stylist
    post("/:id/update", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      String updatedName = request.queryParams("updatedName");
      stylist.update(updatedName);
      model.put("stylist", stylist);
      model.put("template", "templates/update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


//remove a stylist
    post("/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
      stylist.delete();
      model.put("template", "templates/delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

//add new client
    get("/:id/newappointment", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("template", "templates/newappointment.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      Client newClient = new Client((request.queryParams("newClientName")), (Integer.parseInt(request.params(":id"))));
      newClient.save();
      model.put("stylist", stylist);
      model.put("template", "templates/stylistpage.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

//view client page
    get("/client/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      model.put("client", client);
      model.put("template", "templates/clientpage.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

//remove a client
    post("/client/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params("id")));
      client.delete();
      model.put("template", "templates/delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

//update a client
    post("/client/:id/update", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params("id")));
      Stylist stylist = Stylist.find(client.getStylistId());
      String updatedName = request.queryParams("updatedName");
      client.update(updatedName);
      model.put("stylist", stylist);
      model.put("template", "templates/update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

//view clients by stylist
    get("/index/assignments", (request, reponse) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("clients", Client.all());
      model.put("template", "templates/assignments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());



  }
}

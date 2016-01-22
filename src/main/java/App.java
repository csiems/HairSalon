import java.lang.*;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

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

    get("/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("template", "templates/stylistpage.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

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

    get("/index/assignments", (request, reponse) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("clients", Client.all());
      model.put("template", "templates/assignments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}

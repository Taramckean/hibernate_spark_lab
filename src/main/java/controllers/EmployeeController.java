package controllers;

import db.DBHelper;
import db.Seeds;
import models.Employee;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.route.HttpMethod.get;

public class EmployeeController {
	public static void main(String[] args) {
		ManagersController managersController = new ManagersController();
		EngineersController engineersController = new EngineersController();
		Seeds.seedData();

		get("/employees", (res, req) -> {
			HashMap<String, Object> model = new HashMap<>();
			List<Employee> employees = DBHelper.getAll(Employee.class);
			model.put("template", "templates/employees/index.vtl.vtl");
			model.put("employees", employees);
			return new ModelAndView(model, "templates/layout.vtl");
		}, new VelocityTemplateEngine());
	}
}


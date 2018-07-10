package controllers;

import com.sun.xml.internal.ws.api.pipe.Engine;
import db.DBHelper;
import models.Department;
import models.Engineer;
import models.Manager;
import spark.ModelAndView;
import spark.route.HttpMethod;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class EngineersController {
	public EngineersController() {
		this.engineerEndpoints();
	}

	private void engineerEndpoints() {
		get("/engineers", (res, req) -> {
			HashMap<String, Object> model = new HashMap<>();
			List<Engineer> engineers = DBHelper.getAll(Engineer.class);
			model.put("template", "templates/engineers/index.vtl");
			model.put("engineers", engineers);
			return new ModelAndView(model, "templates/layout.vtl");
		}, new VelocityTemplateEngine());

		get("/engineers/new", (res, req) -> {
			HashMap<String, Object> model = new HashMap<>();
			List<Department> departments = DBHelper.getAll(Department.class);
			model.put("departments", departments);
			model.put("template", "templates/engineers/create.vtl");
			return new ModelAndView(model, "templates/layout.vtl");

		}, new VelocityTemplateEngine());

		post("/engineers", (req, res) -> {
			int departmentId = Integer.parseInt(req.queryParams("department"));
			Department department = DBHelper.find(departmentId, Department.class);

			String firstName = req.queryParams("firstName");
			String lastName = req.queryParams("lastName");
			int salary = Integer.parseInt(req.queryParams("salary"));


			Engineer newEngineer = new Engineer(firstName, lastName, salary, department);
			DBHelper.save(newEngineer);

			res.redirect("/engineers");
			return null;

		}, new VelocityTemplateEngine());


		get("/engineers/:id", (req, res) -> {
			HashMap<String, Object> model = new HashMap<>();
			int EngineerId = Integer.parseInt(req.params(":id"));
			Manager specificEngineer = DBHelper.find(EngineerId, Engineer.class);

			List<Department> departments = DBHelper.getAll(Department.class);
			model.put("departments", departments);
			model.put("manager", specificEngineer);
			model.put("template", "templates/engineers/edit.vtl");
			return new ModelAndView(model, "templates/layout.vtl");
		}, new VelocityTemplateEngine());

		post("/engineers/edit/:id", (req, res) -> {

			int engineerId = Integer.parseInt(req.params(":id"));
			Manager updatedEngineer = DBHelper.find(engineerId, Engineer.class);

			int departmentId = Integer.parseInt(req.queryParams("department"));
			Department department = DBHelper.find(departmentId, Department.class);


			String firstName = req.queryParams("firstName");
			String lastName = req.queryParams("lastName");
			int salary = Integer.parseInt(req.queryParams("salary"));

			updatedEngineer.setFirstName(firstName);
			updatedEngineer.setLastName(lastName);
			updatedEngineer.setDepartment(department);
			updatedEngineer.setSalary(salary);


			DBHelper.save(updatedEngineer);

			res.redirect("/engineers");
			return null;

		}, new VelocityTemplateEngine());

	}
}
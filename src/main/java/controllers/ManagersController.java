package controllers;

import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;

import spark.template.velocity.VelocityTemplateEngine;
import sun.security.pkcs11.Secmod;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class ManagersController {

	public ManagersController() {
		this.setupEndpoints();
	}

	private void setupEndpoints() {
		get("/managers", (res, req) -> {
			HashMap<String, Object> model = new HashMap<>();
			List<Manager> managers = DBHelper.getAll(Manager.class);
			model.put("template", "templates/managers/index.vtl");
			model.put("managers", managers);
			return new ModelAndView(model, "templates/layout.vtl");
		}, new VelocityTemplateEngine());


		get("/managers/new", (res, req) -> {
			HashMap<String, Object> model = new HashMap<>();
			List<Department> departments = DBHelper.getAll(Department.class);
			model.put("departments", departments);
			model.put("template", "templates/managers/create.vtl");
			return new ModelAndView(model, "templates/layout.vtl");
		}, new VelocityTemplateEngine());

		post("/managers", (req, res) -> {
			int departmentId = Integer.parseInt(req.queryParams("department"));
			Department department = DBHelper.find(departmentId, Department.class);

			String firstName = req.queryParams("firstName");
			String lastName = req.queryParams("lastName");
			int salary = Integer.parseInt(req.queryParams("salary"));
			Double budget = Double.parseDouble(req.queryParams("budget"));

			Manager newManager = new Manager(firstName, lastName, salary, department,budget);
			DBHelper.save(newManager);

			res.redirect("/managers");
			return null;

		}, new VelocityTemplateEngine());

		get("/managers/:id", (req, res) -> {
			HashMap<String, Object> model = new HashMap<>();
			int managerId = Integer.parseInt(req.params(":id"));
			Manager specificManager = DBHelper.find(managerId, Manager.class);

			List<Department> departments = DBHelper.getAll(Department.class);
			model.put("departments", departments);
			model.put("manager", specificManager);
			model.put("template", "templates/managers/edit.vtl");
			return new ModelAndView(model, "templates/layout.vtl");
		}, new VelocityTemplateEngine());


		post("/managers/edit/:id", (req, res) -> {

			int managerId = Integer.parseInt(req.params(":id"));
			Manager updatedManager = DBHelper.find(managerId, Manager.class);

			int departmentId = Integer.parseInt(req.queryParams("department"));
			Department department = DBHelper.find(departmentId, Department.class);


			String firstName = req.queryParams("firstName");
			String lastName = req.queryParams("lastName");
			int salary = Integer.parseInt(req.queryParams("salary"));
			double budget = Double.parseDouble(req.queryParams("budget"));

			updatedManager.setFirstName(firstName);
			updatedManager.setLastName(lastName);
			updatedManager.setDepartment(department);
			updatedManager.setSalary(salary);
			updatedManager.setBudget(budget);

			DBHelper.save(updatedManager);

			res.redirect("/managers");
			return null;

		}, new VelocityTemplateEngine());

		post("/managers/delete/:id", (req,res) -> {
			int managerId = Integer.parseInt(req.params(":id"));

			Manager managerToBeDeleted = DBHelper.find(managerId, Manager.class);

			DBHelper.delete(managerToBeDeleted);
			res.redirect("/managers");
			return null;
		}, new VelocityTemplateEngine());




	}


}
package controllers;

import com.sun.corba.se.spi.ior.ObjectKey;
import db.DBHelper;
import models.Department;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.beans.DefaultPersistenceDelegate;
import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class DepartmentsController {
	public DepartmentsController(){
		this.departmentEndPoints();
	}

	private void departmentEndPoints() {
		get("/departments", (req,res) -> {
			HashMap<String, Object> model = new HashMap<>();
			List<Department> departments = DBHelper.getAll(Department.class);
			model.put("departments", departments);
			model.put("template", "templates/departments/index.vtl");
			return new ModelAndView(model, "templates/layout.vtl");
		}, new VelocityTemplateEngine());

		get("/departments/new", (req, res) -> {
			HashMap<String, Object> model = new HashMap<>();
			List<Department> departments = DBHelper.getAll(Department.class);
			model.put("departments", departments);
			model.put("template", "templates/departments/create.vtl");
			return new ModelAndView(model, "templates/layout.vtl");
		}, new VelocityTemplateEngine());

		post("/departments", (req, res) -> {
			String title = req.queryParams("title");
			Department newDepartment = new Department(title);
			DBHelper.save(newDepartment);

			res.redirect("/departments");
			return null;
		}, new VelocityTemplateEngine());

		get("/departments/:id", (req, res) -> {
			HashMap<String, Object> model = new HashMap<>();
			int departmentId = Integer.parseInt(req.params(":id"));
			Department specificDepartment = DBHelper.find(departmentId, Department.class);
			model.put("department", specificDepartment);
			model.put("template", "templates/departments/edit.vtl");
			return new ModelAndView(model, "templates/layout.vtl");
		}, new VelocityTemplateEngine());

		post("/departments/edit/:id", (req, res) -> {
			int departmentId = Integer.parseInt(req.params(":id"));
			Department updatedDepartment = DBHelper.find(departmentId, Department.class);

			String titleName = req.queryParams("title");

			updatedDepartment.setTitle(titleName);
			DBHelper.save(updatedDepartment);

			res.redirect("/departments");
			return null;
		},new VelocityTemplateEngine());

		post("/departments/delete/:id", (req, res) -> {
			int departmentId = Integer.parseInt(req.params(":id"));

			Department departmentToBeDeleted = DBHelper.find(departmentId, Department.class);

			DBHelper.delete(departmentToBeDeleted);
			res.redirect("/departments");
			return null;
		}, new VelocityTemplateEngine());
	}
}

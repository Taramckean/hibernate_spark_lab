package db;

import models.Department;
import models.Employee;
import models.Engineer;
import models.Manager;

public class Seeds {
	public static void seedData(){
		DBHelper.deleteAll(Engineer.class);
		DBHelper.deleteAll(Employee.class);
		DBHelper.deleteAll(Manager.class);
		DBHelper.deleteAll(Department.class);

		Department department1 = new Department("HR");
		DBHelper.save(department1);
		Department department2 = new Department("IT");
		DBHelper.save(department2);
		Department department3 = new Department("Torture");
		DBHelper.save(department3);
		Department department4 = new Department("Hell");
		DBHelper.save(department4);
		Manager manager = new Manager("Peter", "Griffin", 40000,department1, 100000 );
		DBHelper.save(manager);
		Manager manager2 = new Manager("Homer", "Simpson", 20,department1, 2000 );
		DBHelper.save(manager2);
		Manager manager3 = new Manager("Juno", "myCat", 800000,department1, 4000000 );
		DBHelper.save(manager3);
		Engineer engineer1 = new Engineer("Lois", "Griffin", 29000, department1);
		DBHelper.save(engineer1);
		Engineer engineer2 = new Engineer("Stewie", "Griffin", 27000, department1);
		DBHelper.save(engineer2);
		Engineer engineer3 = new Engineer("Elon", "Musk", 400, department1);
		DBHelper.save(engineer3);
	}
}

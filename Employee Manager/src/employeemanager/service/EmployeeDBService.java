package employeemanager.service;
import java.util.List;
import employeemanager.dao.EmployeeDAO;
import employeemanager.dto.Employee;

public class EmployeeDBService {

	public String insertEmployeeDetailsToDBService(Employee employee) {
		
		
			
			EmployeeDAO employeeDAO=new EmployeeDAO();
			int response=employeeDAO.insertEmployeeDetailsInDB(employee);
			if (response==-1) {
				
				return "Insert Failed";
			}
			else {
				
				return response +" rows effected";
			}
		
	}
	
	public List<Employee> getAllEmployeeDetailsFromDBService(){
		
		
		return new EmployeeDAO().getAllEmployeesDetails();
		
	}
	
	public Employee getEmployeeDetailByID(String id) {
		

			
		return new EmployeeDAO().getEmployeeById(id);
		
		
	}
	
	public int deleteEmployeeByIDService(String id) {
		
		
		return new EmployeeDAO().deleteEmployeeByID(id);
		
	}
	
	public int updateEmployeeByIDService(Employee employee) {
		
	
		return new EmployeeDAO().updateEmployeeByID(employee);
		
	}
	
	
	
}

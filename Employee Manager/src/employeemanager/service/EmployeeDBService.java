package employeemanager.service;
import java.util.List;

import employeemanager.customexception.EmployeeDaoException;
import employeemanager.customexception.EmployeeNotFoundException;
import employeemanager.customexception.EmployeeServiceException;
import employeemanager.dao.EmployeeDAO;
import employeemanager.dto.Employee;

public class EmployeeDBService {

	public int insertEmployeeDetailsToDBService(Employee employee) throws EmployeeServiceException{
		
			EmployeeDAO employeeDAO=new EmployeeDAO();
			try {
				return employeeDAO.createNewEmployee(employee);
			}
			catch(EmployeeDaoException e) {
				
				throw new EmployeeServiceException("Service layer failed to create employees",e);
				
			}
			
	}
	
	public List<Employee> getAllEmployeeDetailsFromDBService() throws EmployeeServiceException, EmployeeNotFoundException{
		
		
		try {
			return new EmployeeDAO().fetchAllEmployees();
		} 
		catch (EmployeeDaoException e) {
			
			throw new EmployeeServiceException("Service layer failed to fetch employees",e);
		} 
		catch (EmployeeNotFoundException e) {
			
			throw e;
		}
		
	}
	
	public Employee getEmployeeDetailByID(String id) throws EmployeeServiceException, EmployeeNotFoundException{
		

		try {	
			
			return new EmployeeDAO().fetchEmployeeById(id);
		}
		catch (EmployeeDaoException e) {
			
			throw new EmployeeServiceException("Service layer failed to fetch employees",e);
		}
		catch (EmployeeNotFoundException e) {
			
			throw e;
		}
		
	}
	
	public int deleteEmployeeByIDService(String id) throws EmployeeServiceException{
		
		try {
			return new EmployeeDAO().deleteEmployeeByID(id);
		}
		catch(EmployeeDaoException e){
			throw new EmployeeServiceException("Service layer failed to delete employees",e);
		}
	}
	
	public int updateEmployeeByIDService(Employee employee) throws EmployeeServiceException{
		
		try {
			return new EmployeeDAO().updateEmployeeByID(employee);
		}
		catch(EmployeeDaoException e){
			throw new EmployeeServiceException("Service layer failed to update employees",e);
		}
		
		
	}
	
	
	
}

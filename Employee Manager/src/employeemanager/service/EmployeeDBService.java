package employeemanager.service;

import java.util.List;
import employeemanager.customexception.EmployeeDaoException;
import employeemanager.customexception.EmployeeNotFoundException;
import employeemanager.customexception.EmployeeServiceException;
import employeemanager.dao.EmployeeDAO;
import employeemanager.dto.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeDBService {
	
	private static final Logger logger = LogManager.getLogger(EmployeeDBService.class);

	public int insertEmployeeDetailsToDBService(Employee employee) throws EmployeeServiceException {
        logger.trace("Entering insertEmployeeDetailsToDBService with employee: {}", employee);
        EmployeeDAO employeeDAO = new EmployeeDAO();
        try {
            int result = employeeDAO.createNewEmployee(employee);
            logger.info("Employee created successfully with ID: {}", employee.getID());
            return result;
        } catch (EmployeeDaoException e) {
            logger.error("Error while creating employee: {}", employee, e);
            if (e.getErrorCode() == 400)
                throw new EmployeeServiceException("Database error while creating employee", e, 400);
            else
                throw new EmployeeServiceException("Service layer failed to create employee", e, 401);
        } finally {
            logger.trace("Exiting insertEmployeeDetailsToDBService");
        }
    }
	
	
	public List<Employee> getAllEmployeeDetailsFromDBService() throws EmployeeServiceException, EmployeeNotFoundException {
        logger.trace("Entering getAllEmployeeDetailsFromDBService");
        try {
            List<Employee> employees = new EmployeeDAO().fetchAllEmployees();
            if (employees.isEmpty()) {
                logger.warn("No employees found in the database");
            } else {
                logger.info("Fetched {} employees from database", employees.size());
            }
            return employees;
        } catch (EmployeeDaoException e) {
            logger.error("Error while fetching all employees", e);
            if (e.getErrorCode() == 400)
                throw new EmployeeServiceException("Database error while fetching all employees", e, 400);
            else
                throw new EmployeeServiceException("Service layer failed to fetch employees", e, 401);
        } catch (EmployeeNotFoundException e) {
            logger.warn("No employee records available");
            throw e;
        } finally {
            logger.trace("Exiting getAllEmployeeDetailsFromDBService");
        }
    }
	
	public Employee getEmployeeDetailByID(String id) throws EmployeeServiceException, EmployeeNotFoundException {
        logger.trace("Entering getEmployeeDetailByID with ID: {}", id);
        try {
            Employee employee = new EmployeeDAO().fetchEmployeeById(id);
            logger.info("Fetched employee with ID: {}", id);
            return employee;
        } catch (EmployeeDaoException e) {
            logger.error("Error while fetching employee with ID: {}", id, e);
            if (e.getErrorCode() == 400)
                throw new EmployeeServiceException("Database error while fetching employee", e, 400);
            else
                throw new EmployeeServiceException("Service layer failed to fetch employees", e, 401);
        } catch (EmployeeNotFoundException e) {
            logger.warn("Employee with ID {} not found", id);
            throw e;
        } finally {
            logger.trace("Exiting getEmployeeDetailByID with ID: {}", id);
        }
    }
	
	public int deleteEmployeeByIDService(String id) throws EmployeeServiceException {
        logger.trace("Entering deleteEmployeeByIDService with ID: {}", id);
        try {
            int result = new EmployeeDAO().deleteEmployeeByID(id);
            logger.info("Deleted employee with ID: {}", id);
            return result;
        } catch (EmployeeDaoException e) {
            logger.error("Error while deleting employee with ID: {}", id, e);
            if (e.getErrorCode() == 400)
                throw new EmployeeServiceException("Database error while deleting employee", e, 400);
            else
                throw new EmployeeServiceException("Service layer failed to delete employees", e, 401);
        } finally {
            logger.trace("Exiting deleteEmployeeByIDService with ID: {}", id);
        }
    }
	
	public int updateEmployeeByIDService(Employee employee) throws EmployeeServiceException {
        logger.trace("Entering updateEmployeeByIDService with employee: {}", employee);
        try {
            int result = new EmployeeDAO().updateEmployeeByID(employee);
            logger.info("Updated employee with ID: {}", employee.getID());
            return result;
        } catch (EmployeeDaoException e) {
            logger.error("Error while updating employee: {}", employee, e);
            if (e.getErrorCode() == 400)
                throw new EmployeeServiceException("Database error while updating employee", e, 400);
            else
                throw new EmployeeServiceException("Service layer failed to update employees", e, 401);
        } finally {
            logger.trace("Exiting updateEmployeeByIDService with employee: {}", employee);
        }
    }
	
}

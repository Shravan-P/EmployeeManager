package employeemanager.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import employeemanager.constant.SqlConstant;
import employeemanager.customexception.EmployeeDaoException;
import employeemanager.customexception.EmployeeNotFoundException;
import employeemanager.dto.Employee;
import employeemanager.util.DBUtil;

public class EmployeeDAO {

    public int createNewEmployee(Employee employee) throws EmployeeDaoException{
        
        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement statement = connection.prepareStatement(SqlConstant.ADD_NEW_EMPLOYEE)) {

            statement.setString(1, employee.getID());
            statement.setString(2, employee.getFname());
            statement.setString(3, employee.getLname());
            statement.setString(4, employee.getphn());
            statement.setString(5, employee.getemail());
            statement.setDate(6, Date.valueOf(employee.getJoindate()));
            statement.setString(7, employee.getstatus());

            return statement.executeUpdate();

        } catch (SQLException e) {
        	
            throw new EmployeeDaoException("Database error while creating employee", e);
        }
    }

    public List<Employee> fetchAllEmployees() throws EmployeeDaoException,EmployeeNotFoundException {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement stmt = connection.prepareStatement(SqlConstant.SELECT_ALL_EMPLOYEES);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getString(SqlConstant.ID_COLUMN),
                    rs.getString(SqlConstant.FIRST_NAME_COLUMN),
                    rs.getString(SqlConstant.LAST_NAME_COLUMN),
                    rs.getString(SqlConstant.PHN_COLUMN),
                    rs.getString(SqlConstant.EMAIL_COLUMN),
                    rs.getDate(SqlConstant.JOINDATE_COLUMN).toString(),
                    rs.getString(SqlConstant.STATUS_COLUMN)
                );
                employees.add(emp);
            }
            if (employees.size()==0)
            	throw new EmployeeNotFoundException("No employees found");

        } catch (SQLException e) {
        	
        	throw new EmployeeDaoException("Database error while fetching all employees", e);
        }

        return employees;
    }

    public Employee fetchEmployeeById(String id) throws EmployeeDaoException,EmployeeNotFoundException {
        Employee emp = null;

        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement stmt = connection.prepareStatement(SqlConstant.SELECT_EMPLOYEE_BY_ID)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                emp = new Employee(
            		rs.getString(SqlConstant.ID_COLUMN),
                    rs.getString(SqlConstant.FIRST_NAME_COLUMN),
                    rs.getString(SqlConstant.LAST_NAME_COLUMN),
                    rs.getString(SqlConstant.PHN_COLUMN),
                    rs.getString(SqlConstant.EMAIL_COLUMN),
                    rs.getDate(SqlConstant.JOINDATE_COLUMN).toString(),
                    rs.getString(SqlConstant.STATUS_COLUMN)
                );
            }
            else
            	throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");

        } catch (SQLException e) {
        	
        	throw new EmployeeDaoException("Database error while fetchig employee", e);
        }

        return emp;
    }

    public int deleteEmployeeByID(String empId) throws EmployeeDaoException{

        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement statement = connection.prepareStatement(SqlConstant.DELETE_EMPLOYEE_BY_ID)) {

            statement.setString(1, empId);
            return statement.executeUpdate();

        } catch (SQLException e) {
        	
        	throw new EmployeeDaoException("Database error while deleting employee", e);
        }
    }

    public int updateEmployeeByID(Employee emp) throws EmployeeDaoException{
        

        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement statement = connection.prepareStatement(SqlConstant.UPDATE_EMPLOYEE_BY_ID)) {

            statement.setString(1, emp.getFname());
            statement.setString(2, emp.getLname());
            statement.setString(3, emp.getphn());
            statement.setString(4, emp.getemail());
            statement.setDate(5, Date.valueOf(emp.getJoindate()));
            statement.setString(6, emp.getstatus());
            statement.setString(7, emp.getID());

            return statement.executeUpdate();

        } catch (SQLException e) {
        	
        	throw new EmployeeDaoException("Database error while updating employee", e);
        }
    }
}

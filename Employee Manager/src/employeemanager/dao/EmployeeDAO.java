package employeemanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import employeemanager.constant.SqlConstant;
import employeemanager.customexception.EmployeeDaoException;
import employeemanager.customexception.EmployeeNotFoundException;
import employeemanager.dto.Employee;
import employeemanager.util.DBUtil;

public class EmployeeDAO {

    private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);

    public int createNewEmployee(Employee employee) throws EmployeeDaoException {
        logger.trace("Entering createNewEmployee with employee: {}", employee);
        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement statement = connection.prepareStatement(SqlConstant.ADD_NEW_EMPLOYEE)) {

            statement.setString(1, employee.getID());
            statement.setString(2, employee.getFname());
            statement.setString(3, employee.getLname());
            statement.setString(4, employee.getphn());
            statement.setString(5, employee.getemail());
            statement.setDate(6, Date.valueOf(employee.getJoindate()));
            statement.setString(7, employee.getstatus());

            int rows = statement.executeUpdate();
            logger.info("Inserted {} employee record(s) into DB. Employee ID: {}", rows, employee.getID());
            return rows;

        } catch (SQLException e) {
            logger.error("SQL error while creating employee: {}", employee, e);
            throw new EmployeeDaoException("Database error while creating employee", e, 400);
        } finally {
            logger.trace("Exiting createNewEmployee");
        }
    }

    public List<Employee> fetchAllEmployees() throws EmployeeDaoException, EmployeeNotFoundException {
        logger.trace("Entering fetchAllEmployees");
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

            if (employees.isEmpty()) {
                logger.warn("No employees found in the database");
                throw new EmployeeNotFoundException("No employees found");
            }

            logger.info("Fetched {} employees from database", employees.size());
            return employees;

        } catch (SQLException e) {
            logger.error("SQL error while fetching all employees", e);
            throw new EmployeeDaoException("Database error while fetching all employees", e, 400);
        } finally {
            logger.trace("Exiting fetchAllEmployees");
        }
    }

    public Employee fetchEmployeeById(String id) throws EmployeeDaoException, EmployeeNotFoundException {
        logger.trace("Entering fetchEmployeeById with ID: {}", id);
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
                logger.info("Employee fetched with ID: {}", id);
            } else {
                logger.warn("Employee with ID {} not found", id);
                throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
            }

            return emp;

        } catch (SQLException e) {
            logger.error("SQL error while fetching employee with ID: {}", id, e);
            throw new EmployeeDaoException("Database error while fetching employee", e, 400);
        } finally {
            logger.trace("Exiting fetchEmployeeById with ID: {}", id);
        }
    }

    public int deleteEmployeeByID(String empId) throws EmployeeDaoException {
        logger.trace("Entering deleteEmployeeByID with ID: {}", empId);

        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement statement = connection.prepareStatement(SqlConstant.DELETE_EMPLOYEE_BY_ID)) {

            statement.setString(1, empId);
            int rows = statement.executeUpdate();

            if (rows > 0) {
                logger.info("Deleted employee with ID: {}", empId);
            } else {
                logger.warn("No employee found to delete with ID: {}", empId);
            }
            return rows;

        } catch (SQLException e) {
            logger.error("SQL error while deleting employee with ID: {}", empId, e);
            throw new EmployeeDaoException("Database error while deleting employee", e, 400);
        } finally {
            logger.trace("Exiting deleteEmployeeByID with ID: {}", empId);
        }
    }

    public int updateEmployeeByID(Employee emp) throws EmployeeDaoException {
        logger.trace("Entering updateEmployeeByID with employee: {}", emp);

        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement statement = connection.prepareStatement(SqlConstant.UPDATE_EMPLOYEE_BY_ID)) {

            statement.setString(1, emp.getFname());
            statement.setString(2, emp.getLname());
            statement.setString(3, emp.getphn());
            statement.setString(4, emp.getemail());
            statement.setDate(5, Date.valueOf(emp.getJoindate()));
            statement.setString(6, emp.getstatus());
            statement.setString(7, emp.getID());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                logger.info("Updated employee with ID: {}", emp.getID());
            } else {
                logger.warn("No employee found to update with ID: {}", emp.getID());
            }
            return rows;

        } catch (SQLException e) {
            logger.error("SQL error while updating employee: {}", emp, e);
            throw new EmployeeDaoException("Database error while updating employee", e, 400);
        } finally {
            logger.trace("Exiting updateEmployeeByID with employee: {}", emp);
        }
    }
}

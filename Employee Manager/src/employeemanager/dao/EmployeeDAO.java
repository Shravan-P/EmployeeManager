package employeemanager.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import employeemanager.dto.Employee;
import employeemanager.util.DBUtil;

public class EmployeeDAO {

    public int insertEmployeeDetailsInDB(Employee employee) {
        String insertEmployeeQuery = "INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement statement = connection.prepareStatement(insertEmployeeQuery)) {

            statement.setString(1, employee.getID());
            statement.setString(2, employee.getFname());
            statement.setString(3, employee.getLname());
            statement.setString(4, employee.getphn());
            statement.setString(5, employee.getemail());
            statement.setDate(6, Date.valueOf(employee.getJoindate()));
            statement.setString(7, employee.getstatus());

            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Employee> getAllEmployeesDetails() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";

        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getString("ID"),
                    rs.getString("Fname"),
                    rs.getString("Lname"),
                    rs.getString("phn"),
                    rs.getString("email"),
                    rs.getDate("Joindate").toString(),
                    rs.getString("status")
                );
                employees.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public Employee getEmployeeById(String id) {
        Employee emp = null;
        String sql = "SELECT * FROM employee WHERE ID = ?";

        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                emp = new Employee(
                    rs.getString("ID"),
                    rs.getString("Fname"),
                    rs.getString("Lname"),
                    rs.getString("phn"),
                    rs.getString("email"),
                    rs.getDate("Joindate").toString(),
                    rs.getString("status")
                );
            }

        } catch (SQLException e) {
            System.out.println("Not Found");
        }

        return emp;
    }

    public int deleteEmployeeByID(String empId) {
        String sql = "DELETE FROM employee WHERE ID = ?";

        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, empId);
            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int updateEmployeeByID(Employee emp) {
        String sql = "UPDATE employee SET Fname=?, Lname=?, phn=?, email=?, Joindate=?, status=? WHERE ID=?";

        try (Connection connection = DBUtil.getConnectionToDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, emp.getFname());
            statement.setString(2, emp.getLname());
            statement.setString(3, emp.getphn());
            statement.setString(4, emp.getemail());
            statement.setDate(5, Date.valueOf(emp.getJoindate()));
            statement.setString(6, emp.getstatus());
            statement.setString(7, emp.getID());

            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}

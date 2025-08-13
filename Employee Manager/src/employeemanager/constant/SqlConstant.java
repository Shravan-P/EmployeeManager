package employeemanager.constant;

public class SqlConstant {
	
	public static final String ID_COLUMN="ID";
	public static final String FIRST_NAME_COLUMN="FIRST_NAME";
	public static final String LAST_NAME_COLUMN="LAST_NAME";
	public static final String PHN_COLUMN="PHN";
	public static final String EMAIL_COLUMN="EMAIL";
	public static final String JOINDATE_COLUMN="JOINDATE";
	public static final String STATUS_COLUMN="STATUS";
	
	public static final String ADD_NEW_EMPLOYEE= "INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?, ?)";
	public static final String SELECT_ALL_EMPLOYEES="SELECT id,first_name,last_name,phn,email,joindate,status FROM employee";
	public static final String SELECT_EMPLOYEE_BY_ID="SELECT id,first_name,last_name,phn,email,joindate,status FROM employee WHERE id=?";
	public static final String DELETE_EMPLOYEE_BY_ID="DELETE FROM employee WHERE id=?";
	public static final String UPDATE_EMPLOYEE_BY_ID = "UPDATE employee SET first_name=?, last_name=?, phn=?, email=?, joindate=?, status=? WHERE id=?";
}

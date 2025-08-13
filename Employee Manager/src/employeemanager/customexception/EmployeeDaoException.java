package employeemanager.customexception;

public class EmployeeDaoException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmployeeDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}


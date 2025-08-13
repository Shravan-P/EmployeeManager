package employeemanager.customexception;

public class EmployeeServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmployeeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

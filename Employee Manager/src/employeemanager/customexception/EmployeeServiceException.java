package employeemanager.customexception;

public class EmployeeServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	private int errorCode;
	public EmployeeServiceException(String message, Throwable cause,int errorCode) {
        super(message, cause);
        this.errorCode=errorCode;
    }
	public int getErrorCode() {
		return errorCode;
	}
}

package employeemanager.customexception;

public class EmployeeDaoException extends Exception {
	private static final long serialVersionUID = 1L;
	private int errorCode;
	public EmployeeDaoException(String message, Throwable cause,int errorCode) {
        super(message, cause);
        this.errorCode=errorCode;
    }
	public int getErrorCode() {
		return errorCode;
	}

}


package employeemanager.dto;

public class ResponseDto<D> {
	
	private String status;
	private D data;
	private String message;
	
	public ResponseDto() {
		
	}
	
	public ResponseDto(String status,D data,String message) {
		this.status=status;
		this.data=data;
		this.message=message;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public D getData() {
		return data;
	}

	public void setData(D data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}

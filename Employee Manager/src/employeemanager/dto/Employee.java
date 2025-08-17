package employeemanager.dto;

public class Employee {
	
	private String ID;
	private String Fname;
	private String Lname;
	private String phn;
	private String email;
	private String Joindate;
	private String status;
	

	public void employeedetails(String ID,String Fname,String Lname,String phn,String email,String Joindate,String status) {
		
		this.ID=ID;
		this.Fname=Fname;
		this.Lname=Lname;
		this.phn=phn;
		this.email=email;
		this.Joindate=Joindate;
		this.status=status;
		
	}
	public Employee (String ID,String Fname,String Lname,String phn,String email,String Joindate,String status) {
		
		this.ID=ID;
		this.Fname=Fname;
		this.Lname=Lname;
		this.phn=phn;
		this.email=email;
		this.Joindate=Joindate;
		this.status=status;
		
	}
	
	public Employee() {
		
	}
	
	public String getID() {
		return ID;
	}
	
	public String getFname() {
		return Fname;
	}
	
	public String getLname() {
		return Lname;
	}
	
	public String getphn() {
		return phn;
	}
	
	public String getemail() {
		return email;
	}
	
	public String getJoindate() {
		return Joindate;
	}
	
	public String getstatus() {
		return status;
	}

}

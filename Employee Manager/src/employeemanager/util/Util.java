package employeemanager.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import employeemanager.dto.Employee;

public class Util {

	public static Boolean isEmail(String email) {
		
		if (email.matches("^[a-z]+[a-z0-9]+(.[a-z]+)*@[a-z]+(.[a-z]+)+$")) {
			
			return true;
		}
		
		return false;
	}
	public static Boolean isId(String id) {
		
		if(id.matches("^[0-9]+$")) {
			
			return true;
		}
		return false;
	}
	public static Boolean isId(String id,List<Employee> emplist) {
		
		if(id.matches("^[0-9]+$")) {
			
			for(Employee emp: emplist) {
				
				if (emp.getID().equals(id)) {
					
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public static Boolean isPhn(String phn) {
		
		if (phn.matches("^[0-9]{10}$")) {
			
			return true;
		}
		return false;
	}
	
	public static Boolean isName(String fname,String lname){
		
		if (fname.matches("^[A-Za-z]+$") && lname.matches("^[A-Za-z]+$")) {
			
			return true;
		}
		return false;
		
	}
	
	public static Boolean isJoindate(String joindate) {
		
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			
			LocalDate.parse(joindate,formatter);
			return true;
		}
		catch(Exception e){
			
			return false;
		}
	}
	
	public static Boolean isstatus(String status) {
		
		if (status.equalsIgnoreCase("true") || status.equalsIgnoreCase("false")) {
			
			return true;
		}
		return false;
	}
	

}

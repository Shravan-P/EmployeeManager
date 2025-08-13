package employeemanager.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import employeemanager.constant.MessageConstant;
import employeemanager.constant.StatusConstant;
import employeemanager.customexception.EmployeeNotFoundException;
import employeemanager.customexception.EmployeeServiceException;
import employeemanager.dto.Employee;
import employeemanager.dto.ResponseDto;
import employeemanager.service.EmployeeDBService;
import employeemanager.util.Util;

public class EmployeeController{
	
	
	public ResponseDto<List<Employee>> readEmployeeDetailsFromTextFile(File file) throws IOException {
		
		ResponseDto<List<Employee>> response=new ResponseDto<List<Employee>>();
		
		BufferedReader br=new BufferedReader(new FileReader(file));
		
		String str="";
		String strarray[];
		List<Employee> emplist=new ArrayList<>();
		
		while ((str=br.readLine())!=null) {
			
			response.setMessage("Successfully read the employee details");
			strarray=str.split("\\$");
			if (strarray.length==7) {
				Employee emp = new Employee();
				emp.employeedetails(strarray[0],strarray[1],strarray[2],strarray[3],strarray[4],strarray[5],strarray[6]);
				emplist.add(emp);
			}
			else
				response.setMessage("Skipped some invalid entries");
			
		}
		br.close();
		response.setData(emplist);
		response.setStatus("OK");
		return response;
		
		
	}
	
	public ResponseDto<String> writeEmployeeDetailstoCsvFromTextFile(File readfile,File writefile) throws IOException {
		
		ResponseDto<String> response=new ResponseDto<String>();
		
		BufferedWriter bw=new BufferedWriter(new FileWriter(writefile));
		BufferedReader br=new BufferedReader(new FileReader(readfile));

		String str;
		String strarray[];
		String csvstr;
		
		while((str=br.readLine())!=null) {
			
			response.setMessage("Sucessfully written employee details from text file to csv file");
			strarray=str.split("\\$");
			if (strarray.length==7) {
				
				csvstr=String.join(",", strarray);
				bw.write(csvstr+"\n");
			}
			else
				response.setMessage("Skipped Invalid Entries");
		}
		bw.close();
		br.close();
		response.setStatus("OK");
		return response;
		
	}
			
	public ResponseDto<String> writeEmpolyeeDetailstoCsvFromConsole(File writefile) throws IOException {
		
		ResponseDto<String> response=new ResponseDto<String>();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String c;
		String id;
		String fname;
		String lname;
		String phn;
		String email;
		String joindate;
		String status;
		Boolean isid;
		Boolean isname;
		Boolean isemail;
		Boolean isjoindate;
		Boolean isphn;
		Boolean isstatus;
		StringBuffer message =new StringBuffer("Problem in");
		
		PrintWriter pw=new PrintWriter(new FileWriter(writefile,true));
		
		
		do{
			//Reading employee details from console
			System.out.println("Enter Employee Details:\n");
			System.out.println("Enter ID:");
			id=br.readLine();
			isid=Util.isId(id);
			System.out.println("Enter Firstname:");
			fname=br.readLine();
			System.out.println("Enter Lastname:");
			lname=br.readLine();
			isname=Util.isName(fname, lname);
			System.out.println("Enter Phone no:");
			phn=br.readLine();
			isphn=Util.isPhn(phn);
			System.out.println("Enter Email:");
			email=br.readLine();
			isemail=Util.isEmail(email);
			System.out.println("Enter Joining Date:");
			joindate=br.readLine();
			isjoindate=Util.isJoindate(joindate);
			System.out.println("Enter Status(True/False):");
			status=br.readLine();
			isstatus=Util.isstatus(status);
			
			
			//Validating
			if (isid && isname && isemail && isjoindate && isphn && isstatus) {
				
				String empdetails=id+","+fname+","+lname+","+phn+","+email+","+joindate+","+status;
				pw.println(empdetails);	
			}
			else {
				
				if(!isid)
					message.append(" id");
				if(!isname)
					message.append(" name");
				if(!isemail)
					message.append(" email");
				if(!isjoindate)
					message.append(" joining date");
				if(!isstatus)
					message.append(" status");
				if(!isphn)
					message.append(" phn no.");
				System.out.println(message);
			}
			
			System.out.println("Do you want to enter another employee details(Y/N)");
			c=br.readLine();		
			
		}while(c.equals("Y"));
		
		pw.close();
		response.setStatus("OK");
		response.setMessage("Sucessfully added employee details");
		return response;
		
	}
	
	//Database Controls

	public ResponseDto<String> insertEmployeeDetailsToDB(Employee employee) {
	    
	    if (Util.isId(employee.getID()) && Util.isName(employee.getFname(), employee.getLname()) && Util.isPhn(employee.getphn()) &&
	    		Util.isEmail(employee.getemail()) && Util.isJoindate(employee.getJoindate()) && Util.isstatus(employee.getstatus())) {
	    	
	    	try {
	    		new EmployeeDBService().insertEmployeeDetailsToDBService(employee);
	    		return new ResponseDto<>(StatusConstant.SUCCESS_CODE,null,MessageConstant.CREATE_NEW_EMPLOYEE_SUCCESS);
	    	}
	    	catch(EmployeeServiceException e) {
	    		return new ResponseDto<>(StatusConstant.ERROR_CODE,null,e.getMessage());
	    	}
	    	catch(Exception e) {
	    		return new ResponseDto<>(StatusConstant.ERROR_CODE,null,e.getMessage());
	    	}
	    }
	    else
	    	return new ResponseDto<>(StatusConstant.ERROR_CODE,null,MessageConstant.INVALID_EMPLOYEE_DETAILS);
	}

	
	public ResponseDto<List<Employee>> getAllEmployeeDetailsFromDB(){
		
		try {
			
			List<Employee> employees=new EmployeeDBService().getAllEmployeeDetailsFromDBService();
			return new ResponseDto<>(StatusConstant.SUCCESS_CODE,employees,MessageConstant.FETCH_ALL_EMPLOYEES_SUCCESS);
		}
		catch(EmployeeServiceException e) {
			
			return new ResponseDto<>(StatusConstant.ERROR_CODE,null,e.getMessage());
		}
		catch(EmployeeNotFoundException e) {
			return new ResponseDto<>(StatusConstant.ERROR_CODE,null,e.getMessage());
		}
		catch(Exception e) {
			return new ResponseDto<>(StatusConstant.ERROR_CODE,null,e.getMessage());
		}
	}
	
	public ResponseDto<Employee> getEmployeeDetailsByIdFromDB(String id){
		try {
			
			Employee employee=new EmployeeDBService().getEmployeeDetailByID(id);
			return new ResponseDto<>(StatusConstant.SUCCESS_CODE,employee,MessageConstant.FETCH_EMPLOYEE_SUCCESS);
		}
		catch(EmployeeServiceException e) {
			
			return new ResponseDto<>(StatusConstant.ERROR_CODE,null,e.getMessage());
		}
		catch(EmployeeNotFoundException e) {
			return new ResponseDto<>(StatusConstant.ERROR_CODE,null,e.getMessage());
		}
		catch(Exception e) {
			return new ResponseDto<>(StatusConstant.ERROR_CODE,null,e.getMessage());
		}
	
	}
	
	public ResponseDto<String> deleteEmployeeByIDFromDB(String id){
		
		try {
			int roweffected=new EmployeeDBService().deleteEmployeeByIDService(id);
			if(roweffected==1)
				return new ResponseDto<>(StatusConstant.SUCCESS_CODE,null,MessageConstant.DELETE_EMPLOYEE_SUCCESS);
			else
				return new ResponseDto<>(StatusConstant.ERROR_CODE,null,MessageConstant.EMPLOYEE_NOT_FOUND);
		}
		catch(EmployeeServiceException e) {
			return new ResponseDto<>(StatusConstant.ERROR_CODE,null,e.getMessage());
		}
		catch(Exception e) {
			return new ResponseDto<>(StatusConstant.ERROR_CODE,null,e.getMessage());
		}
	}
	
	public ResponseDto<String> updateEmployeeByIDInDB(Employee employee){
		
		try {
			new EmployeeDBService().updateEmployeeByIDService(employee);
			return new ResponseDto<>(StatusConstant.SUCCESS_CODE,null,MessageConstant.UPDATE_EMPLOYEE_SUCCESS);
		}
		catch(EmployeeServiceException e) {
			return new ResponseDto<>(StatusConstant.ERROR_CODE,null,e.getMessage());
		}
		catch(Exception e) {
			return new ResponseDto<>(StatusConstant.ERROR_CODE,null,e.getMessage());
		}
	}
}

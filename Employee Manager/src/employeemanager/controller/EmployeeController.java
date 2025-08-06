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

	public ResponseDto<String> insertEmployeeDetailsToDB() {
	    
	    ResponseDto<String> response = new ResponseDto<>();
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    try {
	        
	        System.out.println("Enter Employee Details:\nEmployee ID:");
	        String id = br.readLine();
	        
	        System.out.println("Enter Firstname:");
	        String firstname = br.readLine();
	        
	        System.out.println("Enter Lastname:");
	        String lastname = br.readLine();
	        
	        System.out.println("Enter Phone no:");
	        String phn = br.readLine();
	        
	        System.out.println("Enter Email:");
	        String email = br.readLine();
	        
	        System.out.println("Enter Joining Date:");
	        String joinDate = br.readLine();
	        
	        System.out.println("Enter Status(True/False):");
	        String status = br.readLine();
	        
	        Employee employee = new Employee(id, firstname, lastname, phn, email, joinDate, status);
	        
	        String message = new EmployeeDBService().insertEmployeeDetailsToDBService(employee);
	        
	        response.setMessage(message);
	        
	    } catch (Exception e) {
	        response.setMessage("Error occurred while inserting employee data.");
	    }
	    
	    return response;
	}

	
	public ResponseDto<List<Employee>> getAllEmployeeDetailsFromDB(){
		
		ResponseDto<List<Employee>> response=new ResponseDto<List<Employee>>();
		List<Employee> employees=new EmployeeDBService().getAllEmployeeDetailsFromDBService();
		
		response.setData(employees);
		
		return response;
		
		
		
	}
	
	public ResponseDto<Employee> getEmployeeDetailByIdFromDB(){
		
		ResponseDto<Employee> response=new ResponseDto<Employee>();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nEnter Id of Employee:");
	
		try {
			
			String id=br.readLine();
			Employee employee=new EmployeeDBService().getEmployeeDetailByID(id);
			response.setData(employee);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			response.setData(null);
		}
		return response;
	}
	
	public ResponseDto<String> deleteEmployeeByIDFromDB(){
		
		ResponseDto<String> response=new ResponseDto<String>();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nEnter Id of Employee to Delete:");
		
		try {
			
			String id=br.readLine();
			int rowAffected=new EmployeeDBService().deleteEmployeeByIDService(id);
			if (rowAffected==1)
				response.setMessage("Deletion SucessFull");
			else
				response.setMessage("Deletion Failed");
			
		}
		catch(IOException e) {
			
			response.setMessage("Deletion Failed");
		}
		
		return response;
	
	}
	
	public ResponseDto<String> updateEmployeeByIDInDB(){
		
		ResponseDto<String> response=new ResponseDto<String>();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		try{
			
			System.out.println("Enter ID of Employee to update:");
			String id=br.readLine();
			
			System.out.println("\nEnter Employee Details:\nEnter Firstname:");
			String firstname=br.readLine();
			
			System.out.println("Enter Lastname:");
			String lastname=br.readLine();
			
			System.out.println("Enter Phone no:");
			String phn=br.readLine();
			
			System.out.println("Enter Email:");
			String email=br.readLine();
			
			System.out.println("Enter Joining Date:");
			String joinDate=br.readLine();
			
			System.out.println("Enter Status(True/False):");
			String status=br.readLine();
			
			Employee employee=new Employee(id,firstname,lastname,phn,email,joinDate,status);
			
			int rowsAffected=new EmployeeDBService().updateEmployeeByIDService(employee);
			
			if (rowsAffected==1)
				response.setMessage("Updation SucessFull");
			else
				response.setMessage("Updation Failed");
			
		}
		catch (Exception e) {
			response.setMessage("Updation Failed");
		}
		return response;
	}
}

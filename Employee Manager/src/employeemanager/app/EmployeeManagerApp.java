package employeemanager.app;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import employeemanager.constant.MessageConstant;
import employeemanager.constant.StatusConstant;
import employeemanager.controller.EmployeeController;
import employeemanager.dto.Employee;
import employeemanager.dto.ResponseDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeManagerApp{
	
	private static final Logger logger = LogManager.getLogger(EmployeeManagerApp.class);
	
	public static void main(String args[]) throws SQLException {		
		
		logger.info("Starting Employee Manager Application...");
		String readfilename;
		String writefilename;
		EmployeeController controller=new EmployeeController();
		
		
		try {
			
			Scanner scan=new Scanner(System.in);
			
			System.out.println("Enter List of Actions on Employee Database are:"
					+ "\n1.Create new employee"
					+ "\n2.Get all employees"
					+ "\n3.Get employee by id"
					+ "\n4.Delete employee by id"
					+ "\n5.Update employee by id"
					+ "\n\nEnter option:");
			
			int employeeDB_Option=scan.nextInt();
			scan.nextLine();
			
			switch(employeeDB_Option) {
			
				case 1:
					//Create new employee
				
				    try {
				        
				        System.out.println("Enter Employee Details:\nEmployee ID:");
				        String id = scan.nextLine();
				        
				        System.out.println("Enter Firstname:");
				        String firstname = scan.nextLine();
				        
				        System.out.println("Enter Lastname:");
				        String lastname = scan.nextLine();
				        
				        System.out.println("Enter Phone no:");
				        String phn = scan.nextLine();
				        
				        System.out.println("Enter Email:");
				        String email = scan.nextLine();
				        
				        System.out.println("Enter Joining Date:");
				        String joinDate = scan.nextLine();
				        
				        System.out.println("Enter Status(True/False):");
				        String status = scan.nextLine();
				        
				        Employee employee = new Employee(id, firstname, lastname, phn, email, joinDate, status);
				        
				        ResponseDto<String> createResponse=controller.insertEmployeeDetailsToDB(employee);
				        
				        System.out.println(createResponse.getMessage());
				        
				        
				    } catch (Exception e) {
				    	System.out.println(MessageConstant.CREATE_NEW_EMPLOYEE_FAILED);
				    }
					
					break;
				case 2:
					//Get all employees
					ResponseDto<List<Employee>> fetchEmployeesResponse=controller.getAllEmployeeDetailsFromDB();
					
					if(fetchEmployeesResponse.getStatus().equals(StatusConstant.SUCCESS_CODE)) {
						List<Employee> employees=fetchEmployeesResponse.getData();
						
						for (Employee empdetails:employees) {
							System.out.println(empdetails.getID()+" "+empdetails.getFname()+" "+empdetails.getLname());
						}
					}
					else
						System.out.println(fetchEmployeesResponse.getMessage());
					
					break;
				case 3:
					//Get employee by id

					System.out.println("\nEnter ID of Employee:");
					try {
						
						String id=scan.nextLine();
						ResponseDto<Employee> fetchEmployeeResponse=controller.getEmployeeDetailsByIdFromDB(id);
						
						if(fetchEmployeeResponse.getStatus().equals(StatusConstant.SUCCESS_CODE)) {
					        Employee employee=fetchEmployeeResponse.getData();
					        System.out.println(employee.getID()+" "+employee.getFname()+" "+employee.getLname());
						}
						else
							System.out.println(fetchEmployeeResponse.getMessage());
						
					} catch (Exception e) {
						
						System.out.println(e.getMessage());
					}
					
					break;
			        
				case 4:
					//Delete Employee By ID
					System.out.println("\nEnter Id of Employee to Delete:");
					try {
						
						String id=scan.nextLine();	
						ResponseDto<String> deletedEmployeeResponse=controller.deleteEmployeeByIDFromDB(id);
				        System.out.println(deletedEmployeeResponse.getMessage());
					}
					catch(Exception e) {
						
						System.out.println(e.getMessage());
					}
			        
			        
			        break;
			        
				case 5:
					//Update employee by ID
					try{
						
						System.out.println("Enter ID of Employee to update:");
						String id=scan.nextLine();
						
						if(controller.getEmployeeDetailsByIdFromDB(id).getStatus().equals(StatusConstant.SUCCESS_CODE)) {
						
							System.out.println("\nEnter Employee Details:\nEnter Firstname:");
							String firstname=scan.nextLine();
							
							System.out.println("Enter Lastname:");
							String lastname=scan.nextLine();
							
							System.out.println("Enter Phone no:");
							String phn=scan.nextLine();
							
							System.out.println("Enter Email:");
							String email=scan.nextLine();
							
							System.out.println("Enter Joining Date:");
							String joinDate=scan.nextLine();
							
							System.out.println("Enter Status(True/False):");
							String status=scan.nextLine();
							
							Employee employee=new Employee(id,firstname,lastname,phn,email,joinDate,status);
							ResponseDto<String> updateResponse=controller.updateEmployeeByIDInDB(employee);
					        System.out.println(updateResponse.getMessage());
						}
						else
							System.out.println(MessageConstant.EMPLOYEE_NOT_FOUND);
						
						
					}
					catch (Exception e) {
						
					}
			        
			        break;
			        
			    default:
			    	System.out.println("Invalid input");
			
			}

			
			System.out.println("\nThe List of Actions on Employee Details Are:"
					+ "\n1.Read Employee details from text file"
					+ "\n2.Write Employee details to CSV from a text file"
					+ "\n3.Enter Employee datails and add to CSV file"
					+ "\n\nEnter the Option(1,2,3):");
			
			
			int employeeDetailsOption=scan.nextInt();
			scan.nextLine();
	
			//phase 1
			if (employeeDetailsOption==1){
				
				System.out.println("Enter the textfile to read:");
				readfilename=scan.nextLine();
				
				if (readfilename!=null && !readfilename.isBlank()) {
					
					File readfile=new File(readfilename);
					ResponseDto<List<Employee>> response=controller.readEmployeeDetailsFromTextFile(readfile);
					if (response.getStatus().equals("OK")){
					
						List<Employee> emplist=response.getData();
						for (Employee empdetails:emplist) {
							
							System.out.println("ID:"+empdetails.getID());
							System.out.println("Fname:"+empdetails.getFname());
							System.out.println("Lname:"+empdetails.getLname());
							System.out.println("Phn:"+empdetails.getphn());
							System.out.println("Email:"+empdetails.getemail());
							System.out.println("JoinDate:"+empdetails.getJoindate());
							System.out.println("Status:"+empdetails.getstatus());
						}
					}
					else
						System.out.println(response.getMessage());
				
				}
				else
					System.out.println("Given textfile is invalid");
				
			}
			//phase 2
			else if (employeeDetailsOption==2) {
				
				System.out.println("Enter the textfile to read:");
				readfilename=scan.nextLine();
				
				System.out.println("Enter the CSVfile to write:");
				writefilename=scan.nextLine();
				
				if (readfilename!=null && !readfilename.isBlank() && writefilename!=null && !writefilename.isBlank()) {
					
					File readfile=new File(readfilename);
					File writefile=new File(writefilename);
					ResponseDto<String> response=controller.writeEmployeeDetailstoCsvFromTextFile(readfile,writefile);
					if (response.getStatus().equals("OK")){
						
						System.out.println(response.getMessage());
					}
					else
						System.out.println(response.getMessage());
				}
				else
					System.out.println("Given textfile is invalid");
			}
			//phase 3
			else if (employeeDetailsOption==3){
				
				System.out.println("Enter the CSVfile to write:");
				writefilename=scan.nextLine();
				
				if (writefilename!=null && !writefilename.isBlank()) {
					File writefile=new File(writefilename);
				
					ResponseDto<String> response=controller.writeEmpolyeeDetailstoCsvFromConsole(writefile);
					System.out.println(response.getMessage());
				}
			}
			else
				System.out.println("Invalid Input");
			
			scan.close();
	
		}
		catch (IOException e) {
			
			System.out.println(e.getMessage());
		
		}
		
	}
}
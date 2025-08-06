package employeemanager.app;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import employeemanager.controller.EmployeeController;
import employeemanager.dto.Employee;
import employeemanager.dto.ResponseDto;

public class EmployeeManagerApp{
	
	public static void main(String args[]) throws SQLException {
		
		
		String readfilename;
		String writefilename;
		EmployeeController controller=new EmployeeController();

        //Create new employee
        ResponseDto<String> insertResponse=controller.insertEmployeeDetailsToDB();
        System.out.println(insertResponse.getMessage());
        
        //Get all employees
        ResponseDto<List<Employee>> employeesResponse=controller.getAllEmployeeDetailsFromDB();
        List<Employee> employees=employeesResponse.getData();
        for (Employee empdetails:employees) {
        	System.out.println(empdetails.getID()+" "+empdetails.getFname()+" "+empdetails.getLname());
        }
        
        //Get employee by id
        ResponseDto<Employee> employeeResponse=controller.getEmployeeDetailByIdFromDB();
        Employee employee=employeeResponse.getData();
        System.out.println(employee.getID()+" "+employee.getFname()+" "+employee.getLname());
        
        //Delete Employee By ID
        ResponseDto<String> deletedEmployeeResponse=controller.deleteEmployeeByIDFromDB();
        System.out.println(deletedEmployeeResponse.getMessage());
        
        //Update employee by ID
        ResponseDto<String> updateResponse=controller.updateEmployeeByIDInDB();
        System.out.println(updateResponse.getMessage());
        
        try {
			
			Scanner scan=new Scanner(System.in);
		
		
		
		System.out.println("The List of Actions on Employee Details Are:"
				+ "\n1.Read Employee details from text file"
				+ "\n2.Write Employee details to CSV from a text file"
				+ "\n3.Enter Employee datails and add to CSV file"
				+ "\n\nEnter the Option(1,2,3):");
		
		
		int option=scan.nextInt();
		scan.nextLine();

		//phase 1
		if (option==1){
			
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
		else if (option==2) {
			
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
		else if (option==3){
			
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
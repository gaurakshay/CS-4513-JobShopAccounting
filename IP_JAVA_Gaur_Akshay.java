/*
CS 4513
Database Management System
Sec 001
Fall 2015
Course Instructor: Dr. Le Gruenwald
Submitted by:
		Akshay Gaur
		113294004
		akshaygaur@ou.edu


The following is java source code for Task 5 of Individual Project 1.

Code presents the user with 4 choices and takes action as per choice selected.
Choice 1:	Enter a new customer.
Choice 2:	Enter a new department.
Choice 3:	Enter a new assembly with its customer-name, assembly-details, assembly-id, and date-ordered.
Choice 4:	Enter a new process-id and its department together with its type and information relevant to the type.
Choice 5:	Create a new account and associate it with the process, assembly, or department to which it is applicable.
Choice 6:	Enter a new job, given its job-no, assembly-id, process-id, and date the job commenced.
Choice 7:	At the completion of a job, enter the date it completed and the information relevant to the type of job.
Choice 8:	Enter a transaction-no, and its sup-cost and update all the costs (details) of the affected accounts by adding sup-cost to their current values of details.
Choice 9:	Retrieve the cost incurred on an assembly-id.
Choice 10:	Retrieve the labor time recorded on an assembly-id.
Choice 11:	Retrieve the total labor time within a department for jobs completed in the department during a given date.
Choice 12:	Retrieve the processes through which a given assembly-id has passed so far (in date-commenced order) and the department responsible for each process.
Choice 13:	Retrieve the jobs (together with their type information and assembly-id) completed during a given date in a given department.
Choice 14:	Retrieve the customers (in name order) whose assemblies are painted RED using a given painting method.
Choice 15:	Delete all cut-jobs whose job-no is in some range.
Choice 16:	Change the color of a given paint job.
Choice 17:	Retrieve the average cost of all accounts.
Choice 18:	Import: enter new customers from a data file.
Choice 19:	Export: Retrieve the customers (in name order) whose assemblies are painted RED using a given painting method and output them to a data file instead of screen.
Choice 20:	Ends the program.
The program will terminate only when user selects option 20.
*/

import java.sql.*;
import java.util.*;
import java.io.Console;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


public class IP_JAVA_Gaur_Akshay {
    public static void main(String[] args) throws FileNotFoundException {
		// Declare variables.
		int loop=0;
		int choice=0;
		
		// Console reader.
		Console cnsl = null;
		
		// Declare connection set to null. We will create an object that will
		// exist until the program terminates so that oonnectionsto the DB
		// don't need to be made again and again.
		Connection conn = null;
		try {
			// Load and register Oracle driver
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        // Establish connection that will persist for the duration of the program until user exits.
			// This way, the connection will not have to be made and closed again and again.
	        // connection string: "jdbc:oracle:thin@hostname:port/servername","username","password"
	        conn = DriverManager.getConnection(
	        "jdbc:oracle:thin:@oracle.cs.ou.edu:1521/pdborcl.cs.ou.edu",
	                "gaur4004","IVgm3Um6");
			
			//Statement stmt = conn.createStatement();
			
			cnsl = System.console();
					
			// Start while loop to present the user with options, user will stay in while loop
			// unless user chooses option 20.
			while (loop==0) {
				System.out.println();
				System.out.println("                     WELCOME TO THE JOB-SHOP ACCOUNTING DATABASE SYSTEM");
				System.out.println();
				System.out.println("    1.   Enter a new customer.");
				System.out.println("    2.   Enter a new department.");
				System.out.println("    3.   Enter a new assembly with its customer-name, assembly-details, assembly-id, and date-ordered.");
				System.out.println("    4.   Enter a new process-id and its department together with its type and information relevant to the type.");
				System.out.println("    5.   Create a new account and associate it with the process, assembly, or department to which it is applicable.");
				System.out.println("    6.   Enter a new job, given its job-no, assembly-id, process-id, and date the job commenced.");
				System.out.println("    7.   At the completion of a job, enter the date it completed and the information relevant to the type of job.");
				System.out.println("    8.   Enter a transaction-no, and its sup-cost and update all the costs (details) of the affected accounts.");
				System.out.println("    9.   Retrieve the cost incurred on an assembly-id.");
				System.out.println("    10.  Retrieve the labor time recorded on an assembly-id.");
				System.out.println("    11.  Retrieve the total labor time within a department for jobs completed in the department during a given date.");
				System.out.println("    12.  Retrieve the processes through which a given assembly-id has passed so far (in date-commenced order) and");
				System.out.println("         the department responsible for each process.");
				System.out.println("    13.  Retrieve the jobs (together with their type information and assembly-id) completed during a given date in a given department.");
				System.out.println("    14.  Retrieve the customers (in name order) whose assemblies are painted RED using a given painting method.");
				System.out.println("    15.  Delete all cut-jobs whose job-no is in some range.");
				System.out.println("    16.  Change the color of a given paint job.");
				System.out.println("    17.  Retrieve the average cost of all accounts.");
				System.out.println("    18.  Import: enter new customers from a data file.");
				System.out.println("    19.  Export: Retrieve the customers (in name order) whose assemblies are painted RED using a given painting method.");
				System.out.println("    20.  Quit.");
				// Read user input.
				choice = Integer.parseInt(cnsl.readLine());
				// Select action based on user input.
				switch(choice) {
					case 1:
						// Declare variables.
						String custName1 = null;
						String custAdd1 = null;
						String custInsertQuery1 = null;
						
						// Present user with prompt for the data to be entered
						// accept the same using Console Reader.
						custName1 = cnsl.readLine("Please enter the customer name: ");
						custAdd1 = cnsl.readLine("Please enter the customer address: ");
						
						// Create the query to be sent to Oracle.
						custInsertQuery1 = "INSERT INTO cust VALUES ('" + custName1
							+ "', '" + custAdd1 +"')";
						
						// Execute the query.
						conn.createStatement().executeQuery(custInsertQuery1);
						
						// Print Success message.
						System.out.println();
						System.out.println("Customer Successfully Entered!!");
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 2:
						// Declare variables.
						int deptNo2 = 0;
						String deptData2 = null;
						String deptInsertQuery2 = null;
						
						// Present user prompt for the data to be entered
						// accept the same using Console Reader.
						deptNo2 = Integer.parseInt(cnsl.readLine("Please enter the department number: "));
						deptData2 = cnsl.readLine("Please enter the department data: ");
						
						// Create the query to be sent to Oracle.
						deptInsertQuery2 = "INSERT INTO dept VALUES (" + deptNo2 + ", "
							+ "'" + deptData2 + "')";
						
						// Execute the query.
						conn.createStatement().executeQuery(deptInsertQuery2);
						
						// Print the success message.
						System.out.println();
						System.out.println("Department Successfully Entered!!");
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 3:
						// Declare variables.
						int asmblyID3 = 0;
						String ordrDt3 = null;
						String asmblyDet3 = null;
						String custName3 = null;
						String asmblyInsertQuery3 = null;
						
						// Present user prompt for the data to be entered
						// accept the same using Console Reader.
						asmblyID3 = Integer.parseInt(cnsl.readLine("Please enter the assembly number: "));
						ordrDt3 = cnsl.readLine("Please enter the order date in MM/DD/YYYY format: ");
						asmblyDet3 = cnsl.readLine("Please enter the assembly details: ");
						custName3 = cnsl.readLine("Please enter the customer who entered this assembly: ");
						
						// Create the query to be sent to Oracle.
						asmblyInsertQuery3 = "INSERT INTO asmbly VALUES (" + asmblyID3 + ", "
							+ "TO_DATE('" + ordrDt3 + "', 'MM/DD/YYYY'), "
							+ "'" + asmblyDet3 + "', "
							+ "'" + custName3 + "')";
						
						// Execute the statement.
						conn.createStatement().executeQuery(asmblyInsertQuery3);
						
						// Print the success message.
						System.out.println();
						System.out.println("Assembly Successfully Entered!!");
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 4:
						// Declare variables.
						int procID4 = 0;
						String procData4 = null;
						String deptNo4 = null;
						String procType4 = null;
						String fitType4 = null;
						String paintType4 = null;
						String paintMthd4 = null;
						String cutType4 = null;
						String mchnType4 = null;						
						String procInsertQuery4 = null;
						String procTypeInsertQuery4 = null;
						
						// Present user prompt for the data to be entered
						// accept the same using Console Reader.
						procID4 = Integer.parseInt(cnsl.readLine("Please enter the process number: "));
						procData4 = cnsl.readLine("Please enter the process data: ");
						deptNo4 = cnsl.readLine("Please enter the supervising department: ");
												
						// Create the query to be sent to Oracle.
						procInsertQuery4 = "INSERT INTO proc VALUES (" + procID4 + ", "
							+ "'" + procData4 + "', "
							+ deptNo4 + ")";
						
						// Execute the statement.
						conn.createStatement().executeQuery(procInsertQuery4);
						
						procType4 = cnsl.readLine("Please enter f/c/p if the process is fit proc/cut proc/paint proc respectively: ");
						
						if (procType4.equals("f")) {
							fitType4 = cnsl.readLine("Please enter the fit type for the fit process: ");
							procTypeInsertQuery4 = "INSERT INTO fit_proc VALUES (" + procID4 +", '" + fitType4 + "')";
							conn.createStatement().executeQuery(procTypeInsertQuery4);
						} else if (procType4.equals("c")) {
							cutType4 = cnsl.readLine("Please enter the cut type for the cut process: ");
							mchnType4 = cnsl.readLine("Please enter the machine type for the cut process: ");
							procTypeInsertQuery4 = "INSERT INTO cut_proc VALUES (" + procID4 +", '" + cutType4 + "', '" + mchnType4 + "')";
							conn.createStatement().executeQuery(procTypeInsertQuery4);
						} else if (procType4.equals("p")) {
							paintType4 = cnsl.readLine("Please enter the paint type for the paint process: ");
							paintMthd4 = cnsl.readLine("Please enter the paint method for the paint process: ");
							procTypeInsertQuery4 = "INSERT INTO pnt_proc VALUES (" + procID4 +", '" + paintType4 + "', '" + paintMthd4 + "')";
							conn.createStatement().executeQuery(procTypeInsertQuery4);
						}
						
						// Print the success message.
						System.out.println();
						System.out.println("Assembly Successfully Entered!!");
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 5:
						// Declare variables.
						int accntNo5 = 0;
						String estdDt5 = null;
						String accntType5 = null;
						int details5 = 0;
						int id5 = 0;
						String accntInsertQuery5 = null;
						String accntTypeInsertQuery5 = null;
						
						// Present user prompt for the data to be entered
						// accept the same using Console Reader.
						accntNo5 = Integer.parseInt(cnsl.readLine("Please enter the accnt number: "));
						estdDt5 = cnsl.readLine("Please enter the account established date in MM/DD/YYYY format: ");
												
						// Create the query to be sent to Oracle.
						accntInsertQuery5 = "INSERT INTO accnt VALUES (" + accntNo5 + ", "
							+ "TO_DATE('" + estdDt5 + "', 'MM/DD/YYYY'))";
						
						// Execute the statement.
						conn.createStatement().executeQuery(accntInsertQuery5);
						
						accntType5 = cnsl.readLine("Please enter a/p/d if the account is assembly/process/department account respectively: ");
						
						if (accntType5.equals("a")) {
							id5 = Integer.parseInt(cnsl.readLine("Please enter the assembly id for the assembly account: "));
							accntTypeInsertQuery5 = "INSERT INTO asmbly_accnt VALUES (" + accntNo5 +", " + details5 + ", " + id5 + ")";
							conn.createStatement().executeQuery(accntTypeInsertQuery5);
						} else if (accntType5.equals("p")) {
							id5 = Integer.parseInt(cnsl.readLine("Please enter the process id for the process account: "));
							accntTypeInsertQuery5 = "INSERT INTO proc_accnt VALUES (" + accntNo5 +", " + details5 + ", " + id5 + ")";
							conn.createStatement().executeQuery(accntTypeInsertQuery5);
						} else if (accntType5.equals("d")) {
							id5 = Integer.parseInt(cnsl.readLine("Please enter the department number for the department account: "));
							accntTypeInsertQuery5 = "INSERT INTO dept_accnt VALUES (" + accntNo5 +", " + details5 + ", " + id5 + ")";
							conn.createStatement().executeQuery(accntTypeInsertQuery5);
						}
						
						// Print the success message.
						System.out.println();
						System.out.println("Account Successfully Entered!!");
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 6:
						// Declare variables.
						int jobNo6 = 0;
						String strtDt6 = null;
						int asmblyId6 = 0;
						int procId6 = 0;
						String jobType6 = null;
						String jobsInsertQuery6 = null;
						String jobassgnInsertQuery6 = null;
						String jobTypeInserQuery6 = null;
						
						// Present user prompt for the data to be entered
						// accept the same using Console Reader.
						jobNo6 = Integer.parseInt(cnsl.readLine("Please enter the job number: "));
						strtDt6 = cnsl.readLine("Please enter the job start date in MM/DD/YYYY format: ");
												
						// Create the query to be sent to Oracle.
						jobsInsertQuery6 = "INSERT INTO jobs (job_no, strt_dt) VALUES (" + jobNo6 + ", "
							+ "TO_DATE('" + strtDt6 + "', 'MM/DD/YYYY'))";
						
						// Execute the statement.
						conn.createStatement().executeQuery(jobsInsertQuery6);
						
						jobType6 = cnsl.readLine("Please enter f/c/p if the job is fit/cut/paint job respectively: ");
						
						if (jobType6.equals("f")) {
							jobTypeInserQuery6 = "INSERT INTO fit_job VALUES (" + jobNo6 + ")";
							conn.createStatement().executeQuery(jobTypeInserQuery6);
						} else if (jobType6.equals("c")) {
							jobTypeInserQuery6 = "INSERT INTO cut_job (job_no) VALUES (" + jobNo6 + ")";
							conn.createStatement().executeQuery(jobTypeInserQuery6);
						} else if (jobType6.equals("p")) {
							jobTypeInserQuery6 = "INSERT INTO pnt_job (job_no) VALUES (" + jobNo6 + ")";
							conn.createStatement().executeQuery(jobTypeInserQuery6);
						}
						
						asmblyId6 = Integer.parseInt(cnsl.readLine("Please enter the assembly id for the job: "));
						procId6 = Integer.parseInt(cnsl.readLine("Please enter the proess id for the job: "));
						
						jobassgnInsertQuery6 = "INSERT INTO job_assgn VALUES (" + jobNo6 + "," + asmblyId6 + "," + procId6 + ")";
						
						conn.createStatement().executeQuery(jobassgnInsertQuery6);
						
						// Print the success message.
						System.out.println();
						System.out.println("Job Successfully Entered!!");
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 7:
						// Declare variables.
						int jobNo7 = 0;
						String endDt6 = null;
						int hours7 = 0;
						String mchn7 = null;
						int usd7 = 0;
						String mtrl7 = null;
						String color7 = null;
						int vol7 = 0;
						String jobsUpdateQuery7 = null;
						String jobTypeUpdQuery7 = null;
						
						// Present user prompt for the data to be entered
						// accept the same using Console Reader.
						jobNo7 = Integer.parseInt(cnsl.readLine("Please enter the job number to update: "));
						endDt6 = cnsl.readLine("Please enter the job end date in MM/DD/YYYY format: ");
						hours7 = Integer.parseInt(cnsl.readLine("Please enter the hours taken by the job: "));
												
						// Create the query to be sent to Oracle.
						jobsUpdateQuery7 = "UPDATE jobs SET end_dt=TO_DATE('" + endDt6 + "', 'MM/DD/YYYY'), lbr_tim=NUMTODSINTErVAL(" + hours7 + ", 'HOUR') WHERE job_no = +" + jobNo7;
						
						// Execute the statement.
						conn.createStatement().executeQuery(jobsUpdateQuery7);
						
						//System.out.println("SELECT * FROM fit_job WHERE job_no = " + jobNo7);
						//System.out.println("SELECT * FROM cut_job WHERE job_no = " + jobNo7);
						
						ResultSet rs1 = conn.createStatement().executeQuery("SELECT * FROM fit_job WHERE job_no = " + jobNo7);
						ResultSet rs2 = conn.createStatement().executeQuery("SELECT * FROM cut_job WHERE job_no = " + jobNo7);
						
						if(rs1.next()){
							System.out.println();
							System.out.println("Job Successfully Updated!!");
							
							cnsl.readLine("Hit enter to continue......");
							break;
						} else if (rs2.next()){
							mchn7 = cnsl.readLine("Please enter the machine type used for the cut job: ");
							usd7 = Integer.parseInt(cnsl.readLine("Please enter the amount of time machine was used (in hrs): "));
							mtrl7 = cnsl.readLine("Please enter the material used for the cut job: ");
							jobTypeUpdQuery7 = "UPDATE cut_job SET mchn_typ='" + mchn7 + "', tim_usd=NUMTODSINTERVAL("
								+ usd7 + ", 'HOUR'), mtrl_usd='" + mtrl7 + "' WHERE job_no = " + jobNo7;
							conn.createStatement().executeQuery(jobTypeUpdQuery7);
						} else {
							color7 = cnsl.readLine("Please enter the colorfor the paint job: ");
							vol7 = Integer.parseInt(cnsl.readLine("Please enter the volume of paint: "));
							jobTypeUpdQuery7 = "UPDATE pnt_job SET color='"+color7+"', vol = "+vol7+" WHERE job_no = "+jobNo7;
							conn.createStatement().executeQuery(jobTypeUpdQuery7);
						};
												
						// Print the success message.
						System.out.println();
						System.out.println("Job Successfully Updated!!");
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 8:
						// Declare variables.
						int trans8 = 0;
						double sup8 = 0.0;
						int job8 = 0;
						String transInsert8 = null;
						String aAct8 = null;
						String pAct8 = null;
						String dAct8 = null;
						String aAcc8 = null;
						String pAcc8 = null;
						String dAcc8 = null;
						
						// Present user prompt for the data to be entered
						// accept the same using Console Reader.
						trans8 = Integer.parseInt(cnsl.readLine("Please enter the transaction number: "));
						sup8 = Double.parseDouble(cnsl.readLine("Please enter the transaction cost: "));
						job8 = Integer.parseInt(cnsl.readLine("Please enter the job no for which transaction is being recorded: "));
						
						// Create the query to be sent to Oracle.
						transInsert8 = "INSERT INTO trans VALUES (" + trans8 + ", " + sup8 + ", " + job8 + ")";
						aAct8 = "INSERT INTO a_accnt_trans" 
							+ " SELECT " + trans8 + ", accnt_no FROM (SELECT accnt_no FROM asmbly_accnt" 
							+ " WHERE asmbly_id=(SELECT asmbly_id FROM job_assgn WHERE job_no="
							+ "(SELECT job_no FROM trans WHERE trans_id=" + trans8 + ")))";
						pAct8 = "INSERT INTO p_accnt_trans" 
							+ " SELECT " + trans8 + ", accnt_no FROM (SELECT accnt_no FROM proc_accnt" 
							+ " WHERE proc_id=(SELECT proc_id FROM job_assgn WHERE job_no="
							+ "(SELECT job_no FROM trans WHERE trans_id=" + trans8 + ")))";
						dAct8 = "INSERT INTO d_accnt_trans"
							+ " SELECT " + trans8 + ", accnt_no FROM (SELECT accnt_no FROM dept_accnt "
							+ "WHERE dept_no=(SELECT dept_no FROM proc WHERE proc_id = (SELECT "
							+ "proc_id FROM job_assgn WHERE job_no=(SELECT job_no FROM trans WHERE trans_id=" + trans8 + "))))";
						aAcc8 = "UPDATE asmbly_accnt SET details1=details1+(SELECT sup_cost FROM trans WHERE trans_id = "+trans8+")"
							+ " WHERE asmbly_id=(SELECT asmbly_id FROM job_assgn WHERE job_no=(SELECT job_no FROM trans WHERE trans_id="+trans8+"))";
						pAcc8 = "UPDATE proc_accnt SET details3=details3+(SELECT sup_cost FROM trans WHERE trans_id = "+trans8+")"
							+ " WHERE proc_id=(SELECT proc_id FROM job_assgn WHERE job_no=(SELECT job_no FROM trans WHERE trans_id="+trans8+"))";
						dAcc8 = "UPDATE dept_accnt SET details2=details2+(SELECT sup_cost FROM trans WHERE trans_id = "+trans8+")"
							+ " WHERE dept_no=(SELECT dept_no FROM proc WHERE proc_id=(SELECT proc_id FROM job_assgn WHERE job_no=(SELECT job_no FROM trans WHERE trans_id="+trans8+")))";	
						
						// Execute the statement.
						conn.createStatement().executeQuery(transInsert8);
						conn.createStatement().executeQuery(aAct8);
						conn.createStatement().executeQuery(pAct8);
						conn.createStatement().executeQuery(dAct8);
						conn.createStatement().executeQuery(aAcc8);
						conn.createStatement().executeQuery(pAcc8);
						conn.createStatement().executeQuery(dAcc8);
						
						// Print the success message.
						System.out.println();
						System.out.println("Trans Successfully Entered!!");
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 9:
						// Declare variables.
						int asmbly9 = 0;
						String asmCost9 = null;
						
						// Present user with prompt for the data to be entered
						// accept the same using Console Reader.
						asmbly9 = Integer.parseInt(cnsl.readLine("Please enter the assembly id to retreive cost incurred: "));
						
						// Create the query to be sent to Oracle.
						asmCost9 = "SELECT details1 FROM asmbly_accnt WHERE asmbly_id = " + asmbly9;
						
						// Fetch the results.
						ResultSet rs3 = conn.createStatement().executeQuery(asmCost9);
						
						// Print output.
						System.out.println();
						while(rs3.next()){
							System.out.println("Cost incurred on the entered assembly id is : " + rs3.getDouble(1));
						}
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 10:
						// Declare variables.
						int asmbly10 = 0;
						String asmLbr10 = null;
						
						// Present user with prompt for the data to be entered
						// accept the same using Console Reader.
						asmbly10 = Integer.parseInt(cnsl.readLine("Please enter the assembly id to retreive labour time recorded: "));
						
						// Create the query to be sent to Oracle.
						asmLbr10 = "SELECT SUM(EXTRACT(HOUR FROM lbr_tim)) FROM jobs WHERE job_no IN ( SELECT job_no FROM job_assgn WHERE asmbly_id = "+asmbly10+")";
						
						// Fetch the results.
						ResultSet rs4 = conn.createStatement().executeQuery(asmLbr10);
						
						// Print output.
						System.out.println();
						while(rs4.next()){
							System.out.println("Labor time recorded on the entered assembly id is : " + rs4.getInt(1) + " Hrs");
						}
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 11:
						// Declare variables.
						int dept11 = 0;
						String dt11 = null;
						String depLbr11 = null;
						
						// Present user with prompt for the data to be entered
						// accept the same using Console Reader.
						dept11 = Integer.parseInt(cnsl.readLine("Please enter the dept number to retreive labour time recorded: "));
						dt11 = cnsl.readLine("Please enter the date to calculate the labor time for in MM/DD/YYYY: ");
						
						// Create the query to be sent to Oracle.
						depLbr11 = "SELECT SUM(EXTRACT(HOUR FROM lbr_tim)) FROM jobs WHERE job_no IN "
						+ "(SELECT job_no FROM job_assgn WHERE proc_id IN (SELECT proc_id FROM proc WHERE dept_no = "+dept11+"))"
						+ " AND strt_dt >= TO_DATE('"+dt11+"','MM/DD/YYYY') AND end_dt <= TO_DATE('"+dt11+"','MM/DD/YYYY')";
						
						// Fetch the results.
						ResultSet rs5 = conn.createStatement().executeQuery(depLbr11);
						
						// Print output.
						System.out.println();
						while(rs5.next()){
							System.out.println("Labor time recorded on the entered department id for the given date is : " + rs5.getInt(1) + " Hrs");
						}
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 12:
						// Declare variables.
						int asmbly12 = 0;
						String asmProc12 = null;
						
						// Present user with prompt for the data to be entered
						// accept the same using Console Reader.
						asmbly12 = Integer.parseInt(cnsl.readLine("Please enter the assembly id to retreive the processes passed: "));
						
						// Create the query to be sent to Oracle.
						asmProc12 = "SELECT p.proc_id, p.dept_no FROM (SELECT job_no, asmbly_id, proc_id FROM job_assgn "
							+ "WHERE asmbly_id = "+asmbly12+") ja INNER JOIN jobs jo ON ja.job_no = jo.job_no INNER JOIN proc p " 
							+ "ON ja.proc_id = p.proc_id ORDER BY jo.strt_dt, p.proc_id";
						
						// Fetch the results.
						ResultSet rs6 = conn.createStatement().executeQuery(asmProc12);
						
						// Print output.
						System.out.println();
						System.out.println("Processes and the Supervising departments that the provided assembly has passed through are:");
						System.out.println("proc_id\tdept_no");
						while(rs6.next()){
							System.out.println(rs6.getInt(1) + "\t" + rs6.getInt(2));
						}
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 13:
						// Declare variables.
						int dept13 = 0;
						String dt13 = null;
						String depJob13 = null;
						
						// Present user with prompt for the data to be entered
						// accept the same using Console Reader.
						dept13 = Integer.parseInt(cnsl.readLine("Please enter the department number to retreive the jobs: "));
						dt13 = cnsl.readLine("Please enter the date to retreive the jobs: ");
						
						// Create the query to be sent to Oracle.
						depJob13 = "SELECT j.job_no AS \"JOB NUMBER\", 'FIT' AS \"JOB TYPE\", ja.asmbly_id AS \"ASSEMBLY ID\" FROM jobs j "
							+ "INNER JOIN job_assgn ja ON j.job_no = ja.job_no INNER JOIN fit_job fj ON j.job_no = fj.job_no "
							+ "WHERE strt_dt >= TO_DATE('"+dt13+"', 'MM/DD/YYYY') AND end_dt <= TO_DATE('"+dt13+"', 'MM/DD/YYYY') "
							+ " AND ja.proc_id IN  ( SELECT proc_id FROM proc WHERE dept_no="+dept13+" ) "
							+ "UNION "
							+ " SELECT j.job_no AS \"JOB NUMBER\", 'PAINT' AS \"JOB TYPE\", ja.asmbly_id AS \"ASSEMBLY ID\" FROM jobs j "
							+ " INNER JOIN job_assgn ja ON j.job_no = ja.job_no INNER JOIN pnt_job pj ON j.job_no = pj.job_no"
							+ " WHERE strt_dt >= TO_DATE('"+dt13+"', 'MM/DD/YYYY') AND end_dt <= TO_DATE('"+dt13+"', 'MM/DD/YYYY')"
							+ " AND ja.proc_id IN ( SELECT proc_id FROM proc WHERE dept_no="+dept13+" )"
							+ " UNION"
							+ " SELECT j.job_no AS \"JOB NUMBER\", 'CUT' AS \"JOB TYPE\", ja.asmbly_id AS \"ASSEMBLY ID\" FROM jobs j "
							+ " INNER JOIN job_assgn ja ON j.job_no = ja.job_no INNER JOIN cut_job cj ON j.job_no = cj.job_no "
							+ " WHERE strt_dt >= TO_DATE('"+dt13+"', 'MM/DD/YYYY') AND end_dt <= TO_DATE('"+dt13+"', 'MM/DD/YYYY') "
							+ " AND ja.proc_id IN ( SELECT proc_id FROM proc WHERE dept_no="+dept13+" )";
						
						// Fetch the results.
						ResultSet rs7 = conn.createStatement().executeQuery(depJob13);
						
						// Print output.
						System.out.println();
						System.out.println("Jobs completed in the department for the provided date are:");
						System.out.println("job_no\tjob_type\tassembly_id");
						while(rs7.next()){
							System.out.println(rs7.getInt(1) + "\t" + rs7.getString(2) + "\t\t" + rs7.getInt(3));
						}
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 14:
						// Declare variables.
						String pmthd14 = null;
						String redAss = null;
						
						// Present user with prompt for the data to be entered
						// accept the same using Console Reader.
						pmthd14 = cnsl.readLine("Please enter the paint method to retreive the customers with Red assemblies: ");
						
						// Create the query to be sent to Oracle.
						redAss = "SELECT cust_nm FROM asmbly WHERE asmbly_id IN ( SELECT asmbly_id"
							+ " FROM job_assgn WHERE job_no IN ( SELECT job_no FROM pnt_job WHERE color='RED' )"
							+ " AND proc_id IN ( SELECT proc_id FROM pnt_proc WHERE pnt_mthd='"+pmthd14+"' ) ) ORDER BY cust_nm";
						
						// Fetch the results.
						ResultSet rs8 = conn.createStatement().executeQuery(redAss);
						
						// Print output.
						System.out.println();
						System.out.println("Customers that fulfill the criteria are:");
						while(rs8.next()){
							System.out.println(rs8.getString(1));
						}
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 15:
						// Declare variables.
						int jobLow15 = 0;
						int jobHigh15 = 0;
						String del15 = null;
						
						// Present user with prompt for the data to be entered
						// accept the same using Console Reader.
						jobLow15 = Integer.parseInt(cnsl.readLine("Please enter the lower range of the job: "));
						jobHigh15 = Integer.parseInt(cnsl.readLine("Please enter the upper range of the job: "));
						
						// Create the query to be sent to Oracle.
						del15 = "DELETE FROM cut_job WHERE job_no BETWEEN "+jobLow15+" AND "+jobHigh15;
						
						// Fetch the results.
						conn.createStatement().executeQuery(del15);
						
						// Print success message.
						System.out.println();
						System.out.println("Jobs successfully deleted!");
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 16:
						// Declare variables.
						int job16 = 0;
						String setRed15 = null;
						
						// Present user with prompt for the data to be entered
						// accept the same using Console Reader.
						job16 = Integer.parseInt(cnsl.readLine("Please enter the job for which paint color should be changed to Red: "));
						
						// Create the query to be sent to Oracle.
						setRed15 = "UPDATE pnt_job SET color = 'RED' WHERE job_no = " +job16;
						
						// Fetch the results.
						conn.createStatement().executeQuery(setRed15);
						
						// Print success message.
						System.out.println();
						System.out.println("Color successfully updated!");
						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 17:
						// Create the query to be sent to Oracle.
						String acAvg17 = "SELECT TO_CHAR(AVG(expense),'999,999.99') FROM "
							+ " ( SELECT details1 AS expense FROM asmbly_accnt "
							+ "UNION ALL "
							+ " SELECT details3 AS expense FROM proc_accnt " 
							+ "UNION ALL "
							+ "SELECT details2 AS expense FROM dept_accnt )";
						
						// Fetch the results.
						ResultSet rs9 = conn.createStatement().executeQuery(acAvg17);
						
						// Print output.
						System.out.println();
						System.out.println("Average of the acounts are:");
						while(rs9.next()){
							System.out.println(rs9.getString(1));
						}
						
						cnsl.readLine("Hit enter to continue......");
						break;
					case 18:
						String filename18 = cnsl.readLine("Please enter the file name: ");
						
						String filepath18 = "C:\\Users\\gaura\\Documents\\"+filename18;
						
						String  thisLine = null;
					      try{
					         // open input stream test.txt for reading purpose.
					         BufferedReader br = new BufferedReader(new FileReader(filepath18));
					         while ((thisLine = br.readLine()) != null) {
								conn.createStatement().executeQuery("INSERT INTO cust VALUES ("+ thisLine +")");
					         }       
					      }catch(Exception e){
					         e.printStackTrace();
					      }
						//Print success message
						
						System.out.println("Customers entered successfully!!!");
						
						cnsl.readLine("Hit enter to continue......");
						break;
					case 19:
						// Declare variables.
						String pmthd19 = null;
						String file19 = null;
						String filepath = null;
						String redAss19 = null;
						
						// Present user with prompt for the data to be entered
						// accept the same using Console Reader.
						pmthd19 = cnsl.readLine("Please enter the paint method to retreive the customers with Red assemblies: ");
						file19 = cnsl.readLine("Please enter the file name (.csv): ");
						
						filepath = "C:\\Users\\gaura\\Documents\\" + file19;
						
						// Create the query to be sent to Oracle.
						redAss19 = "SELECT * FROM cust WHERE cust_nm IN (SELECT cust_nm FROM asmbly WHERE asmbly_id IN ( SELECT asmbly_id"
							+ " FROM job_assgn WHERE job_no IN ( SELECT job_no FROM pnt_job WHERE color='RED' )"
							+ " AND proc_id IN ( SELECT proc_id FROM pnt_proc WHERE pnt_mthd='"+pmthd19+"' ) ) ) ORDER BY cust_nm";
						
						// Fetch the results.
						ResultSet rs10 = conn.createStatement().executeQuery(redAss19);
						
						try{
							FileWriter writer = new FileWriter(filepath);
							writer.append("'");
							writer.append("Cust Name");
							writer.append("'");
							writer.append(",");
							writer.append("'");
							writer.append("Cust Add");
							writer.append("'");
							writer.append("\r\n");
							// Write the result set to file
							while(rs10.next()){
							writer.append("'");
							writer.append(rs10.getString(1));
							writer.append("'");
							writer.append(",");
							writer.append("'");
							writer.append(rs10.getString(2));
							writer.append("'");
							writer.append("\r\n");
							}
							writer.flush();
							writer.close();
						} catch(IOException e) {e.printStackTrace();}

						
						cnsl.readLine("Hit enter to continue......");
						
						break;
					case 20:
						System.out.println();
						System.out.println("Exiting the program!");
						loop=1;
						break;
					default :
						//Ask the user to correct the choice entered.
						System.out.println("Incorrect option chosen, please choose again!");
						break;
				}
			}
		// Catch any exception.
		} catch(Exception e) {
			System.out.println(e);
		// Close the connection before exiting the program.
		} finally {
			if(conn != null){
				try {
					conn.close();
				} catch (Exception e) {System.out.println(e);}
			}
		}
   }
}
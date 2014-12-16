package com.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.model.ClaimProcessing;
import com.model.Customer;
import com.model.HealthPolicy;
import com.util.Service;




public class ClaimController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ClaimController() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action1=request.getParameter("claimAction");
		System.out.println("ACTION    BY IMAGE "+action1);
		if("pieChart".equals(action1))
		{
			
			System.out.println("makePiechart");
			
			
			OutputStream out = response.getOutputStream(); /* Get the output stream from the response object */
	        response.setContentType("image/png"); /* Set the HTTP Response Type */
	        
	        
	        com.util.PieChart_AWT piechart=new com.util.PieChart_AWT("YoYo");
	        
	        
	        JFreeChart chart =piechart.getChart();
	       // Create chart
	         
	        ChartUtilities.writeChartAsPNG(out,chart , 500, 600);/* Write the data to the output stream */
			
		}
	
	
	
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		HttpSession session = request.getSession(true);
		String action = (String) session.getAttribute("claimAction");
		
		String action1=request.getParameter("claimAction");
		System.out.println("ACTION    BY IMAGE "+action1);
		
		if("claimRegistration".equals(action))
		{
			System.out.println(action);


			Service claimService = new Service();

			HttpSession psession = request.getSession(true);
			String policyRefNum=(String)psession.getAttribute("pSession");
            String errorMsg = null;
			
			
			if(request.getParameter("cAmount") == null || request.getParameter("cAmount").equals("")){
				errorMsg = "Claim Amount can't be null or empty.";
				}
				if(request.getParameter("cDate")== null | request.getParameter("cDate").equals("")){
				errorMsg = "Submission date can't be null or empty.";
				}
				if(request.getParameter("bName") == null | request.getParameter("bName").equals("")){
				errorMsg = "Beneficiary name can't be null or empty.";
				}
				if(request.getParameter("bAddr") == null | request.getParameter("bAddr").equals("")){
				errorMsg = "Beneficiary address can't be null or empty.";
				}
				if(request.getParameter("bEmail") == null | request.getParameter("bEmail").equals("")){
					errorMsg = "Beneficiary email can't be null or empty.";
					}
				if(request.getParameter("bNum") == null | request.getParameter("bNum").equals("")){
					errorMsg = "Beneficiary contact number can't be null or empty.";
					}
				if(request.getParameter("bAge") == null | request.getParameter("bAge").equals("")){
					errorMsg = "Beneficiary's age can't be null or empty.";
					}
				
				
				if(errorMsg != null)
				{
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Insurance Claims Processing/PolicyRefNumForRegistration.jsp");
				request.setAttribute("msg1","<h1><font color='red'>Fields cant be null</font></h1> ");
				rd.forward(request, response);
				}
				
				else
				{

			  ClaimProcessing claimProcessing = new ClaimProcessing();
			
			
			claimProcessing.setClaimAmount(Double.parseDouble(request.getParameter("cAmount")));
			claimProcessing.setClaimSubmissionDate(request.getParameter("cDate"));
			claimProcessing.setBenName(request.getParameter("bName"));
			claimProcessing.setBenAddress(request.getParameter("bAddr"));
			claimProcessing.setBenEmail(request.getParameter("bEmail"));
			claimProcessing.setBenContactNo(Long.parseLong(request.getParameter("bNum")));
			claimProcessing.setBenAge(Integer.parseInt(request.getParameter("bAge")));
			int custId=Integer.parseInt(request.getParameter("hiddenCustId"));
		
			
			String claimId=claimService.submitClaim(custId, policyRefNum, claimProcessing);

			request.setAttribute("claimId", claimId);
			RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/ClaimGeneration.jsp");
			rd.forward(request, response);
				
				
		     }
		}
		
		
		else if("claimModify".equals(action))
		{   String errorMsg = null;
		
		
		if(request.getParameter("cAmount") == null || request.getParameter("cAmount").equals("")){
			errorMsg = "Claim Amount can't be null or empty.";
			}
			
			if(request.getParameter("bName") == null | request.getParameter("bName").equals("")){
			errorMsg = "Beneficiary name can't be null or empty.";
			}
			if(request.getParameter("bAddr") == null | request.getParameter("bAddr").equals("")){
			errorMsg = "Beneficiary address can't be null or empty.";
			}
			if(request.getParameter("bEmail") == null | request.getParameter("bEmail").equals("")){
				errorMsg = "Beneficiary email can't be null or empty.";
				}
			if(request.getParameter("bNum") == null | request.getParameter("bNum").equals("")){
				errorMsg = "Beneficiary contact number can't be null or empty.";
				}
			if(request.getParameter("bAge") == null | request.getParameter("bAge").equals("")){
				errorMsg = "Beneficiary's age can't be null or empty.";
				}
			
			
			if(errorMsg != null)
			{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Insurance Claims Processing/SearchClaimForModification.jsp");
			request.setAttribute("msg","<h1><font color='red'>Fields cant be null</font></h1> ");
			rd.forward(request, response);
			}
			
			else
			{
			ClaimProcessing claimProcessing = new ClaimProcessing();
			claimProcessing.setClaimId(request.getParameter("ID"));
			claimProcessing.setClaimAmount(Double.parseDouble(request.getParameter("cAmount")));
			claimProcessing.setBenName(request.getParameter("bName"));
			claimProcessing.setBenAddress(request.getParameter("bAddr"));
			claimProcessing.setBenEmail(request.getParameter("bEmail"));
			claimProcessing.setBenContactNo(Long.parseLong(request.getParameter("bNum")));
			claimProcessing.setBenAge(Integer.parseInt(request.getParameter("bAge")));
			
			Service claimService = new Service();
			boolean checkModify=claimService.modifyClaim(claimProcessing);
			System.out.println(checkModify);
			if(checkModify)
			{
			RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/SearchClaimForModification.jsp");
			request.setAttribute("msg", "<center><h1><b><font color='red'>Modified successfully</font></b></h1></center>");
			rd.forward(request, response);
			}
			else
			{
				RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/SearchClaimForModification.jsp");
				request.setAttribute("msg", "<h1><b><font color='red'>Modification unsuccessful as status is not open</font></b></h1>");
				rd.forward(request, response);
			}
		 }
	}
		else if("getCustomerPolicy".equals(action))
		{
			String policyRefNum=request.getParameter("policyRefNum");
			HttpSession pSession=request.getSession(true);
			pSession.setAttribute("pSession", policyRefNum);
			HttpSession officerSession = request.getSession(true);
			
			String officer = (String) officerSession.getAttribute("userid");
			Service claimService = new Service();
			if(officer.substring(0,2).equals("IO") && policyRefNum.substring(0,1).equals("H"))
				{
		    
			      Customer customer=claimService.getCustomerPolicy(policyRefNum);
			      if(customer == null)
					{
					 System.out.println(customer);
					 RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/PolicyRefNumForRegistration.jsp");
					 request.setAttribute("msg1", "<center><h1><b><font color='red'>Customer does not exist for this policy </font></b></h1></center>");
					 rd.forward(request, response);
					}
					else
					{
						
						 
						 ArrayList<String> allPolicyRefNums=claimService.getAllPolicyRefNum();
						 for(String s:allPolicyRefNums)
						 {
							 if(s.equals(policyRefNum))
							 {
								 System.out.println("Already exists!!");
								 RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/PolicyRefNumForRegistration.jsp");
								 request.setAttribute("msg1", "<center><h1><b><font color='red'>Claim Id already generated for the policy</font></b></h1></center>");
								 rd.forward(request, response);
								 return;
							 }
						 }
						 
						 
						
						 RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/ClaimRegistration.jsp");
						 request.setAttribute("msg1",customer);
						 rd.forward(request, response);
					}
				}
			  else if(officer.substring(0,2).equals("FO") && policyRefNum.substring(0,1).equals("V"))
			  {
				  Customer customer=claimService.getCustomerPolicy(policyRefNum);
				  
				  
			      if(customer == null)
					{
					 System.out.println(customer);
					 RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/PolicyRefNumForRegistration.jsp");
					 request.setAttribute("msg1", "<center><h1><b><font color='red'>Customer does not exist for this policy</font></b></h1></center>");
					 rd.forward(request, response);
					}
					else
					{
						
						 ArrayList<String> allPolicyRefNums=claimService.getAllPolicyRefNum();
						 for(String s:allPolicyRefNums)
						 {
							 if(s.equals(policyRefNum))
							 {
								 System.out.println("Already exists!!");
								 RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/PolicyRefNumForRegistration.jsp");
								 request.setAttribute("msg1", "<center><h1><b><font color='red'>Claim Id already generated for the policy</font></b></h1></center>");
								 rd.forward(request, response);
								 return;
							 }
						 }
						 
						 
						
						 RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/ClaimRegistration.jsp");
						 request.setAttribute("msg1",customer);
						 rd.forward(request, response);
					}
			  }
			  else
				{
					RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/PolicyRefNumForRegistration.jsp");
					request.setAttribute("msg1", "<center><h1><b><font color='red'>No rights to register</font></b></h1></center>");
					rd.forward(request, response);
				}
			
			
		}
		else if("getClaimDetailsForModification".equals(action))
		{
			String claimId=request.getParameter("claimId");
			HttpSession officerSession = request.getSession(true);
			String officer = (String) officerSession.getAttribute("userid");
			//System.out.println("officer:" + officer);
			
			String officerType="";
			
			if(officer.substring(0,2).equals("IO"))
			{
				officerType="H%";
			}
			else
			{
				officerType="V%";
			}
			
			Service claimService = new Service();
            boolean checkClaimId=false;
			
			ArrayList<String> allClaimId = claimService.getAllClaimId();
			for(String str:allClaimId)
			{
				if(str.equals(claimId))
				{
					checkClaimId=true;
					break;
				}
				
				
			}
	    
			if(checkClaimId)
			{
			
			
			
				ClaimProcessing claimProcessing=claimService.getClaimDetails(claimId,officerType);
				
				if(claimProcessing == null)
				{
					response.setContentType("text/html");
					request.setAttribute("msg", "<center><h2><b><font color='red'>No rights to register</font></b></h2></center>");
					RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/SearchClaimForModification.jsp");
					rd.forward(request,response);
				}
				else if(!claimProcessing.getClaimStatus().equals("OPEN"))
				{
					response.setContentType("text/html");
					request.setAttribute("msg", "<center><h2><b><font color='red'>Claim Status is not open</font></b></h2></center>");
					RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/SearchClaimForModification.jsp");
					rd.forward(request,response);
				}
				else
				{
				request.setAttribute("claimProcessing", claimProcessing);
				request.setAttribute("claimId", claimId);
				RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/ClaimModification.jsp");
				rd.forward(request,response);
				}
			}
			else
			{
				response.setContentType("text/html");
				request.setAttribute("msg", "<center><h2><b><font color='red'>Claim Id does not exist</font></b></h2></center>");
				RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/SearchClaimForModification.jsp");
				rd.forward(request,response);
			}
			
		}
			
		
		else if("getClaimDetailsForDeletion".equals(action))
		{
			String claimId=request.getParameter("claimId");
			HttpSession officerSession = request.getSession(true);
			String officer = (String) officerSession.getAttribute("userid");
			//System.out.println("officer:" + officer);
			
			String officerType="";
			if(officer.substring(0,2).equals("IO"))
			{
				officerType="H%";
			}
			else
			{
				officerType="V%";
			}
			Service claimService = new Service();
			System.out.println(claimId);
			 boolean checkClaimId=false;
				
				ArrayList<String> allClaimId = claimService.getAllClaimId();
				for(String str:allClaimId)
				{
					if(str.equals(claimId))
					{
						checkClaimId=true;
						break;
					}
					
					
				}
		    
		if(checkClaimId)
		{
			
			
			ClaimProcessing claimProcessing=claimService.getClaimDetails(claimId,officerType);
			
			if(claimProcessing == null)
			{
				response.setContentType("text/html");
				request.setAttribute("msg", "<center><h1><b><font color='red'>No rights to register</font></b></h1></center>");
				RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/SearchClaimForDeletion.jsp");
				rd.forward(request,response);
			}
			else
			{
				
				boolean claimdelete=claimService.deleteClaim(claimId);
				
				if(claimdelete)
				{
				//System.out.println(claimdelete);
				 RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/SearchClaimForDeletion.jsp");
				 request.setAttribute("msg", "<center><h1><b><font color='red'>Deletion Successful</font></b></h1></center>");
				 rd.forward(request, response);
				}
				else
				{
					//System.out.println(claimdelete);
					 RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/SearchClaimForDeletion.jsp");
					 request.setAttribute("msg", "<center><h1><b><font color='red'>Claim status is not open</font></b></h1></center>");
					 rd.forward(request, response);
				}
			}
		}
		else
		{
			response.setContentType("text/html");
			request.setAttribute("msg", "<center><h2><b><font color='red'>Claim Id does not exist</font></b></h2></center>");
			RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/SearchClaimForDeletion.jsp");
			rd.forward(request,response);
		}
	}
			else if("ClaimDetails".equals(action))
			{
				String claimId=request.getParameter("claimId");
				Service claimService = new Service();
				ClaimProcessing claimProcessing=claimService.getClaimDetails(claimId,"%");
				if(claimProcessing!=null)
				{
					ClaimProcessing adminClaimProcessing=claimService.getClaimDetailsForAdmin(claimId);
					if(adminClaimProcessing!=null)
					{
						RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/ClaimApproval.jsp");
						request.setAttribute("claimId",claimId);
						request.setAttribute("claimProcessing", adminClaimProcessing);
						rd.forward(request,response);
					}
					else
					{
						
						response.setContentType("text/html");
						request.setAttribute("msg", "<center><h1><b><font color='red'>Claim status is not open</font></b></h1></center>");
						RequestDispatcher rd=request.getRequestDispatcher("/Admin_HomePage.jsp");
						rd.forward(request,response);
					}
					
				}
				else
				{
					response.setContentType("text/html");
					request.setAttribute("msg", "<center><h1><b><font color='red'>Claim Id does not exist</font></b></h1></center>");
					RequestDispatcher rd=request.getRequestDispatcher("/Admin_HomePage.jsp");
					rd.forward(request,response);
				}
				
			}
		
		
		
			else if("approveClaim".equals(action))
			{
				//System.out.println("heya approved!!");
				String status="";
				
				Service claimService = new Service();
				status="Approved";
				String claimId = (String) request.getParameter("hiddenClaimId");
				
				claimService.approve_Claim(claimId, status);
				RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/ClaimApproval.jsp");
				request.setAttribute("msg", status);
				rd.forward(request,response);
				
				
			}
			else if("rejectClaim".equals(action))
			{
				String status="";
				String[] checkBoxValues=request.getParameterValues("reason");
				Service claimService = new Service();
			
				System.out.println(request.getParameterValues("reason"));
				if(request.getParameterValues("reason")!=null)
				{ //System.out.println(request.getParameterValues("reason"));
					status="Rejected";
					String claimId = (String) request.getParameter("hiddenClaimId");
					String x="";
				    for(String s:checkBoxValues)
				    {
				    	x+=s+",";
				    }
				    claimService.reject_Claim(claimId, status,x);
					RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/ClaimRejection.jsp");
					request.setAttribute("msgrej", "<center><h1><b><font color='red'>Claim Rejected</font></b></h1></center>");
					rd.forward(request,response);
					
				}
				else
				{   System.out.println("inside else");
				System.out.println(request.getParameterValues("reason"));
					RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/ClaimRejection.jsp");
					request.setAttribute("msg", "<center><h1><b><font color='red'>Please provide atleast one reason for rejection of claim</font></b></h1></center>");
					rd.forward(request,response);
				}
			
			}
			else if("claimStatus".equals(action))
			{
				
				System.out.println("claim stat");
				String claimId=request.getParameter("claimId");
				System.out.println(claimId);
				HttpSession officerSession = request.getSession(true);
				String officer = (String) officerSession.getAttribute("userid");
				
				String officerType="";
				if(officer.substring(0,2).equals("IO"))
				{
					officerType="H%";
				}
				else
				{
					officerType="V%";
				}
				
				Service claimService = new Service();
				 boolean checkClaimId=false;
				ArrayList<String> allClaimId = claimService.getAllClaimId();
				for(String str:allClaimId)
				{
					if(str.equals(claimId))
					{
						checkClaimId=true;
						break;
					}
					
					
				}

				if(checkClaimId)
				{


				ClaimProcessing claimProcessing=claimService.getClaimDetails(claimId,officerType);

				if(claimProcessing == null)
				{
				response.setContentType("text/html");
				request.setAttribute("msg", "<center><h1><b><font color='red'>No rights to register</font></b></h1></center>");
				RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/Claim_Status.jsp");
				rd.forward(request,response);
				}
				else
				{

				   ClaimProcessing claimProcessingReason=claimService.getClaimStatusReason(claimId,officerType);
				//System.out.println(claimProcessing);
				
				   /*if(claimProcessingReason == null)
				   {
					RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/Claim_Status.jsp");
					request.setAttribute("msg", "<center><h1><b><font color='red'>Please enter a valid claim Id!!</font></b></h1></center>");
					rd.forward(request,response);
				   }
				   else*/
				    
					RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/ClaimStatusForm.jsp");
					request.setAttribute("claimProcessing",claimProcessingReason);
					rd.forward(request,response);
				    
			     }
			}
				else
				{
			    RequestDispatcher rd=request.getRequestDispatcher("/Insurance Claims Processing/Claim_Status.jsp");
				request.setAttribute("msg", "<center><h1><b><font color='red'>Claim id does not exist</font></b></h1></center>");
				rd.forward(request,response);
			    }
					
			}
			
<<<<<<< .mine
		
		
		
		
		
			
=======
>>>>>>> .r10284
			
		}
		
	}


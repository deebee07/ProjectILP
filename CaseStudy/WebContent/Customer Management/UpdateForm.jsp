<%@page import="com.util.Service"%>
<%@page import="com.model.Customer"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% response.setHeader( "Cache-Control", "no-store, no-cache, must-revalidate");  //HTTP 1.1
         response.setHeader("Pragma","no-cache"); //HTTP 1.0
         response.setDateHeader ("Expires", -1); //prevents caching at the proxy server
         
         if(request.getSession(false).getAttribute("userid")==null)
         {
        %>
        
        <jsp:forward page="${pageContext.request.contextPath}/../LoginPage.jsp"></jsp:forward>
        <% 
         }
    %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/projectStyle.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Reg1.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Reg2.css">
<title>Customer Forms</title>		
		
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validation.js"></script>
<title>Update Form</title>
</head>
<body class="bg-cyan">



	
<% 

HttpSession loginSession = request.getSession(true);
String loginName=(String)loginSession.getAttribute("userid");


%>

	

<div class=header>

<img alt="India Assurance" src="<%=request.getContextPath()%>/img/department (1).gif" align="left" >
Welcome <%=loginName %>	
<a href="<%=request.getContextPath()%>/HomePage.jsp">HOME</a> |
<a href="<%=request.getContextPath()%>/LoginController">LOG OUT</a>

</div>
 



		<div class="body body-s">
<% 		

Service service= new Service();

int custInt=(Integer) request.getAttribute("custId");
HttpSession sessionn = request.getSession(true);
sessionn.setAttribute("custAction", "updateCustomer");
sessionn.setAttribute("custID", custInt);

ArrayList<Customer> customers=service.allCustomer();

for(Customer customer:customers)
{
	
if(customer.getCustomerId()==custInt){
%>
<% 
String invalid= (String) request.getAttribute("invalid");

if(invalid!=null){
%>
<center><h1><font color="red"><b> <%=invalid %></b></font></h1></center>
<%} %>
<form  action="<%=request.getContextPath()%>/Controller" name="updateCustomer" method="post" onsubmit="return validateUpdate(document.updateCustomer.name,document.updateCustomer.dob,document.updateCustomer.email,document.updateCustomer.address,document.updateCustomer.number);" class="form">	
<fieldset>
				<header>Customer Updation  Form for Customer ID <%=custInt%></header>		
		


			<section>
						<label class="input" for="name">
							<i class="icon-append icon-user"></i>
							<input type="text" placeholder="Customer name" name="name" id ="name"  onblur="return ValidateName(document.updateCustomer.name);" value="<%=customer.getCustomerName()%>"required>
							<b class="tooltip tooltip-bottom-right"><font color="red">Required Field *</font></b>
						</label>
			</section>
					
					<section>
						<label class="input" for="dob">
							<i class="icon-append icon-dob"></i>
							<input type="text" placeholder="Date Of Birth" name="dob" id ="dob"   onblur="return validateDate(document.updateCustomer.dob);" value="<%=customer.getCustomerDob()%>" required>
							<b class="tooltip tooltip-bottom-right"><font color="red">Required Field * DD/MM/YYYY Format</font></b>
						</label>
					</section>
					
					
		
		            <section>
						<label class="input" for="email">
							<i class="icon-append icon-email"></i>
							<input type="text" placeholder="Email" name="email" id ="email" onblur="return ValidateEmail(document.updateCustomer.email);" value="<%=customer.getCustomerEmail()%>" required>
							<b class="tooltip tooltip-bottom-right"><font color="red">Required Field *</font></b>
						</label>
					</section>
		
	
			      
						<label class="input" for="address">
							<i class="icon-append icon-addr"></i>
							<textarea placeholder="-------------------Customer Address-------------------"  name="address" id="address" rows="4" cols="40" style="font-family: inherit; font-size:medium;" onblur="return ValidateAddress(document.updateCustomer.address);" required><%= customer.getCustomerAddress()%></textarea>
							<b class="tooltip tooltip-bottom-right"><font color="red">Required Field *</font></b>
						</label>
					
		<br>
		
		          <section>
						<label class="input" for="number">
							<i class="icon-append icon-contact"></i>
							<input type="text" placeholder="Contact Number" name="number"id="number" onblur="return ValidateNumber(document.updateCustomer.number);"value="<%=customer.getCustomerContactNumber()%>" required></input>
							<b class="tooltip tooltip-bottom-right"><font color="red">Required Field *</font></b>
						</label>
					</section>
		
		
		
		
		
		<footer>
					<input type="submit" value="Update" class="button" >
				</footer>
		
				</fieldset>
				</form>
		</div>

		
<% 

%>	



<%}//end if
} //end for
%>

<div class=footer>
Copyright � 2014 <a href="#">newindiaassurance@ltd</a>
</div>







</body>
</html>
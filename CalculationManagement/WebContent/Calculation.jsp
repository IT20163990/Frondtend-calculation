<%@page import="com.Calculation"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery.min.js"></script> 
<script src="Components/calculation.js"></script> 
</head>
<body>
	<div class="container">
	<div class="row">
		<div class="col">
			<h1 class="font-weight-bold">CALCULATION DETAILS</h1>
				<form class="row g-3" id="formCalculation" name="formCalculation" method="post" action="Calculation.jsp">  
					<div class="col-md-6">
						<label class="form-label">Calculation Month:</label>  
	 	 				<input id="month" name="month" type="text"  class="form-control form-control-sm" placeholder="Enter Month" required>
					</div>
					
					<div class="col-md-6">
						<label class="form-label">Calculation Units:</label>
						<input id="units" name="units" type="text" class="form-control form-control-sm" placeholder="Enter Units" required>
					</div>    
  					
  					<div class="col-md-6">
	  					<label class="form-label">Unit Cost:</label>
	  					<input id="unit_cost" name="unit_cost" class="form-control datepicker" type="text" placeholder="Enter Cost" required>
  					</div>
					 
					<div class="col-md-6">
						<label class="form-label">Calculation Total:</label>
					  	<input id="total" name="total" type="text" class="form-control form-control-sm" placeholder="Enter Total" required>
					</div>
					
					<div class="col-12">
	  					<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary" required>  
						<input type="hidden" id="hidCalculationIDSave" name="hidCalculationIDSave" value="">
  					</div>
					 
				</form>
				
				<div id="alertSuccess" class="alert alert-success"> </div>
				
				<div id="alertError" class="alert alert-danger"></div>
				
				<br>
				<div id="divCalculationGrid">
					<%
						Calculation calObj = new Calculation();
						out.print(calObj.readCalculation());
					%>
				</div>
			</div>
		</div>
	</div>

</body>

</body>
</html>
$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateCalculationForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidCalculationIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
		url : "CalculationAPI",  
		type : type,  
		data : $("#formCalculation").serialize(),  
		dataType : "text",  
		complete : function(response, status)  
		{   
			onCalculationSaveComplete(response.responseText, status);  
		} 
	}); 
});


function onCalculationSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divCalculationGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidCalculationIDSave").val("");  
	$("#formCalculation")[0].reset(); 
}


//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidCalculationIDSave").val($(this).data("billid"));     
	$("#month").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#units").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#unit_cost").val($(this).closest("tr").find('td:eq(2)').text());  
	$("#total").val($(this).closest("tr").find('td:eq(3)').text());  
	
});


//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "CalculationAPI",
		type : "DELETE",
		data : "billId=" + $(this).data("billid"),
		dataType : "text",
		complete : function(response, status)
		{
			onCalculationDeleteComplete(response.responseText, status);   
		}
	}); 
});


function onCalculationDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divCalculationGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}

//CLIENT-MODEL================================================================
function validateCalculationForm()
{
	// 	MONTH
	if ($("#month").val().trim() == "")
	{
		return "Insert Month.";
	}
	
	// UNITS
	if ($("#units").val().trim() == "")
	{
		return "Insert Units.";
	}
	
	// unit cost-------------------------------
	if ($("#unit_cost").val().trim() == "")
	{
		return "Insert Unit Cost.";
	}
	
	// is numerical value
	var tmpunit_cost = $("#unit_cost").val().trim();
	if (!$.isNumeric(tmpunit_cost))
	{
		return "Insert a numerical value for Total Price.";
	}
	
	// convert to decimal price
	$("#unit_cost").val(parseFloat(tmpunit_cost).toFixed(2));
	
	// TOTAL-------------------------------
	if ($("#unit_cost").val().trim() == "")
	{
		return "Insert Unit Cost.";
	}
	
	// is numerical value
	var tmpTotal = $("#total").val().trim();
	if (!$.isNumeric(tmpTotal))
	{
		return "Insert a numerical value for Total Price.";
	}
	
	// convert to decimal total
	$("#total").val(parseFloat(tmpTotal).toFixed(2));
	
	
	return true;
}


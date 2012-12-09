<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%!public long getCurrentTime() {
		return System.nanoTime();
	}%>

<c:set var="unique_page_id"><%=getCurrentTime()%></c:set>
<c:set var="selectFormToConfigureDivId">selectFormToConfigureDiv-${unique_page_id}</c:set>
<c:set var="selectFormToConfigureSelectId">selectFormToConfigureSelect-${unique_page_id}</c:set>
<c:set var="selectFormToConfigureButtonId">selectFormToConfigureButton-${unique_page_id}</c:set>
<c:set var="selectFormToConfigureConfigurationFormId">selectFormToConfigureConfigurationForm-${unique_page_id}</c:set>

<script>
  $(document).ready(
      function() {

        $("#${selectFormToConfigureSelectId}").multiselect({
          multiple : false,
          selectedList : 1,
          header : false
        });

        $("#${selectFormToConfigureButtonId}").button().click(
            function() {
              $.ajax({
                url: "getFormToConfigure.html",
                data: {
                  formToConfigure : $("#${selectFormToConfigureSelectId} :selected").val()
                },
                type: "GET",
                success: function(responseData) {
                  $("#${selectFormToConfigureConfigurationFormId}").html(
                      responseData);
                }
              });
            });
      });
</script>

<div id="${selectFormToConfigureDivId}" class="">
	<div>
		<label><i>Select a Form to Configure</i></label> <select
			id="${selectFormToConfigureSelectId}">
			<option value="Donor">Donor Form</option>
			<option value="CollectedSample">Collection Form</option>
			<option value="TestResult">Test Result Form</option>
			<option value="Product">Product Form</option>
			<option value="Request">Request Form</option>
			<option value="ProductUsage">Usage Form</option>
		</select>
		<button id="${selectFormToConfigureButtonId}"
			style="margin-left: 30px;">Configure Selected Form</button>
	</div>
	<div id="${selectFormToConfigureConfigurationFormId}"></div>
</div>
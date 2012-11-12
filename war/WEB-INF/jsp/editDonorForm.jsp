<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%!public long getCurrentTime() {
		return System.nanoTime();
	}%>

<c:set var="unique_page_id"><%=getCurrentTime()%></c:set>
<c:set var="editDonorFormDivId">editDonorFormDiv-${unique_page_id}</c:set>
<c:set var="editDonorFormId">editDonorForm-${unique_page_id}</c:set>
<c:set var="deleteDonorConfirmDialogId">deleteDonorConfirmDialog-${unique_page_id}</c:set>
<c:set var="genderSelectorId">genderSelector-${unique_page_id}</c:set>
<c:set var="bloodGroupSelectorId">bloodTypeSelector-${unique_page_id}</c:set>
<c:set var="birthDateInputId">birthDateInput-${unique_page_id}</c:set>
<c:set var="updateDonorButtonId">updateDonorButton-${unique_page_id}</c:set>
<c:set var="deleteDonorButtonId">deleteDonorButton-${unique_page_id}</c:set>
<c:set var="addCollectionButtonId">addCollectionButton-${unique_page_id}</c:set>

<script>
$(document).ready(
    function() {

      $("#${updateDonorButtonId}").button({
        icons : {
          primary : 'ui-icon-plusthick'
        }
      }).click(function() {
        if ("${model.existingDonor}" == "true")
        	updateExistingDonor($("#${editDonorFormId}")[0], "${editDonorFormDivId}");
        else
          addNewDonor($("#${editDonorFormId}")[0], "${editDonorFormDivId}");
      });

      $("#${deleteDonorButtonId}").button({
        icons : {
          primary : 'ui-icon-minusthick'
        }
      }).click(
          function() {
            $("#${deleteDonorConfirmDialogId}").dialog(
                {
                  modal : true,
                  title : "Confirm Delete",
                  buttons : {
                    "Delete" : function() {
                      var donorNumber = $("#${editDonorFormId}").find(
                          "[name='donorNumber']").val();
                      deleteDonor(donorNumber);
                      $(this).dialog("close");
                    },
                    "Cancel" : function() {
                      $(this).dialog("close");
                    }
                  }
                });
          });

      $("#${goBackButtonId}").button({
        icons : {
          primary : 'ui-icon-circle-arrow-w'
        }
      }).click(function() {
        window.history.back();
        return false;
      });

      $("#${addCollectionButtonId}").button({
        icons : {
          primary : 'ui-icon-disk'
        }
      }).click(
              function() {
                var parentDivId = $("#${editDonorFormDivId}").parent().attr(
                    "id");
                replaceContent(parentDivId, "${model.requestUrl}",
                    "editCollectionFormGenerator.html", {
                      donorNumber : "${model.donorNumber}"
                    });
                return false;
              });

      $("#${genderSelectorId}").multiselect({
        multiple : false,
        selectedList : 1,
        header : false
      });

      $("#${bloodGroupSelectorId}").multiselect({
        multiple : false,
        selectedList : 1,
        header : false
      });

      $("#${birthDateInputId}").datepicker({
        changeMonth : true,
        changeYear : true,
        minDate : -36500,
        maxDate : 0,
        dateFormat : "mm/dd/yy",
        yearRange : "c-100:c0",
      });      
    });
</script>

<c:if test="${model.hasErrors}">
	<script>
     showErrorMessage("${model.message}");
	</script>
</c:if>
<c:if test="${model.success == true}">
	<script>
     showMessage("${model.message}");
	</script>
</c:if>
<c:if test="${model.success == false}">
	<script>
     showErrorMessage("${model.message}");
	</script>
</c:if>

<div id="${editDonorFormDivId}" class="editFormDiv">
	<form:form id="${editDonorFormId}" method="POST" class="editForm"
		commandName="editDonorForm">
		<div>
			<form:label path="donorNumber">Donor Number</form:label>
			<form:input path="donorNumber" />
			<form:errors class="formError" path="donor.donorNumber" delimiter=", "></form:errors>
		</div>
		<div>
			<form:label path="firstName">${model.firstNameDisplayName}</form:label>
			<form:input path="firstName" />
			<form:errors class="formError" path="donor.firstName" delimiter=", "></form:errors>
		</div>
		<div>
			<form:label path="middleName">Middle Name</form:label>
			<form:input path="middleName" />
			<form:errors class="formError" path="donor.middleName" delimiter=", "></form:errors>
		</div>
		<div>
			<form:label path="lastName">${model.lastNameDisplayName}</form:label>
			<form:input path="lastName" />
			<form:errors class="formError" path="donor.lastName" delimiter=", "></form:errors>
		</div>
		<div>
			<form:label path="birthDate">${model.dobDisplayName}</form:label>
			<form:input path="birthDate" id="${birthDateInputId}" />
			<form:errors class="formError" path="donor.birthDate" delimiter=", "></form:errors>
		</div>
		<div>
			<form:label path="gender">${model.genderDisplayName}</form:label>
			<form:select path="gender" id="${genderSelectorId}">
				<form:option value="male" label="Male" />
				<form:option value="female" label="Female" />
				<form:option value="not_known" label="Not Known" />
				<form:option value="not_applicable" label="Not Applicable" />
			</form:select>
			<form:errors class="formError" path="donor.gender" delimiter=", "></form:errors>
		</div>
		<div>
			<form:label path="bloodGroup">Blood Group</form:label>
			<form:select path="bloodGroup" id="${bloodGroupSelectorId}">
				<form:option value="Unknown" label="Unknown" />
				<form:option value="A+" label="A+" />
				<form:option value="A-" label="A-" />
				<form:option value="B+" label="B+" />
				<form:option value="B-" label="B-" />
				<form:option value="AB+" label="AB+" />
				<form:option value="AB-" label="AB-" />
				<form:option value="O+" label="O+" />
				<form:option value="O-" label="O-" />
			</form:select>
				<form:errors class="formError" path="bloodGroup" delimiter=", "></form:errors>
		</div>
		<div>
			<form:label path="address">Address</form:label>
			<form:textarea path="address"
				maxlength="255" />
			<form:errors class="formError" path="donor.address" delimiter=", "></form:errors>
		</div>
		<div>
			<form:label path="city">City</form:label>
			<form:input path="city" />
			<form:errors class="formError" path="donor.city" delimiter=", "></form:errors>
		</div>
		<div>
			<form:label path="state">State</form:label>
			<form:input path="state" />
			<form:errors class="formError" path="donor.state" delimiter=", "></form:errors>
		</div>
		<div>
			<form:label path="country">Country</form:label>
			<form:input path="country" />
			<form:errors class="formError" path="donor.country" delimiter=", "></form:errors>
		</div>
		<div>
			<form:label path="zipcode">Zip Code</form:label>
			<form:input path="zipcode" />
			<ul>
				<form:errors class="formError" path="donor.zipcode" delimiter=", "></form:errors>
			</ul>
		</div>
		<div>
			<form:label path="notes">Notes</form:label>
			<form:textarea path="notes"
				maxlength="255" />
			<form:errors class="formError" path="donor.notes"></form:errors>
		</div>
		<div>
			<button type="button" id="${updateDonorButtonId}"
				style="margin-left: 10px">Save</button>
			<button type="button" id="${deleteDonorButtonId}"
				style="margin-left: 10px">Delete</button>
			<c:if test="${model.existingDonor == 'true'}">
				<button type="button" id="${addCollectionButtonId}"
					style="margin-left: 10px">Add collection for this donor</button>
			</c:if>

		</div>
</form:form>
</div>

<div id="${deleteDonorConfirmDialogId}" style="display: none">Are
	you sure you want to delete this Donor?</div>

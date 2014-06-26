<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<sec:authorize access="hasRole(T(utils.PermissionConstants).VIEW_DONOR)">
<div class="formFormatClass printableArea">
  <!-- <br />
   <div class="donorBarcode"></div> -->
  
    <div>
   		<label>Donor Codes</label>
   		<c:if test="${empty donorCodeGroups}">
        	<label>-</label>
      	</c:if>
      	<c:if test="${not empty donorCodeGroups}">
	      	<c:forEach var="donorCodeGroup" items="${donorCodeGroups}">
	        	<label>${donorCodeGroup.donorCodeGroup}</label>
	        	<br>
            	<label></label>
	        </c:forEach>
        </c:if>
    </div>
    
     <c:if test="${donorFields.donorNumber.hidden != true }">
        <div>
          <label>${donorFields.donorNumber.displayName}</label>
          <label>${donor.donorNumber}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.firstName.hidden != true }">
        <div>
          <label>${donorFields.firstName.displayName}</label>
          <label>
          <c:if test="${donor.title != 'Blank' }">
               ${donor.title}
          </c:if>
          ${donor.firstName}</label>

        </div>
      </c:if>
      <c:if test="${donorFields.middleName.hidden != true }">
        <div>
          <label>${donorFields.middleName.displayName}</label>
          <label>${donor.middleName}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.lastName.hidden != true }">
        <div>
          <label>${donorFields.lastName.displayName}</label>
          <label>${donor.lastName}</label>
        </div>
      </c:if>   
      <c:if test="${donorFields.callingName.hidden != true }">
        <div>
          <label>${donorFields.callingName.displayName}</label>
          <label>${donor.callingName}</label>
        </div>
      </c:if>   
      <c:if test="${donorFields.dateOfLastDonation.hidden != true }">
        <div>
          <label>${donorFields.dateOfLastDonation.displayName}</label>
          <label>${donor.dateOfLastDonation}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.birthDate.hidden != true }">
        <div>
          <label>${donorFields.birthDate.displayName}</label>
          <label style="width:300px">${donor.birthDate}
          <c:if test="${donorFields.birthDateEstimated.hidden != true }">
             <c:if test="${donor.birthDateEstimated == true}">
                    (${donorFields.birthDateEstimated.displayName})
             </c:if>
          </c:if>
          </label>
        </div>
      </c:if>
      <c:if test="${donorFields.age.hidden != true }">
        <div>
          <label>${donorFields.age.displayName}</label>
          <c:if test="${not empty donor.age}">
            <label>${donor.age} years</label>
          </c:if>
          <c:if test="${empty donor.age}">
            <label>${donor.age}</label>
          </c:if>
        </div>
      </c:if>
      <c:if test="${donorFields.dateOfFirstDonation.hidden != true }">
        <div>
          <label>${donorFields.dateOfFirstDonation.displayName}</label>
          <label>${donor.dateOfFirstDonation}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.gender.hidden != true }">
        <div>
          <label>${donorFields.gender.displayName}</label>
          <label>${donor.gender}</label>
        </div>
      </c:if>
       <c:if test="${donorFields.preferredLanguage.hidden != true }">
        <div>
          <label>${donorFields.preferredLanguage.displayName}</label>
          <label>${donor.preferredLanguage}</label>
        </div>
      </c:if>
       <c:if test="${donorFields.idType.hidden != true }">
        <div>
          <label>${donorFields.idType.displayName}</label>
          <label>${donor.idType}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.idNumber.hidden != true }">
        <div>
          <label>${donorFields.idNumber.displayName}</label>
          <label>${donor.idNumber}</label>
        </div>
      </c:if>
       <c:if test="${donorFields.mobileNumber.hidden != true }">
        <div>
          <label>${donorFields.mobileNumber.displayName}</label>
          <label>${donor.contact.mobileNumber}</label>
        </div>
        </c:if>
       <c:if test="${donorFields.homeNumber.hidden != true }">
        <div>
          <label>${donorFields.homeNumber.displayName}</label>
          <label>${donor.contact.homeNumber}</label>
        </div>
        </c:if>  
       <c:if test="${donorFields.workNumber.hidden != true }">
        <div>
          <label>${donorFields.workNumber.displayName}</label>
          <label>${donor.contact.workNumber}</label>
        </div>
        </c:if>
      <c:if test="${donorFields.email.hidden != true }">
        <div>
          <label>${donorFields.email.displayName}</label>
          <label>${donor.contact.email}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.contactMethodType.hidden != true }">
        <div>
          <label>${donorFields.contactMethodType.displayName}</label>
          <label>${donor.contactMethodType}</label>
        </div>
        </c:if>
        
      <c:if test="${donorFields.preferredAddressType.hidden != true }">
        <div>
          <label>${donorFields.preferredAddressType.displayName}</label>
          <label>${donor.preferredAddressType}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.donorPanel.hidden != true}">
        <div>
          <label>${donorFields.donorPanel.displayName}</label>
          <label>${donor.donorPanel}</label>
        </div>
      </c:if>
       <c:if test="${donorFields.homeAddress.hidden != true }">
       <div style="margin-left: 5px;">
            <b><label path="">${donorFields.homeAddress.displayName}</label></b>
       </div>
       </c:if>                 
     <c:if test="${donorFields.homeAddress.hidden != true }">
        <div>
          <label>${donorFields.addressLine1.displayName}</label>
          <label>${donor.address.homeAddressLine1}</label>
        </div>
      </c:if>
     <c:if test="${donorFields.homeAddress.hidden != true }">
        <div>
          <label>${donorFields.addressLine2.displayName}</label>
          <label>${donor.address.homeAddressLine2}</label>
        </div>
      </c:if>
   
      <c:if test="${donorFields.city.hidden != true }">
        <div>
          <label>${donorFields.city.displayName}</label>
          <label>${donor.address.homeAddressCity}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.province.hidden != true }">
        <div>
          <label>${donorFields.province.displayName}</label>
          <label>${donor.address.homeAddressProvince}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.district.hidden != true }">
        <div>
          <label>${donorFields.district.displayName}</label>
          <label>${donor.address.homeAddressDistrict}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.state.hidden != true }">
        <div>
          <label>${donorFields.state.displayName}</label>
          <label>${donor.address.homeAddressState}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.country.hidden != true }">
        <div>
          <label>${donorFields.country.displayName}</label>
          <label>${donor.address.homeAddressCountry}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.zipcode.hidden != true }">
        <div>
          <label>${donorFields.zipcode.displayName}</label>
          <label>${donor.address.homeAddressZipcode}</label>
        </div>
      </c:if>
        <c:if test="${donorFields.postalAddress.hidden != true }">
                              	<div style="margin-left: 5px;">
                                    <b><label path="">${donorFields.postalAddress.displayName}</label></b>
	</div>
        </c:if>                 
      <c:if test="${donorFields.postalAddress.hidden != true }">
        <div>
          <label>${donorFields.addressLine1.displayName}</label>
          <label>${donor.address.postalAddressLine1}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.postalAddress.hidden != true }">
        <div>
          <label>${donorFields.addressLine2.displayName}</label>
          <label>${donor.address.postalAddressLine2}</label>
        </div>
      </c:if>
    <c:if test="${donorFields.city.hidden != true }">
        <div>
          <label>${donorFields.city.displayName}</label>
          <label>${donor.address.postalAddressCity}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.province.hidden != true }">
        <div>
          <label>${donorFields.province.displayName}</label>
          <label>${donor.address.postalAddressProvince}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.district.hidden != true }">
        <div>
          <label>${donorFields.district.displayName}</label>
          <label>${donor.address.postalAddressDistrict}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.state.hidden != true }">
        <div>
          <label>${donorFields.state.displayName}</label>
          <label>${donor.address.postalAddressState}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.country.hidden != true }">
        <div>
          <label>${donorFields.country.displayName}</label>
          <label>${donor.address.postalAddressCountry}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.zipcode.hidden != true }">
        <div>
          <label>${donorFields.zipcode.displayName}</label>
          <label>${donor.address.postalAddressZipcode}</label>
        </div>
      </c:if>
       <c:if test="${donorFields.workAddress.hidden != true }">
                              	<div style="margin-left: 5px;">
                                    <b><label path="">${donorFields.workAddress.displayName}</label></b>
      </div>
        </c:if>         
      <c:if test="${donorFields.workAddress.hidden != true }">
        <div>
          <label>${donorFields.addressLine1.displayName}</label>
          <label>${donor.address.workAddressLine1}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.workAddress.hidden != true }">
        <div>
          <label>${donorFields.addressLine2.displayName}</label>
          <label>${donor.address.workAddressLine2}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.city.hidden != true }">
        <div>
          <label>${donorFields.city.displayName}</label>
          <label>${donor.address.workAddressCity}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.province.hidden != true }">
        <div>
          <label>${donorFields.province.displayName}</label>
          <label>${donor.address.workAddressProvince}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.district.hidden != true }">
        <div>
          <label>${donorFields.district.displayName}</label>
          <label>${donor.address.workAddressDistrict}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.state.hidden != true }">
        <div>
          <label>${donorFields.state.displayName}</label>
          <label>${donor.address.workAddressState}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.country.hidden != true }">
        <div>
          <label>${donorFields.country.displayName}</label>
          <label>${donor.address.workAddressCountry}</label>
        </div>
      </c:if>
      <c:if test="${donorFields.zipcode.hidden != true }">
        <div>
          <label>${donorFields.zipcode.displayName}</label>
          <label>${donor.address.workAddressZipcode}</label>
        </div>
      </c:if>

      <c:if test="${donorFields.notes.hidden != true }">
        <div>
          <label>${donorFields.notes.displayName}</label>
          <label>${donor.notes}</label>
        </div>
      </c:if>
      <br />

      <div>
        <label>${donorFields.lastUpdatedTime.displayName}</label>
        <label style="width: auto;">${donor.lastUpdated}</label>
      </div>
      <div>
        <label>${donorFields.lastUpdatedBy.displayName}</label>
        <label style="width: auto;">${donor.lastUpdatedBy}</label>
      </div>
        <hr />
      </div>
  </sec:authorize>

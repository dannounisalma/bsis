package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import model.collectedsample.CollectedSample;
import model.collectionbatch.CollectionBatch;
import model.testbatch.TestBatch;
import model.donor.Donor;
import backingform.TestResultBackingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import repository.CollectedSampleRepository;
import repository.DonorRepository;
import repository.TestBatchRepository;
import repository.bloodtesting.BloodTestingRepository;
import model.bloodtesting.TTIStatus;
import repository.bloodtesting.BloodTypingStatus;
import repository.bloodtesting.BloodTypingMatchStatus;

import utils.PermissionConstants;
import viewmodel.CollectedSampleViewModel;
import viewmodel.DonorViewModel;
import viewmodel.BloodTestingRuleResult;

@RestController
@RequestMapping("testresults")
public class TestResultController {

  @Autowired
  private CollectedSampleRepository collectedSampleRepository;

  @Autowired
  private TestBatchRepository testBatchRepository;
  
  @Autowired
  private BloodTestingRepository bloodTestingRepository;
  
  @Autowired
  private DonorRepository donorRepository;
  
  public TestResultController() {
  }

  @RequestMapping(value = "{donationIdentificationNumber}", method = RequestMethod.GET)
  @PreAuthorize("hasRole('"+PermissionConstants.VIEW_TEST_OUTCOME+"')")
  public ResponseEntity findTestResult(@PathVariable String donationIdentificationNumber ) {

    Map<String, Object> map = new HashMap<String, Object>();
    CollectedSample c = collectedSampleRepository.findCollectedSampleByCollectionNumber(donationIdentificationNumber);
    BloodTestingRuleResult results =  bloodTestingRepository.getAllTestsStatusForCollection(c.getId());
    map.put("donation", new CollectedSampleViewModel(c));
    map.put("testResults", results);
    return new ResponseEntity(map, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/search", method = RequestMethod.GET)
  @PreAuthorize("hasRole('"+PermissionConstants.VIEW_TEST_OUTCOME+"')")
  public ResponseEntity findTestResultsForTestBatch(HttpServletRequest request,
		@RequestParam(value = "testBatch", required = true) Long testBatchId) {
	  
		Map<String, Object> map = new HashMap<String, Object>();
		
		TestBatch testBatch = testBatchRepository.findTestBatchById(testBatchId);
		List<CollectionBatch> collectionBatches = testBatch.getCollectionBatches();
		List<Integer> donationBatchIds = new ArrayList<Integer>();
		for(CollectionBatch collectionBatch : collectionBatches){
			donationBatchIds.add(collectionBatch.getId());
		}
	
	    List<BloodTestingRuleResult> ruleResults =
	    		bloodTestingRepository.getAllTestsStatusForDonationBatches(donationBatchIds);
	
		map.put("testResults", ruleResults);
	
		return new ResponseEntity(map, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/overview", method = RequestMethod.GET)
  @PreAuthorize("hasRole('"+PermissionConstants.VIEW_TEST_OUTCOME+"')")
  public ResponseEntity findTestResultsOverviewForTestBatch(HttpServletRequest request,
		@RequestParam(value = "testBatch", required = true) Long testBatchId) {
	  
		Map<String, Object> map = new HashMap<String, Object>();
		
		TestBatch testBatch = testBatchRepository.findTestBatchById(testBatchId);
		List<CollectionBatch> collectionBatches = testBatch.getCollectionBatches();
		List<Integer> donationBatchIds = new ArrayList<Integer>();
		for(CollectionBatch collectionBatch : collectionBatches){
			donationBatchIds.add(collectionBatch.getId());
		}
	
	    List<BloodTestingRuleResult> ruleResults =
	    		bloodTestingRepository.getAllTestsStatusForDonationBatches(donationBatchIds);
	    
	    Boolean pendingBloodTypingTests = false;
	    Boolean pendingTTITests = false;
	    Boolean basicBloodTypingComplete = true;
	    Boolean basicTTIComplete = true;
	    Boolean pendingBloodTypingMatchTests = false;
	    
	    for(BloodTestingRuleResult result : ruleResults){
	    	if(!result.getBloodTypingStatus().equals(BloodTypingStatus.COMPLETE)){
	    		basicBloodTypingComplete = false;
	    	}
	    	if(result.getTTIStatus().equals(TTIStatus.NOT_DONE)){
	    		basicTTIComplete = false;
	    	}
	    	if(result.getPendingBloodTypingTestsIds().size() > 0){
	    		pendingBloodTypingTests = true;
	    	}
	    	if(result.getPendingTTITestsIds().size() > 0){
	    		pendingTTITests = true;
	    	}
	    	if(!result.getBloodTypingStatus().equals(BloodTypingStatus.NOT_DONE) && (result.getBloodTypingMatchStatus().equals(BloodTypingMatchStatus.NO_MATCH) ||
	    	   result.getBloodTypingMatchStatus().equals(BloodTypingMatchStatus.AMBIGUOUS))	){
	    		pendingBloodTypingMatchTests = true;
	    	}
	    }
	
		map.put("pendingBloodTypingTests", pendingBloodTypingTests);
		map.put("pendingTTITests", pendingTTITests);
		map.put("basicBloodTypingComplete", basicBloodTypingComplete);
		map.put("basicTTIComplete", basicTTIComplete);
		map.put("pendingBloodTypingMatchTests", pendingBloodTypingMatchTests);
	
		return new ResponseEntity(map, HttpStatus.OK);
  }
  
  @PreAuthorize("hasRole('"+PermissionConstants.ADD_TEST_OUTCOME+"')")
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> saveTestResults(
		@RequestBody @Valid TestResultBackingForm form) {
	
		HttpStatus httpStatus = HttpStatus.CREATED;        
		boolean success = true;
		String errorMessage = "";
		Map<Long, Map<Long, String>> errorMap = null;
		Map<String, Object> fieldErrors = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		CollectedSample collectedSample = collectedSampleRepository.verifyCollectionNumber(form.getDonationIdentificationNumber());
	
		Map<String, Object> results = null;
		
		results = bloodTestingRepository.saveBloodTestingResults(collectedSample.getId(), form.getTestResults(), true);
	    if (results != null)
	      errorMap = (Map<Long, Map<Long, String>>) results.get("errors");
	    if (errorMap != null && !errorMap.isEmpty())
	      success = false;
	
	    if (success) {
	      map.put("testresults", results.get("bloodTestingResults"));
	    }
	    else {
	      // errors found
	      map.put("errorMap", errorMap);
	      map.put("uninterpretableResults", results.get("uninterpretableResults"));
	      map.put("errorMessage", "There were errors adding tests.");      
	      httpStatus = HttpStatus.BAD_REQUEST;
	    }
	
		map.put("success", success);
		return new ResponseEntity<Map<String, Object>>(map, httpStatus);
  }
  
  @PreAuthorize("hasRole('"+PermissionConstants.ADD_TEST_OUTCOME+"')")
  @RequestMapping(value = "/bloodgroupmatches", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> saveBloodGroupMatchTestResults(
		  @RequestParam(value = "donationIdentificationNumber", required = true) String donationIdentificationNumber,
		  @RequestParam(value = "bloodAbo", required = true) String bloodAbo,
		  @RequestParam(value = "bloodRh", required = true) String bloodRh) {
	  
		HttpStatus httpStatus = HttpStatus.CREATED;        
		boolean success = true;
		String errorMessage = "";
		Map<Long, Map<Long, String>> errorMap = null;
		Map<String, Object> fieldErrors = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		//CollectedSample collectedSample = collectedSampleRepository.verifyCollectionNumber(form.getDonationIdentificationNumber());
	
		Map<String, Object> results = null;
		
		CollectedSample donation = collectedSampleRepository.findCollectedSampleByCollectionNumber(donationIdentificationNumber);
		Donor donor = donation.getDonor();
		donor.setBloodAbo(bloodAbo);
		donor.setBloodRh(bloodRh);
		donation.setBloodAbo(bloodAbo);
		donation.setBloodRh(bloodRh);
		donation.setBloodTypingMatchStatus(BloodTypingMatchStatus.MATCH);
		
		Donor updatedDonor = donorRepository.updateDonor(donor);
		CollectedSample cs = collectedSampleRepository.updateCollectedSample(donation);
		
		map.put("donor", getDonorsViewModel(donorRepository.findDonorById(updatedDonor.getId())));
        return new ResponseEntity<Map<String, Object>>(map, httpStatus);
  }
  
  private DonorViewModel getDonorsViewModel(Donor donor) {
    DonorViewModel donorViewModel = new DonorViewModel(donor);
    return donorViewModel;
  }

}

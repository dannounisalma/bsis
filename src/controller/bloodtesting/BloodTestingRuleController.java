package controller.bloodtesting;

import backingform.BloodTestingRuleBackingForm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import model.bloodtesting.rules.BloodTestingRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repository.bloodtesting.BloodTestingRepository;
import utils.PermissionConstants;
import viewmodel.BloodTestingRuleViewModel;

@RestController
@RequestMapping("/bloodtestrules")
public class BloodTestingRuleController {

    @Autowired
    private BloodTestingRepository bloodTestingRepository;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + PermissionConstants.MANAGE_BLOOD_TYPING_RULES + "')")
    public ResponseEntity configureBloodTypingTests() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bloodTypingTests", bloodTestingRepository.getBloodTypingTests());
        List<BloodTestingRuleViewModel> rules = new ArrayList<BloodTestingRuleViewModel>();
        for (BloodTestingRule rule : bloodTestingRepository.getBloodTypingRules(true)) {
            rules.add(new BloodTestingRuleViewModel(rule));
        }
        map.put("bloodTypingRules", rules);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + PermissionConstants.VIEW_BLOOD_TYPING_OUTCOME + "')")
    public ResponseEntity getBloodTypingRuleSummary(@PathVariable Integer id) {

        Map<String, Object> map = new HashMap<String, Object>();
        BloodTestingRuleViewModel bloodTestingRule;
        bloodTestingRule = new BloodTestingRuleViewModel(bloodTestingRepository.getBloodTestingRuleById(id));
        map.put("bloodTypingRule", bloodTestingRule);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('" + PermissionConstants.MANAGE_BLOOD_TESTS + "')")
    public ResponseEntity saveBloodTypingRule(
            @RequestBody BloodTestingRuleBackingForm form) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	BloodTestingRule typingRule = bloodTestingRepository.saveBloodTypingRule(form.getTypingRule());
        map.put("bloodtest", new BloodTestingRuleViewModel(typingRule));
        return new ResponseEntity(map,HttpStatus.CREATED);


    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('" + PermissionConstants.MANAGE_BLOOD_TESTS + "')")
    public ResponseEntity updateNewBloodTypingRule(
            @RequestBody BloodTestingRuleBackingForm form, @PathVariable Integer id) {

        Map<String, Object> map = new HashMap<String, Object>();
        form.setId(id);
        BloodTestingRule typingRule = form.getTypingRule();
        bloodTestingRepository.updateBloodTypingRule(typingRule);
        map.put("bloodtest", new BloodTestingRuleViewModel(bloodTestingRepository.getBloodTestingRuleById(id)));
        return new ResponseEntity(map, HttpStatus.OK);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('" + PermissionConstants.MANAGE_BLOOD_TESTS + "')")
    public ResponseEntity deleteBloodTypingRule(HttpServletRequest request,
            @PathVariable Integer id) {

        bloodTestingRepository.deleteBloodTestingRule(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}

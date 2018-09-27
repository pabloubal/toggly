package org.pubal.togglebyfeature.test;

import org.pubal.togglebyfeature.core.config.ToggleConfiguration;
import org.pubal.togglebyfeature.core.interfaces.IFeatureProvider;
import org.pubal.togglebyfeature.core.interfaces.IFeatureStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestRestController {
    @Autowired
    private IFeatureStateRepository featureStateRepository;

    @Autowired
    private IFeatureProvider featureProvider;

    @Autowired
    private ToggleConfiguration toggleConfiguration;

    @Autowired
    private ITestService testService;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    public String test(){
        return testService.test();
    }
}

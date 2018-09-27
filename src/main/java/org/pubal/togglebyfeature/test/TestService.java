package org.pubal.togglebyfeature.test;

import org.pubal.togglebyfeature.core.interfaces.ToggleByFeature;
import org.pubal.togglebyfeature.core.interfaces.ToggleFallback;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@ToggleFallback(TestServiceFallback.class)
public class TestService implements ITestService {

    @Override
    @ToggleByFeature(ToggleEnum.SampleFeature)
    public String test() {
        return "TestService";
    }
}

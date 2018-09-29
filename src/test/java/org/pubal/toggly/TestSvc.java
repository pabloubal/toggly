package org.pubal.toggly;

import org.pubal.toggly.core.interfaces.Toggly;
import org.pubal.toggly.core.interfaces.TogglyFallback;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@TogglyFallback(TestSvcFallback.class)
public class TestSvc implements ITestSvc {

    @Override
    @Toggly(TogglyTestEnum.TestEnabledByDefaultFeature)
    public Boolean test() {
        return true;
    }

    @Override
    @Toggly(TogglyTestEnum.TestDisabledByDefaultFeature)
    public Boolean testDisabled() {
        return true;
    }
}

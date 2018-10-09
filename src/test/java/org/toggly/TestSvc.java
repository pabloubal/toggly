package org.toggly;

import org.toggly.core.interfaces.Toggly;
import org.toggly.core.interfaces.TogglyFallback;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
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

package org.pubal.toggly;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TestSvcFallback implements ITestSvc {
    @Override
    public Boolean test() {
        return false;
    }

    @Override
    public Boolean testDisabled() {
        return false;
    }
}

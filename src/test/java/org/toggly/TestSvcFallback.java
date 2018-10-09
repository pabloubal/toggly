package org.toggly;

import org.springframework.stereotype.Component;

/**
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
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

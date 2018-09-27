package org.pubal.togglebyfeature.test;

import org.springframework.stereotype.Service;

@Service
public class TestServiceFallback implements ITestService {
    @Override
    public String test() {
        return "TestServiceFallback";
    }
}

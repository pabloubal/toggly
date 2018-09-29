package org.pubal.toggly;

import org.pubal.toggly.core.SampleFeatureEnum;
import org.pubal.toggly.core.config.ConfigBuilder;
import org.pubal.toggly.core.config.TogglyConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public TogglyConfiguration toggleConfiguration() {
        return new ConfigBuilder()
                .setFeatureEnum(TogglyTestEnum.class)
                .build();
    }

    @Bean
    public ITestSvc testSvc(){
        return new TestSvc();
    }

    @Bean
    public ITestSvc testFallbackSvc(){
        return new TestSvcFallback();
    }
}

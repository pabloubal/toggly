package org.toggly;

import org.toggly.core.config.ConfigBuilder;
import org.toggly.core.config.TogglyConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
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

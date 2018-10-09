package org.toggly;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.toggly.core.TogglyFeatureState;
import org.toggly.core.aspect.TogglyAspect;
import org.toggly.core.config.TogglyConfiguration;
import org.toggly.core.repositories.TogglyCloudConfigStateRepository;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.toggly.core.interfaces.IFeatureStateRepository;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TogglyAutoConfiguration.class})
@Import(TestConfig.class)
public class TogglyApplicationTests {
    @Autowired
    TogglyConfiguration tc;

    @Autowired
    IFeatureStateRepository fsr;

    @Autowired
    TestSvc testsvc;

    @Autowired
    TogglyAspect aspect;

    Map<String, TogglyFeatureState> togglyMap;

    ITestSvc proxiedTestSvc;

    @Before
    public void setup(){
        if(fsr instanceof TogglyCloudConfigStateRepository) {
            togglyMap = ((TogglyCloudConfigStateRepository)fsr).getToggly();
            togglyMap.clear();
        }

        AspectJProxyFactory factory = new AspectJProxyFactory(testsvc);
        factory.addAspect(aspect);

        proxiedTestSvc = factory.getProxy();
    }

	@Test
	public void testFeatureEnabled() throws Throwable {
        Objects.requireNonNull(togglyMap, "Detected null togglyMap");

        togglyMap.put(TogglyTestEnum.TestEnabledByDefaultFeature,
                new TogglyFeatureState(TogglyTestEnum.TestEnabledByDefaultFeature, true, null, null));

        Assert.isTrue(proxiedTestSvc.test(), "Expected feature to be active but it's not");

	}

    @Test
    public void testFeatureDisabled() throws Throwable {
        Objects.requireNonNull(togglyMap, "Detected null togglyMap");

        togglyMap.put(TogglyTestEnum.TestEnabledByDefaultFeature,
                new TogglyFeatureState(TogglyTestEnum.TestEnabledByDefaultFeature, false, null, null));

        Assert.isTrue(!proxiedTestSvc.test(), "Expected feature to be disabled but it's ENABLED");

    }

    @Test
    public void testStrategyEnabled() throws Throwable {
        Objects.requireNonNull(togglyMap, "Detected null togglyMap");

        Map<String, String> parameters = new ConcurrentHashMap<>();
        parameters.put("ratio", "100");

        togglyMap.put(TogglyTestEnum.TestEnabledByDefaultFeature,
                new TogglyFeatureState(TogglyTestEnum.TestEnabledByDefaultFeature,
                        true, "RatioStrategy", parameters));

        Assert.isTrue(proxiedTestSvc.test(), "Expected feature to be active but it's not");

    }

    @Test
    public void testStrategyDisabled() throws Throwable {
        Objects.requireNonNull(togglyMap, "Detected null togglyMap");

        Map<String, String> parameters = new ConcurrentHashMap<>();
        parameters.put("ratio", "0");

        togglyMap.put(TogglyTestEnum.TestEnabledByDefaultFeature,
                new TogglyFeatureState(TogglyTestEnum.TestEnabledByDefaultFeature,
                        false, "RatioStrategy", parameters));

        Assert.isTrue(!proxiedTestSvc.test(), "Expected feature to be disabled but it's ENABLED");

    }







    @Test
    public void testEnabledByDefaultFeatureEnabled() throws Throwable {
        Objects.requireNonNull(togglyMap, "Detected null togglyMap");

        Assert.isTrue(proxiedTestSvc.test(), "Expected feature to be active but it's not");

    }

    @Test
    public void testEnabledByDefaultDisabled() throws Throwable {
        Objects.requireNonNull(togglyMap, "Detected null togglyMap");

        togglyMap.put(TogglyTestEnum.TestEnabledByDefaultFeature,
                new TogglyFeatureState(TogglyTestEnum.TestEnabledByDefaultFeature, false, null, null));

        Assert.isTrue(!proxiedTestSvc.test(), "Expected feature to be disabled but it's ENABLED");

    }



    @Test
    public void testDisabledByDefaultFeatureEnabled() throws Throwable {
        Objects.requireNonNull(togglyMap, "Detected null togglyMap");

        togglyMap.put(TogglyTestEnum.TestDisabledByDefaultFeature,
                new TogglyFeatureState(TogglyTestEnum.TestDisabledByDefaultFeature, true, null, null));

        Assert.isTrue(proxiedTestSvc.testDisabled(), "Expected feature to be active but it's not");

    }

    @Test
    public void testDisabledByDefaultDisabled() throws Throwable {
        Objects.requireNonNull(togglyMap, "Detected null togglyMap");

        Assert.isTrue(!proxiedTestSvc.testDisabled(), "Expected feature to be disabled but it's ENABLED");

    }

}

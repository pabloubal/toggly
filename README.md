# Toggly

Toggly is a Spring-Boot feature toggle framework intended to simplify both feature toggling management and A/B testing. It's also a simplified lightweight version of Togglz.

Toggly understands every feature toggle creates technical debt, that's why it's pretty straight forward to work with Toggly and refactor your code once the feature is stable in production.

# Features!

  - Annotation-driven feature toggling
  - Activation Strategies - mostly used for A/B testing
  - SPI for performing your own customizations of the framework

# Quick Start
```
<dependency>
	<groupId>org.toggly</groupId>
	<artifactId>toggly-spring-starter</artifactId>
	<version>${toggly.version}</version>
</dependency>
```
# So, how does it work?
First create a TogglyEnum class that will contain your features. You need to annotate it as @Component and `@TogglyEnum`
```
@Component
@TogglyEnum
public class ToggleEnum{
    public static final String TestFeature="TestFeature";
}
```
When the application starts, Toggly will search for a Bean annotated with `@TogglyEnum` and will use it as a Features List reference.

Then, encapsulate in a method the business logic you'd like to surround with a feature toggle. i.e.:
```
@Toggly(ToggleEnum.TestFeature)
public String test() {
    return "TestService";
}
```

Then, you just simply configure your Feature Toggle on your application.yml, application.properties or config-server like this:

```
toggly.TestFeature.enabled=true
or...
toggly:
  TestFeature:
    enabled: true
```
Toggly ships by default with a cloud-config-ready features repository, which means it can read and refresh features configuration based on cloud configuration. 
If it's not your style, cause maybe you're more into persisting feature toggles on an RDS, or you want to use a distributed cache system to allocate your toggles, Toggly lets you implement your own features repository just by creating a custom Bean that implements *IFeatureStateRepository*. Toggly will automatically detect this bean on startup and use it as your features repository.

# Is that all???
So what do we do if the Feature Toggle is disabled?
You can annotate your class as `@TogglyFallback(FallbackClass.class)` in order to fallback to an alternative method in case your Feature Toggle is disabled.
i.e.
```
@TogglyFallback(TestServiceFallback.class)
public class TestService implements ITestService{
    @Override
    @Toggly(ToggleEnum.TestFeature)
    public String test() {
        return "TestService";
    }
}

@Service
public class TestServiceFallback implements ITestService {
    @Override
    public String test() {
        return "TestServiceFallback";
    }
}
```
>**NOTE: No need to implement the same interface on both classes, but they DO need to implement the same method signature.**

When Toggly detects an `@Toggly` annotated method which feature is disabled, it will automatically look for an `@TogglyFallback` annotation on its class. 
If it's present, then it will look for a Bean corresponding to the Fallback class and redirect the method call to that Bean.
If it's NOT present, it will just return **NULL**.

# Alternative usage
If you're not into delegating control of your application's flow to an external framework, you can request Spring Boot to inject an *IToggleManager* Bean and ask the status of a particular feature to decide the flow your own way. i.e.:
```
public class TogglyManagerTest {
    @Autowired
    IToggleManager toggleManager;
    public String test(){
        if(toggleManager.isActive(TogglyEnum.TestFeature)){
            return "TestService";
        }
        
        return "TestServiceFallback";
    }
}
```

# Technical Debt
When you're done testing your feature and you know it's ready for production, all you need to do is:
* Remove corresponding `@Toggly` annotation
* Remove corresponding `@TogglyFallback` annotation from the class (if no other Feature is using it)
* Delete the fallback class (if no other Feature is using it)

So finally your application should look like:
```
public class TestService implements ITestService{
    @Override
    public String test() {
        return "TestService";
    }
}
```

# Extra Features

**Cluster-Refresh:**
If property **toggly.endpoints.clustered-refresh.enabled** is set to **true**, Toggly creates a custom Spring Boot Actuator (/cluster-refresh) for executing a /refresh on each member of the cluster, by discovering them (depends on @EnableDiscoveryClient).
For this purpose there are two extra properties you can set:
* **toggly.endpoints.clustered-refresh.serviceName:** name of the service as registered in service registry (consul, eureka, etc)
* **toggly.endpoints.clustered-refresh.refreshUrl:** path to refresh actuator. Defaults to "/refresh"


**Activation-Strategies:**
Maybe you're in the streaming industry and your boss wants to know how users react to a brand new recommender algorithm, and that's a perfect fit for an A/B Testing strategy.
Toggly ships with a *RatioStrategy* which you can use by simply configuring it to a feature. Strategies are set at feature level, so you can play around with different strategies for different features.
Toggly provides *IActivationStrategy* interface which you can implement to set your own activation strategies. During startup Toggly gathers all Beans that implement IActivationStrategy, so you only need implement the interface and create the Bean. Then, if Toggly detects that an active feature contains an activation strategy, before informing the feature as active, it asks the activation strategy whether the feature should ne active or not.
This is very straight forward for implementing A/B Testing. i.e. You could probably set a feature to use RatioStrategy with a 10% ratio, which translates as 10% of the requests going through the new feature

package org.toggly;

import org.toggly.core.SampleFeatureEnum;
import org.toggly.core.TogglyManager;
import org.toggly.core.config.ConfigBuilder;
import org.toggly.core.config.TogglyConfiguration;
import org.toggly.core.interfaces.IToggleManager;
import org.toggly.core.providers.TogglyFeatureProvider;
import org.toggly.core.repositories.TogglyCloudConfigStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.toggly.core.interfaces.IActivationStrategy;
import org.toggly.core.interfaces.IFeatureProvider;
import org.toggly.core.interfaces.IFeatureStateRepository;
import org.toggly.core.interfaces.TogglyEnum;

import java.util.Map;

/**
 * Spring Boot Autoconfiguration.
 *
 * Creates and configures the whole Toggly ecosystem.
 *
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
@Configuration
@ComponentScan({"org.toggly.core"})
public class TogglyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(TogglyConfiguration.class)
    public TogglyConfiguration toggleConfiguration(ApplicationContext ctx) {
        //Check if a user-defined enum already exists
        Map<String, Object> featureEnums = ctx.getBeansWithAnnotation(TogglyEnum.class);

        if(CollectionUtils.isEmpty(featureEnums)) {
            return new ConfigBuilder()
                    .setFeatureEnum(SampleFeatureEnum.class)
                    .build();
        }
        else{
            return new ConfigBuilder()
                    .setFeatureEnum(featureEnums.entrySet().stream()
                                        .findFirst()
                                        .get()
                                        .getValue()
                                        .getClass()
                    ).build();
        }
    }

    @Bean
    @ConditionalOnMissingBean(IFeatureStateRepository.class)
    public IFeatureStateRepository defaultFeatureStateRepository(){
        return new TogglyCloudConfigStateRepository();
    }

    @Bean
    @ConditionalOnMissingBean(IFeatureProvider.class)
    public IFeatureProvider defaultFeatureProvider(TogglyConfiguration config){
        TogglyFeatureProvider togglyFeatureProvider = new TogglyFeatureProvider();

        togglyFeatureProvider.addFeaturesFromEnum(config.getFeatureEnum());

        return togglyFeatureProvider;
    }

    @Bean
    @ConditionalOnMissingBean(IToggleManager.class)
    public IToggleManager defaultToggleManager(TogglyConfiguration togglyConfiguration,
                                               IFeatureStateRepository featureStateRepository,
                                               IFeatureProvider featureProvider,
                                               @Autowired(required = false)
                                               IActivationStrategy[] activationStrategies){
        return new TogglyManager(togglyConfiguration, featureStateRepository, featureProvider, activationStrategies);
    }

}
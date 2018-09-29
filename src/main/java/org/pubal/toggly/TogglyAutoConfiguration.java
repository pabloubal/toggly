package org.pubal.toggly;

import org.pubal.toggly.core.SampleFeatureEnum;
import org.pubal.toggly.core.TogglyManager;
import org.pubal.toggly.core.config.ConfigBuilder;
import org.pubal.toggly.core.config.TogglyConfiguration;
import org.pubal.toggly.core.interfaces.IActivationStrategy;
import org.pubal.toggly.core.interfaces.IFeatureProvider;
import org.pubal.toggly.core.interfaces.IFeatureStateRepository;
import org.pubal.toggly.core.interfaces.IToggleManager;
import org.pubal.toggly.core.providers.TogglyFeatureProvider;
import org.pubal.toggly.core.repositories.TogglyCloudConfigStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"org.pubal.toggly.core"})
public class TogglyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(TogglyConfiguration.class)
    public TogglyConfiguration toggleConfiguration() {
        return new ConfigBuilder()
                .setFeatureEnum(SampleFeatureEnum.class)
                .build();
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

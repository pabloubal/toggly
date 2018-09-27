package org.pubal.togglebyfeature;

import org.pubal.togglebyfeature.core.SampleFeatureEnum;
import org.pubal.togglebyfeature.core.ToggleManager;
import org.pubal.togglebyfeature.core.config.ConfigBuilder;
import org.pubal.togglebyfeature.core.config.ToggleConfiguration;
import org.pubal.togglebyfeature.core.interfaces.IActivationStrategy;
import org.pubal.togglebyfeature.core.interfaces.IFeatureProvider;
import org.pubal.togglebyfeature.core.interfaces.IFeatureStateRepository;
import org.pubal.togglebyfeature.core.interfaces.IToggleManager;
import org.pubal.togglebyfeature.core.providers.FeatureProvider;
import org.pubal.togglebyfeature.core.repositories.CloudConfigStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan({"org.pubal.togglebyfeature.core"})
public class ToggleByFeatureStarterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ToggleConfiguration.class)
    public ToggleConfiguration toggleConfiguration() {
        return new ConfigBuilder()
                .setFeatureEnum(SampleFeatureEnum.class)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(IFeatureStateRepository.class)
    public IFeatureStateRepository defaultFeatureStateRepository(){
        return new CloudConfigStateRepository();
    }

    @Bean
    @ConditionalOnMissingBean(IFeatureProvider.class)
    public IFeatureProvider defaultFeatureProvider(){
        FeatureProvider featureProvider = new FeatureProvider();

        featureProvider.addFeaturesFromEnum(SampleFeatureEnum.class);

        return featureProvider;
    }

    @Bean
    @ConditionalOnMissingBean(IToggleManager.class)

    public IToggleManager defaultToggleManager(ToggleConfiguration toggleConfiguration,
                                               IFeatureStateRepository featureStateRepository,
                                               IFeatureProvider featureProvider,
                                               @Autowired(required = false)
                                               IActivationStrategy[] activationStrategies){
        return new ToggleManager(toggleConfiguration, featureStateRepository, featureProvider, activationStrategies);
    }

}

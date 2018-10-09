package org.toggly.core.repositories;

import org.toggly.core.TogglyFeatureState;
import org.toggly.core.interfaces.IFeatureStateRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of IFeatureStateRepository
 *
 * Provides access to feature state based on cloud-config server
 *
 * Available Configurations:
 * toggly.{FEATURE_NAME}.enabled=true/false
 * toggly.{FEATURE_NAME}.activationStrategy={ACTIVATION_STRATEGY_NAME}
 * toggly.{FEATURE_NAME}.parameters.{PARAM_1_NAME}={VALUE_1}
 * toggly.{FEATURE_NAME}.parameters.{PARAM_2_NAME}={VALUE_2}
 *
 * i.e.:
 * toggly.SampleFeature.enabled=true
 * toggly.SampleFeature.activationStrategy=RatioStrategy
 * toggly.SampleFeatures.parameters.ratio=50
 *
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
@ConfigurationProperties
@RefreshScope
public class TogglyCloudConfigStateRepository implements IFeatureStateRepository {
    private Map<String, TogglyFeatureState> toggly;

    public TogglyCloudConfigStateRepository(){
        this.toggly = new ConcurrentHashMap();
    }

    @Override
    public TogglyFeatureState getFeatureState(String featureName) {
        return this.toggly.get(featureName);
    }

    @Override
    public void setFeatureState(TogglyFeatureState fs){
        this.toggly.put(fs.getFeature(), fs);
    }

    public Map<String, TogglyFeatureState> getToggly(){return this.toggly;}


}

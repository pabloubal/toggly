package org.pubal.togglebyfeature.core.repositories;

import org.pubal.togglebyfeature.core.interfaces.IFeature;
import org.pubal.togglebyfeature.core.interfaces.IFeatureStateRepository;
import org.pubal.togglebyfeature.core.interfaces.IFeatureState;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "togglebyfeature")
//@RefreshScope
public class CloudConfigStateRepository implements IFeatureStateRepository {
    private Map<String, IFeatureState> featureStateMap;

    public CloudConfigStateRepository(){
        this.featureStateMap = new HashMap<>();
    }

    @Override
    public IFeatureState getFeatureState(String featureName) {
        return this.featureStateMap.get(featureName);
    }

    @Override
    public void setFeatureState(IFeatureState fs){
        this.featureStateMap.put(fs.getFeature().name(), fs);
    }

    public void setFeatureStateMap(Map<String, IFeatureState> featureStateMap){
        this.featureStateMap = featureStateMap;
    }
}

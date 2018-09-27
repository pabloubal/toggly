package org.pubal.togglebyfeature.core.repositories;

import org.pubal.togglebyfeature.core.FeatureState;
import org.pubal.togglebyfeature.core.interfaces.IFeatureStateRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ConfigurationProperties
@RefreshScope
public class CloudConfigStateRepository implements IFeatureStateRepository {
    private Map<String, FeatureState> toggly;

    public CloudConfigStateRepository(){
        this.toggly = new ConcurrentHashMap();
    }

    @Override
    public FeatureState getFeatureState(String featureName) {
        return this.toggly.get(featureName);
    }

    @Override
    public void setFeatureState(FeatureState fs){
        this.toggly.put(fs.getFeature(), fs);
    }

    public Map<String, FeatureState> getToggly(){return this.toggly;}
}

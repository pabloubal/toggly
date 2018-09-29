package org.pubal.toggly.core.repositories;

import org.pubal.toggly.core.TogglyFeatureState;
import org.pubal.toggly.core.interfaces.IFeatureStateRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

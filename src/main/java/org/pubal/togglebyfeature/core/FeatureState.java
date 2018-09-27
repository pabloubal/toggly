package org.pubal.togglebyfeature.core;

import org.pubal.togglebyfeature.core.interfaces.IActivationStrategy;
import org.pubal.togglebyfeature.core.interfaces.IFeature;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FeatureState{
    private String feature;
    private boolean enabled;
    private String activationStrategy;
    private Map<String, String> parameters;

    public FeatureState(){
        this.parameters = new ConcurrentHashMap<>();
    }

    public FeatureState(String feature,
                        boolean enabled,
                        String activationStrategy,
                        Map<String, String> parameters){
        this.feature=feature;
        this.enabled=enabled;
        this.activationStrategy=activationStrategy;
        this.parameters=parameters;
    }

    public boolean isActive() {
        return this.enabled;
    }

    public String getActivationStrategy() {
        return this.activationStrategy;
    }

    public String getFeature() {
        return this.feature;
    }

    public String getValue(String key) {
        return this.parameters.get(key);
    }

    public void setFeature(String feature){
        this.feature = feature;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public void setParameters(Map<String, String> parameters){
        this.parameters = parameters;
    }

    public Map<String, String> getParameters(){
        return this.parameters;
    }

    public void setActivationStrategy(String activationStrategy){
        this.activationStrategy=activationStrategy;
    }


}

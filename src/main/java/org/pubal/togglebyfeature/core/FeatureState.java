package org.pubal.togglebyfeature.core;

import org.pubal.togglebyfeature.core.interfaces.IActivationStrategy;
import org.pubal.togglebyfeature.core.interfaces.IFeature;
import org.pubal.togglebyfeature.core.interfaces.IFeatureState;

import java.util.Map;

public class FeatureState implements IFeatureState {
    private IFeature feature;
    private boolean enabled;
    private IActivationStrategy activationStrategy;
    private Map<String, String> parameters;

    public FeatureState(IFeature feature,
                        boolean enabled,
                        IActivationStrategy activationStrategy,
                        Map<String, String> parameters){
        this.feature=feature;
        this.enabled=enabled;
        this.activationStrategy=activationStrategy;
        this.parameters=parameters;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public IActivationStrategy getActivationStrategy() {
        return this.activationStrategy;
    }

    @Override
    public IFeature getFeature() {
        return this.feature;
    }

    @Override
    public String getValue(String key) {
        return this.parameters.get(key);
    }
}

package org.pubal.togglebyfeature.core.config;

import org.pubal.togglebyfeature.core.interfaces.IFeature;

public class ConfigBuilder {
    ToggleConfiguration toggleConfiguration;

    public ConfigBuilder(){
        this.toggleConfiguration = new ToggleConfiguration();
    }

    public ConfigBuilder setFeatureEnum(Class<? extends IFeature> featureEnum){
        toggleConfiguration.setFeatureEnum(featureEnum);
        return this;
    }

    public ToggleConfiguration build(){
        return this.toggleConfiguration;
    }
}

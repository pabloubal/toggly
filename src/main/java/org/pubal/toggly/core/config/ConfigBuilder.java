package org.pubal.toggly.core.config;

import org.pubal.toggly.core.interfaces.IFeature;

public class ConfigBuilder {
    TogglyConfiguration togglyConfiguration;

    public ConfigBuilder(){
        this.togglyConfiguration = new TogglyConfiguration();
    }

    public ConfigBuilder setFeatureEnum(Class<?> featureEnum){
        togglyConfiguration.setFeatureEnum(featureEnum);
        return this;
    }

    public TogglyConfiguration build(){
        return this.togglyConfiguration;
    }
}

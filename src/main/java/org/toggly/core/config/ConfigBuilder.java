package org.toggly.core.config;

/**
 * <p>
 * Configuration Builder
 * </p>
 *
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
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

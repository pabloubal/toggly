package org.toggly.core.config;

/**
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
public class TogglyConfiguration {
    private Class<?> featureEnum;

    public void setFeatureEnum(Class<?> featureEnum){
        this.featureEnum = featureEnum;
    }

    public Class<?> getFeatureEnum(){
        return featureEnum;
    }
}

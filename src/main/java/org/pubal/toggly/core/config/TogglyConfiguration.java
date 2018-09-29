package org.pubal.toggly.core.config;

public class TogglyConfiguration {
    private Class<?> featureEnum;

    public void setFeatureEnum(Class<?> featureEnum){
        this.featureEnum = featureEnum;
    }

    public Class<?> getFeatureEnum(){
        return featureEnum;
    }
}

package org.pubal.togglebyfeature.core.config;

import org.pubal.togglebyfeature.core.interfaces.IFeature;

public class ToggleConfiguration {
    private Class<? extends IFeature> featureEnum;

    public void setFeatureEnum(Class<? extends IFeature> featureEnum){
        this.featureEnum = featureEnum;
    }

    public Class<? extends IFeature> getFeatureEnum(){
        return featureEnum;
    }
}

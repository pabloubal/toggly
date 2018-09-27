package org.pubal.togglebyfeature.core.providers;

import org.pubal.togglebyfeature.core.FeatureState;
import org.pubal.togglebyfeature.core.interfaces.IFeature;
import org.pubal.togglebyfeature.core.interfaces.IFeatureProvider;
import org.pubal.togglebyfeature.core.interfaces.IFeatureState;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FeatureProvider implements IFeatureProvider {
    private Set<IFeature> features;
    private Class<? extends IFeature> featuresEnum;
    private FeatureState defaultFeatureState;

    public FeatureProvider(){

        this.features = new HashSet<>();
        this.defaultFeatureState = new FeatureState(null, false, null,null);
    }

    public void addFeaturesFromEnum(Class<? extends IFeature> featureEnum){
        Assert.isTrue(featureEnum.isEnum(),
                    String.format("Expected an Enum, but the provided class %s is of type %s",
                            featureEnum.getName(),
                            featureEnum.getTypeName()));

        this.featuresEnum = featureEnum;

        for (IFeature feature : featureEnum.getEnumConstants()) {
            this.features.add(feature);
        }
    }

    @Override
    public Set<IFeature> getFeatures(){
        return Collections.unmodifiableSet(this.features);
    }

    @Override
    public IFeatureState getDefaultFeatureState(String f) {
        return this.defaultFeatureState;
    }

    @Override
    public Class<? extends IFeature> getFeaturesEnum(){
        return this.featuresEnum;
    }
}

package org.pubal.togglebyfeature.core.interfaces;

import org.pubal.togglebyfeature.core.FeatureState;

import java.util.Set;

public interface IFeatureProvider {
    Set<IFeature> getFeatures();

    FeatureState getDefaultFeatureState(String f);

    Class<? extends IFeature> getFeaturesEnum();
}

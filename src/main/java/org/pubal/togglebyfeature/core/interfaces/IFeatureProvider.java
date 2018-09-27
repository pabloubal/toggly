package org.pubal.togglebyfeature.core.interfaces;

import java.util.Set;

public interface IFeatureProvider {
    Set<IFeature> getFeatures();

    IFeatureState getDefaultFeatureState(String f);

    Class<? extends IFeature> getFeaturesEnum();
}

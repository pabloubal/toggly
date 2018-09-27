package org.pubal.togglebyfeature.core.interfaces;

public interface IFeatureStateRepository {
    IFeatureState getFeatureState(String featureName);

    void setFeatureState(IFeatureState fs);
}

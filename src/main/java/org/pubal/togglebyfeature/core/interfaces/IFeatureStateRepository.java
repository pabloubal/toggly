package org.pubal.togglebyfeature.core.interfaces;

import org.pubal.togglebyfeature.core.FeatureState;

public interface IFeatureStateRepository {
    FeatureState getFeatureState(String featureName);

    void setFeatureState(FeatureState fs);
}

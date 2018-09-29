package org.pubal.toggly.core.interfaces;

import org.pubal.toggly.core.TogglyFeatureState;

public interface IFeatureStateRepository {
    TogglyFeatureState getFeatureState(String featureName);

    void setFeatureState(TogglyFeatureState fs);
}

package org.toggly.core.interfaces;

import org.toggly.core.TogglyFeatureState;

/**
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
public interface IFeatureStateRepository {
    TogglyFeatureState getFeatureState(String featureName);

    void setFeatureState(TogglyFeatureState fs);
}

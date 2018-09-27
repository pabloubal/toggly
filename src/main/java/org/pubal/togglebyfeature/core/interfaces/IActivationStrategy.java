package org.pubal.togglebyfeature.core.interfaces;

import org.pubal.togglebyfeature.core.FeatureState;

public interface IActivationStrategy {
    boolean isActive(FeatureState fs);

    String getStrategyName();
}

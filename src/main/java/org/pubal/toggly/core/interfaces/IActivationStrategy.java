package org.pubal.toggly.core.interfaces;

import org.pubal.toggly.core.TogglyFeatureState;

public interface IActivationStrategy {
    boolean isActive(TogglyFeatureState fs);

    String getStrategyName();
}

package org.pubal.togglebyfeature.core.interfaces;

public interface IActivationStrategy {
    boolean isActive(IFeatureState fs);

    String getStrategyName();
}

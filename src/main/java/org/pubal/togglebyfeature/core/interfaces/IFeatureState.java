package org.pubal.togglebyfeature.core.interfaces;

public interface IFeatureState {

    boolean isActive();

    IActivationStrategy getActivationStrategy();

    IFeature getFeature();

    String getValue(String key);

}

package org.pubal.toggly.core.interfaces;

public interface IFeatureState {

    boolean isActive();

    IActivationStrategy getActivationStrategy();

    IFeature getFeature();

    String getValue(String key);

}

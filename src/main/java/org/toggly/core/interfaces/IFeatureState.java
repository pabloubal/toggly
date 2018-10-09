package org.toggly.core.interfaces;

/**
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
public interface IFeatureState {

    boolean isActive();

    IActivationStrategy getActivationStrategy();

    IFeature getFeature();

    String getValue(String key);

}

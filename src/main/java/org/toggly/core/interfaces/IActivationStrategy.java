package org.toggly.core.interfaces;

import org.toggly.core.TogglyFeatureState;

/**
 * <p>
 *  Implement this interface in order to extend the capacity of Toggly to decide wether a feature shall be enabled or not.
 * </p>
 *
 * <p>
 *  It represents a strategy within a strategy pattern surrounding an active feature. This means that after Toggly
 *  determines a feature is enabled by configuration (or default), if this feature was configured to use an Activation
 *  Strategy, it will apply the corresponding strategy to determine if it should mark the feature as active or not.
 * </p>
 *
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
public interface IActivationStrategy {
    boolean isActive(TogglyFeatureState fs);

    String getStrategyName();
}

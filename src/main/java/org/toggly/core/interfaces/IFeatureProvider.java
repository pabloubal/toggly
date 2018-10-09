package org.toggly.core.interfaces;

import org.toggly.core.TogglyFeatureState;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * Provides access to user-defined feature list, as well as a default state.
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
public interface IFeatureProvider {
    Map<String, List<Annotation>> getFeatures();

    TogglyFeatureState getDefaultFeatureState(String f);

    Class<?> getFeaturesEnum();
}

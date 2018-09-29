package org.pubal.toggly.core.interfaces;

import org.pubal.toggly.core.TogglyFeatureState;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

public interface IFeatureProvider {
    Map<String, List<Annotation>> getFeatures();

    TogglyFeatureState getDefaultFeatureState(String f);

    Class<?> getFeaturesEnum();
}

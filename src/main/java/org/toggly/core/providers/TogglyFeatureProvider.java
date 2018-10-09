package org.toggly.core.providers;

import org.toggly.core.TogglyFeatureState;
import org.toggly.core.interfaces.EnabledByDefault;
import org.toggly.core.interfaces.IFeatureProvider;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementation of IFeatureProvider
 *
 * This implementation maps features to its annotations list in order to provide meta-data access
 *
 * i.e.:
 * @EnabledByDefault => This annotation on a feature determines default feature state.
 *
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
public class TogglyFeatureProvider implements IFeatureProvider {
    private Map<String, List<Annotation>> features;
    private Class<?> featuresEnum;
    private TogglyFeatureState defaultTogglyFeatureState;

    public TogglyFeatureProvider(){

        this.features = new ConcurrentHashMap<>();
        this.defaultTogglyFeatureState = new TogglyFeatureState(null, false, null,null);
    }

    public void addFeaturesFromEnum(Class<?> featureEnum){
        this.featuresEnum = featureEnum;

        for (Field f : getStaticStringFields(featuresEnum)) {
            this.features.put(f.getName(), Arrays.stream(f.getAnnotations()).collect(Collectors.toList()));
        }
    }

    private List<Field> getStaticStringFields(Class<?> featuresEnum){
        return Arrays.stream(featuresEnum.getDeclaredFields())
                .filter(f -> java.lang.reflect.Modifier.isPublic(f.getModifiers()) &&
                             java.lang.reflect.Modifier.isStatic(f.getModifiers()) &&
                             java.lang.reflect.Modifier.isFinal(f.getModifiers()) &&
                             f.getType() == String.class)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<Annotation>> getFeatures(){
        return Collections.unmodifiableMap(this.features);
    }

    @Override
    public TogglyFeatureState getDefaultFeatureState(String f) {
        if(!features.containsKey(f) || CollectionUtils.isEmpty(features.get(f)) ) {
            return defaultTogglyFeatureState;
        }

        TogglyFeatureState tfs;

        tfs = checkEnabledByDefault(f);

        if(!Objects.isNull(tfs)) {
            return tfs;
        }

        return this.defaultTogglyFeatureState;
    }

    private TogglyFeatureState checkEnabledByDefault(String f){
        if(features.get(f).stream()
                .filter(a -> a.annotationType()== EnabledByDefault.class)
                .findFirst()
                .isPresent()){
            return new TogglyFeatureState(f, true, null, null);
        }

        return null;
    }

    @Override
    public Class<?> getFeaturesEnum(){
        return this.featuresEnum;
    }
}

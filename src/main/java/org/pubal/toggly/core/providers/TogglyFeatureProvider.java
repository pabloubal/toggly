package org.pubal.toggly.core.providers;

import org.pubal.toggly.core.TogglyFeatureState;
import org.pubal.toggly.core.interfaces.EnabledByDefault;
import org.pubal.toggly.core.interfaces.IFeatureProvider;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
                .filter(f -> java.lang.reflect.Modifier.isStatic(f.getModifiers()) && f.getType() == String.class)
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

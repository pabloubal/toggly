package org.pubal.toggly.core;

import org.pubal.toggly.core.config.TogglyConfiguration;
import org.pubal.toggly.core.interfaces.*;

import java.util.Arrays;
import java.util.Objects;

public class TogglyManager implements IToggleManager {
    private static TogglyManager togglyManager;

    private TogglyConfiguration togglyConfiguration;
    private IActivationStrategy[] activationStrategies;
    private IFeatureStateRepository featuresRepo;
    private IFeatureProvider featureProvider;

    public TogglyManager(TogglyConfiguration togglyConfiguration,
                         IFeatureStateRepository featureStateRepository,
                         IFeatureProvider featureProvider,
                         IActivationStrategy[] activationStrategies) {
        this.featuresRepo = featureStateRepository;
        this.featureProvider = featureProvider;
        this.togglyConfiguration = togglyConfiguration;
        this.activationStrategies = activationStrategies;
        togglyManager = this;
    }

    public static TogglyManager getInstance(){
        return togglyManager;
    }

    @Override
    public boolean isActive(IFeature f) {
        return isActive(f.name());
    }

    @Override
    public boolean isActive(String f) {
        Objects.requireNonNull(f, "Feature cannot be null");

        TogglyFeatureState fs = featuresRepo.getFeatureState(f);

        if(Objects.isNull(fs)){
            fs = featureProvider.getDefaultFeatureState(f);
        }

        if(fs.isActive()){
            IActivationStrategy as = findActivationStrategy(fs.getActivationStrategy());

            if(Objects.isNull(as)){
                return true;
            }

            return as.isActive(fs);
        }

        return false;
    }

    private IActivationStrategy findActivationStrategy(String name){
        if(Objects.isNull(activationStrategies) || Objects.isNull(name)){
            return null;
        }

        return Arrays.stream(activationStrategies)
                .filter(a -> a.getStrategyName().equals(name))
                .findFirst()
                .orElse(null);
    }
}

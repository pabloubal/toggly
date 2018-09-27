package org.pubal.togglebyfeature.core;

import org.pubal.togglebyfeature.core.config.ToggleConfiguration;
import org.pubal.togglebyfeature.core.interfaces.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ToggleManager implements IToggleManager {
    private static ToggleManager toggleManager;

    private ToggleConfiguration toggleConfiguration;
    private IActivationStrategy[] activationStrategies;
    private IFeatureStateRepository featuresRepo;
    private IFeatureProvider featureProvider;

    public ToggleManager(ToggleConfiguration toggleConfiguration,
                         IFeatureStateRepository featureStateRepository,
                         IFeatureProvider featureProvider,
                         IActivationStrategy[] activationStrategies) {
        this.featuresRepo = featureStateRepository;
        this.featureProvider = featureProvider;
        this.toggleConfiguration = toggleConfiguration;
        this.activationStrategies = activationStrategies;
        toggleManager = this;
    }

    public static ToggleManager getInstance(){
        return toggleManager;
    }

    @Override
    public boolean isActive(IFeature f) {
        return isActive(f.name());
    }

    @Override
    public boolean isActive(String f) {
        Objects.requireNonNull(f, "Feature cannot be null");

        FeatureState fs = featuresRepo.getFeatureState(f);

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

    public IActivationStrategy findActivationStrategy(String name){
        if(Objects.isNull(activationStrategies) || Objects.isNull(name)){
            return null;
        }

        return Arrays.stream(activationStrategies)
                .filter(a -> a.getStrategyName().equals(name))
                .findFirst()
                .orElse(null);
    }
}

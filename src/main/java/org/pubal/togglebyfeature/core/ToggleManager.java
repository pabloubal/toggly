package org.pubal.togglebyfeature.core;

import org.pubal.togglebyfeature.core.config.ToggleConfiguration;
import org.pubal.togglebyfeature.core.interfaces.*;

import java.util.Objects;

public class ToggleManager implements IToggleManager {
    private static ToggleManager toggleManager;

    private ToggleConfiguration toggleConfiguration;
    private IFeatureStateRepository featuresRepo;
    private IFeatureProvider featureProvider;

    public ToggleManager(ToggleConfiguration toggleConfiguration,
                         IFeatureStateRepository featureStateRepository,
                         IFeatureProvider featureProvider) {
        this.featuresRepo = featureStateRepository;
        this.featureProvider = featureProvider;
        this.toggleConfiguration = toggleConfiguration;
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

        IFeatureState fs = featuresRepo.getFeatureState(f);

        if(Objects.isNull(fs)){
            fs = featureProvider.getDefaultFeatureState(f);
        }

        if(fs.isActive()){
            IActivationStrategy as = fs.getActivationStrategy();

            if(Objects.isNull(as)){
                return true;
            }

            return as.isActive(fs);
        }

        return false;
    }
}

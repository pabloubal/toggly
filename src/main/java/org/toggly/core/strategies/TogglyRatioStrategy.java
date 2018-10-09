package org.toggly.core.strategies;

import org.toggly.core.TogglyFeatureState;
import org.toggly.core.interfaces.IActivationStrategy;
import org.toggly.core.interfaces.IFeatureProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of IActivationStrategy
 *
 * Provides a random activation strategy within the activation-strategy pattern based on a given ratio in order to
 * make a final check to determine whether to mark the feature as active or not
 *
 * It's intended to perform A/B testing based on a random choice. i.e.: Enable a feature for 50% of the requests.
 *
 * i.e.
 * toggly.SampleFeatures.activationStrategy=RatioStrategy
 * toggly.SampleFeatures.parameters.ratio=100 => will always return true
 * toggly.SampleFeatures.parameters.ratio=0 => will always return false
 * toggly.SampleFeatures.parameters.ratio=50 => 50% chances to return true or false
 *
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
@Component
public class TogglyRatioStrategy implements IActivationStrategy {
    @Autowired
    IFeatureProvider featureProvider;

    private static final String STRATEGY_NAME = "RatioStrategy";
    private static final String RATIO_KEY = "ratio";

    @Override
    public boolean isActive(TogglyFeatureState fs) {
        Objects.requireNonNull(fs, "Expected a TogglyFeatureState but received a null object instead");

        String ratioparam = fs.getValue(RATIO_KEY);

        if(Objects.isNull(ratioparam)){
            return featureProvider.getDefaultFeatureState(fs.getFeature()).isActive();
        }

        if (ThreadLocalRandom.current().nextLong(1,100) <= Long.parseLong(ratioparam)){
            return true;
        }

        return false;
    }

    @Override
    public String getStrategyName() {
        return STRATEGY_NAME;
    }
}

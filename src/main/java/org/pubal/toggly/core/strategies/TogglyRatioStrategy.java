package org.pubal.toggly.core.strategies;

import org.pubal.toggly.core.TogglyFeatureState;
import org.pubal.toggly.core.interfaces.IActivationStrategy;
import org.pubal.toggly.core.interfaces.IFeatureProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

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

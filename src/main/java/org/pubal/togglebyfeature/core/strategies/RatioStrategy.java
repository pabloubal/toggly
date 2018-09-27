package org.pubal.togglebyfeature.core.strategies;

import org.pubal.togglebyfeature.core.FeatureState;
import org.pubal.togglebyfeature.core.interfaces.IActivationStrategy;
import org.pubal.togglebyfeature.core.interfaces.IFeatureProvider;
import org.pubal.togglebyfeature.core.interfaces.IFeatureState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RatioStrategy implements IActivationStrategy {
    @Autowired
    IFeatureProvider featureProvider;

    private static final String STRATEGY_NAME = "RatioStrategy";
    private static final String RATIO_KEY = "ratio";

    @Override
    public boolean isActive(FeatureState fs) {
        Objects.requireNonNull(fs, "Expected a FeatureState but received a null object instead");

        String ratioparam = fs.getValue(RATIO_KEY);

        if(Objects.isNull(ratioparam)){
            return featureProvider.getDefaultFeatureState(fs.getFeature()).isActive();
        }

        if (Long.parseLong(ratioparam) < ThreadLocalRandom.current().nextLong(1,100) ){
            return true;
        }

        return false;
    }

    @Override
    public String getStrategyName() {
        return STRATEGY_NAME;
    }
}

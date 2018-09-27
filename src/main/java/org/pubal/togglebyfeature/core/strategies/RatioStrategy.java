package org.pubal.togglebyfeature.core.strategies;

import org.pubal.togglebyfeature.core.interfaces.IActivationStrategy;
import org.pubal.togglebyfeature.core.interfaces.IFeatureProvider;
import org.pubal.togglebyfeature.core.interfaces.IFeatureState;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class RatioStrategy implements IActivationStrategy {
    @Autowired
    IFeatureProvider featureProvider;

    private static final String STRATEGY_NAME = "RatioStrategy";
    private static final String RATIO_KEY = "ratio";

    @Override
    public boolean isActive(IFeatureState fs) {
        Objects.requireNonNull(fs, "Expected an IFeatureState but received a null object instead");

        String ratioparam = fs.getValue(RATIO_KEY);

        if(Objects.isNull(ratioparam)){
            return featureProvider.getDefaultFeatureState(fs.getFeature().name()).isActive();
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

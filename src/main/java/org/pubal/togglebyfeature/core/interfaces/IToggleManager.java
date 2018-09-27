package org.pubal.togglebyfeature.core.interfaces;

import org.pubal.togglebyfeature.core.interfaces.IFeature;

public interface IToggleManager {
    boolean isActive(IFeature f);
    boolean isActive(String f);

}

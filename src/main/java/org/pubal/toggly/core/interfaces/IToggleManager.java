package org.pubal.toggly.core.interfaces;

public interface IToggleManager {
    boolean isActive(IFeature f);
    boolean isActive(String f);

}

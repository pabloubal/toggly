package org.toggly.core.interfaces;

/**
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
public interface IToggleManager {
    boolean isActive(IFeature f);
    boolean isActive(String f);

}

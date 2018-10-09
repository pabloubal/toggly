package org.toggly.core.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used for identifying Feature classes.
 *
 * i.e.:
 * @TogglyEnum
 * public class SampleFeatureEnum {
 *     public static final String SampleFeature="SampleFeature";
 * }
 *
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TogglyEnum {
}

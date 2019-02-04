package org.toggly.core.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>
 * Use this annotation around a Feature in order to enable it by default, in case no configuration is provided for it.
 * </p>
 *
 * <p>
 * i.e.
 * public class SampleFeatureEnum {
 *     <b>&#064;EnabledByDefault</b>
 *     public static final String SampleFeature="SampleFeature";
 * }
 * </p>
 *
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EnabledByDefault {
}

package org.toggly.core.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class-level annotation used to identify Fallback class
 *
 * i.e.:
 * <b>&#064;TogglyFallback(TogglyFallbackTest.class)</b>
 * public class TogglyTest{
 *     &#064;Toggly(SampleFeatureEnum.SampleFeature)
 *     public String testMethod(){
 *         return "Main Method"
 *     }
 * }
 *
 * public class TogglyFallbackTest{
 *     public String testMethod(){
 *         return "Fallback Method"
 *     }
 * }
 *
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TogglyFallback {
    Class value();
}

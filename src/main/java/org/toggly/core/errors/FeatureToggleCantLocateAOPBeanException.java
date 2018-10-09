package org.toggly.core.errors;

/**
 * <p>
 * Exception thrown in case Toggly is not able to find a proper Spring proxied Bean to fallback to.
 * </p>
 *
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */
public class FeatureToggleCantLocateAOPBeanException extends Exception {

    private static final long serialVersionUID = -1877156999813501560L;

    /**
     * Default parent constructor
     *
     * @param message
     */
    public FeatureToggleCantLocateAOPBeanException(String message) {
        super(message);
    }

    /**
     * Default parent constructor
     *
     * @param message
     * @param cause
     */
    public FeatureToggleCantLocateAOPBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Default parent constructor
     *
     * @param cause
     */
    public FeatureToggleCantLocateAOPBeanException(Throwable cause) {
        super(cause);
    }

}

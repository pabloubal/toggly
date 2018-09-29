package org.pubal.toggly.core.errors;

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

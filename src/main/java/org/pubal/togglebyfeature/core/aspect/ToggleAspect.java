package org.pubal.togglebyfeature.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.pubal.togglebyfeature.core.ToggleManager;
import org.pubal.togglebyfeature.core.errors.FeatureToggleCantLocateAOPBeanException;
import org.pubal.togglebyfeature.core.interfaces.ToggleByFeature;
import org.pubal.togglebyfeature.core.interfaces.ToggleFallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class ToggleAspect {
    private static final String ERROR_MESSAGE = "Fallback method contains annotations which are proxied through Spring AOP, but couldn't find any bean on ApplicationContext";
    //NEED TO BE CONTEXT AWARE IN ORDER TO BE ABLE TO PROCESS SPRING AOP ANNOTATIONS THROUGH PROXY BEAN
    @Autowired
    private ApplicationContext context;

    @Around("@annotation(feature)")
    public Object enterIfFeatureIsActive(ProceedingJoinPoint joinPoint, ToggleByFeature feature) throws Throwable {
        if(ToggleManager.getInstance().isActive(feature.value())){
            return joinPoint.proceed();
        } else {
            ToggleFallback annotation = joinPoint.getTarget().getClass().getAnnotation(ToggleFallback.class);
            if (annotation == null) {
                return null;
            }
            Method mainMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Method fallbackMethod = annotation.value().getMethod(mainMethod.getName(), mainMethod.getParameterTypes());
            Object targetBean = getTargetBeanWithoutSpringProxy(annotation);
            return fallbackMethod.invoke(targetBean, joinPoint.getArgs());
        }
    }

    private Object getTargetBeanWithoutSpringProxy(ToggleFallback annotation) throws Throwable {

        Map fallbackBeans = context.getBeansOfType(annotation.value());

        if(Objects.isNull(fallbackBeans)){
            throw new FeatureToggleCantLocateAOPBeanException(ERROR_MESSAGE);
        }

        return fallbackBeans.values()
                .stream()
                .findFirst()
                .orElseThrow(() -> new FeatureToggleCantLocateAOPBeanException(ERROR_MESSAGE));
    }
}

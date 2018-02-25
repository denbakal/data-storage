package ua.challenge.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Log4j2
public class LoggingAspect {
    @Pointcut("@annotation(ua.challenge.aspect.Loggable)")
    public void logging() {
    }

    @Around("logging()")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint) {
        long start = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        log.debug("Before invoking method: {}()", method.getName());
        Object value = null;
        long elapsedTime = 0L;
        try {
            value = proceedingJoinPoint.proceed();
            elapsedTime = System.currentTimeMillis() - start;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        log.debug("After invoking method: {}()", method.getName());
        log.debug("Method {}() execution time: {} milliseconds.", method.getName(), elapsedTime);
        return value;
    }
}

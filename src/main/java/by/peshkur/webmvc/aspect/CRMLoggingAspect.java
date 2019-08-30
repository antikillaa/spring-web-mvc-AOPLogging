package by.peshkur.webmvc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

    //setup logger
    private Logger logger = Logger.getLogger(getClass().getName());

    //setup pointcut declaration
    @Pointcut("execution(* by.peshkur.webmvc.controller.*.*(..))")
    public void forControllerPackage(){}

    //do the same for service and dao
    @Pointcut("execution(* by.peshkur.webmvc.service.*.*(..))")
    public void forServicePackage(){}

    @Pointcut("execution(* by.peshkur.webmvc.dao.*.*(..))")
    public void forDaoPackage(){}

    @Pointcut("forControllerPackage() || forDaoPackage() || forServicePackage()")
    public void forAppFlow(){
    }

    //add @Before advice
    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {

        //display methods we are calling
        String method = joinPoint.getSignature().toString();
        logger.info("======> in @Before: calling method: " + method);

        //display the arguments to the method

        //get arguments
        Object[] args = joinPoint.getArgs();

        //loop thru and display args
        for (Object object : args) {
            logger.info("======> argument: " + object);
        }
    }

    //add @AfterReturning advice
    @AfterReturning (
        pointcut = "forAppFlow()",
            returning = "result"
    )
    public void afterReturning(JoinPoint joinPoint, Object result) {
        //display method we are returning from
        String method = joinPoint.getSignature().toString();
        logger.info("======> in @AfterReturning: from method: " + method);

        //display data returned
        logger.info("======> result: " + result);
    }
}

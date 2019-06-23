package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

  private Logger logger = Logger.getLogger(CRMLoggingAspect.class.getName());

  @Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
  private void aspectForControllerPackage() {

  }

  @Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
  private void aspectForServicePackage() {}

  @Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
  private void aspectForDaoPackage() {}

  @Pointcut("aspectForControllerPackage() || aspectForServicePackage() || aspectForDaoPackage()")
  private void appFlow() {}

  @Before("appFlow()")
  private void before(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().toShortString();
    logger.info("@Before advice method called: " + methodName);

    List<Object> args = new ArrayList<>(Arrays.asList(joinPoint.getArgs())){};
    args.forEach(e -> logger.info("---> Argument passed: " + e.toString()));
  }

  @AfterReturning(
      pointcut = "appFlow()",
      returning = "result")
  private void afterReturning(JoinPoint joinPoint, Object result) {
    String methodName = joinPoint.getSignature().toShortString();
    logger.info("@AfterReturning advice method called: " + methodName);
    logger.info("===> result passed: " + result.toString());
  }
}

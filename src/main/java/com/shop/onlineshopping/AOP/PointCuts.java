package com.shop.onlineshopping.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {

    @Pointcut("within(com.shop.onlineshopping.controller.*)")
    public void inControllerLayer(){}

    @Pointcut("bean(*Service)")
    public void inService(){}

//    @Pointcut("execution(* com.beaconfire.springaop.AOPDemo.dao.DemoDAO.*Demo())")
//    public void inDAOLayer(){}

}

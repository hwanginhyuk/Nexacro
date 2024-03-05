//package kr.co.seoulit.insa.sys.aop;
//
//import kr.co.seoulit.insa.commsvc.systemmgmt.exception.IdNotFoundException;
//import kr.co.seoulit.insa.commsvc.systemmgmt.exception.PwMissMatchException;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.dao.DataAccessException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//
//
//@Component
//@Aspect
//@EnableAspectJAutoProxy
//@Slf4j
//public class CommonAspect {
//
//    public  CommonAspect(){}//기본 생성자가 존재하는 이유는 Spring AOP의 동작 방식과 관련이 있습니다.
//    // Spring AOP는 프록시 기반으로 동작하며, Aspect의 로직을 적용한 프록시 객체를 생성하여 주입해줍니다.
//    // 이때, 프록시 객체는 기본 생성자를 사용하여 생성됩니다.
//    // 그래서 기본 생성자가 없으면 프록시 객체를 생성하는 과정에서 오류가 발생할 수 있습니다.
//    @Around("execution(* kr..service.*.*(..)) || execution(* kr..mapper.*.*(..))")
//    //@Around를 이 패턴에 일치하는 것만 적용하라. : 컨트롤러,서비스,매퍼에만 적용하겠다는 의미 (pointcut)
//    //execution(반환타입 패키지명.클래스명.메서드명*(매개변수목록))
//    public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable { //@Around에서만 ProceedingJoinPoint를 사용함
//        //ProceedingJoinPoint 객체 : 모든 호출정보가 들어있음 - reflection으로 적용
//        //ProceedingJoinPoint안에 원래 호출하려 했던 target의 호출정보가 다 들어가있음
//        //joinPoint.getSignature.getName() : 메서드이름
//        //joinPoint.getArgs() : 매개변수로 넘겨진 값들 다 읽어올 수 있음
//        //joinPoint.proceed() : 메서드호출
//        String type = "";
//        String name = joinPoint.getSignature().getDeclaringTypeName(); //접점의 해당 패키지명을 가져옴
//        if  (name.indexOf("Service") > -1) { //false면 -1
//            type = "ServiceImpl  \t:  ";
//        } else if (name.indexOf("Mapper") > -1) {
//            type = "Mapper  \t\t:  ";
//        }
//        System.out.println(type + name + "." + joinPoint.getSignature().getName() + "()호출⭐");
//        //어느클래스에있는 어느 메서드인지에대한 정보가 로깅됨
//        Object object = joinPoint.proceed();//실제 target클래스의 메서드를 호출하는 부분 - 원래 타겟 호출하라
//        System.out.println(type + name + "." + joinPoint.getSignature().getName() + "()종료⭐");
//
//        return object;
//        //호출결과를 반환 : advice가 여러개 적용될 수 있기때문에 다음 advice에게 메서드호출결과를 넘겨주기위해
//        //단순히 advice가 한개만 적용되는 경우엔 void로 하고 반환형 없애면 된다.
//    }
//
//
//    @ExceptionHandler({IdNotFoundException.class})
//    public ModelAndView idNotFoundExceptionHandler(HttpServletRequest request, IdNotFoundException e) {
//        ModelAndView mv = new ModelAndView("/hr/loginform");
//        mv.addObject("errorCode", -2);
//        mv.addObject("errorMsg", e.getMessage());
//        System.out.println("#######################IdNotFoundException#################1");
//        Logger var10000 = log;
//        StringBuffer var10001 = request.getRequestURL();
//        var10000.error("Request: " + var10001 + "\n raised " + e);
//        return mv;
//    }
//
//
//    @ExceptionHandler({PwMissMatchException.class})
//    public ModelAndView pwMissMatchException(HttpServletRequest request, PwMissMatchException e) {
//        ModelAndView mv = new ModelAndView("/hr/loginform");
//        mv.addObject("errorCode", -4);
//        mv.addObject("errorMsg", e.getMessage());
//        System.out.println("#######################PwMissMatchException#################1");
//        Logger var10000 = log;
//        StringBuffer var10001 = request.getRequestURL();
//        var10000.error("Request: " + var10001 + "\n raised " + e);
//        return mv;
//    }
//
//
//
//    @ExceptionHandler({DataAccessException.class})
//    public ModelAndView pwNotFoundExceptionHandler(HttpServletRequest request, DataAccessException e) {
//        ModelAndView mv = new ModelAndView("/errorPage");
//        mv.addObject("errorCode", -3);
//        mv.addObject("errorMsg", e.getMessage());
//        System.out.println("#####################DataAccessException###################1");
//        Logger var10000 = log;
//        StringBuffer var10001 = request.getRequestURL();
//        var10000.error("Request: " + var10001 + "\n raised " + e);
//        return mv;
//    }
//
//    @ExceptionHandler({Exception.class})
//    public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
//        ModelAndView mv = new ModelAndView("/errorPage");
//        mv.addObject("exception", exception);
//        System.out.println("******************** 전체익셉션");
//        log.error("defaultExceptionHandler", exception);
//        return mv;
//    }
//
//    @AfterThrowing(pointcut = "execution(* kr..service.*.*(..))", throwing = "exception")
//    //대상메서드에서 예외가 발생했을 때 적용할 advice
//    public void afterThrowingAdvice(JoinPoint joinPoint, Exception exception){ //모든 예외에 대해 적용
//        System.out.println("❗❓After method execution, throwing exception : "+exception.getMessage());
//    }
//}
//

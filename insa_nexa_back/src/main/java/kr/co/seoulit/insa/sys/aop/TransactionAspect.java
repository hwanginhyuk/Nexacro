//package kr.co.seoulit.insa.sys.aop;
//
//import org.springframework.aop.Advisor;
//import org.springframework.aop.aspectj.AspectJExpressionPointcut;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.dao.DataAccessException;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
//import org.springframework.transaction.interceptor.RollbackRuleAttribute;
//import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
//import org.springframework.transaction.interceptor.TransactionInterceptor;
//
//import java.util.Collections;
//
//@Configuration
//public class TransactionAspect {
//    private static final String AOP_TRANSACTION_METHOD_NAME = "*";
//    private static final String AOP_TRANSACTION_EXPRESSION = "execution(* kr.co.seoulit..service.*.*(..) ) ";
//    //pointcut객체를 만들어서 넘긴다.
//    @Autowired
//    private PlatformTransactionManager transactionManager;
//
//    public TransactionAspect() {
//    }//기본 생성자가 없으면 프록시 객체를 생성하는 과정에서 오류가 발생할 수 있습니다.
//
//    //적용할 advice를 만듦
//    //advice를 구현하는 방법 -(1)✅클래스로 구현하는 방법  (2)애너테이션으로하는 방법
//    //tx시작, tx커밋, 롤백 등이 이 안에 다 구현되어있음
//    @Bean
//    public TransactionInterceptor transactionAdvice() { //컨테이너에 등록됨. 이름만 Interceptor이고 원래는 Advice 클래스임
//        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
//        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
//        transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
//        transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(DataAccessException.class)));
//        source.setTransactionAttribute(transactionAttribute);
//        return new TransactionInterceptor(this.transactionManager, source);
//    }
//
//    //Pointcut에 advice 적용
//    @Bean
//    public Advisor transactionAdviceAdvisor() { //==> Proxy객체를 만듦
//        //pointcut + advice = aspect (스프링에서는 Advisor를 리턴함)
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
//        return new DefaultPointcutAdvisor(pointcut, this.transactionAdvice()); //pointcut과 advice를 결합한것을 Advisor라는 객체로 리턴
//    }
//}
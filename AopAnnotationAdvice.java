package xyz.itwill06.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//Advice 클래스와 메소드에 AOP 관련 어노테이션을 사용하여 Spring AOP 기능 구현

@Component
//@Aspect : 핵심관심코드에 횡단관심코드를 삽입하여 실행하기 위한 기능을 제공하는 어노테이션
// => Spring Bean Configuration File의 aspect 엘리먼트와 유사한 기능 제공
// => Spring Bean Configuration File에 aspectj-autoproxy 엘리먼트 설정해야만 Spring AOP 기능 구현 가능
@Aspect
public class AopAnnotationAdvice {
	//@Pointcut : 타겟메소드를 지정하기 위한 어노테이션
	// => Spring Bean Configuration File의 pointcut 엘리먼트와 유사한 기능 제공
	// => 메소드를 호출하면 Pointcut 표현식을 제공받아 사용 가능
	//value 속성 : 타겟메소드를 지정할 수 있는 Pointcut 표현식을 속성값으로 설정
	// => value 속성외에 다른 속성이 없는 경우 속성값만 설정 가능
	@Pointcut("within(xyz.itwill06.aop.AopAnnotationBean)")
	public void aopPointcut() {}
	
	//@Before : 타겟메소드의 명령(핵심관심코드)이 실행되기 전에 삽입되어 실행될 횡단관심코드를
	//제공하기 위한 어노테이션
	// => Spring Bean Configuration File의 before 엘리먼트와 유사한 기능 제공
	//value 속성 : 타겟메소드를 지정할 수 있는 Pointcut 표현식을 속성값으로 설정
	// => value 속성외에 다른 속성이 없는 경우 속성값만 설정 가능
	// => @Pointcut 어노테이션을 사용한 메소드를 호출하여 Pointcut 표현식을 제공받아 타겟메소드 지정 
	//@Before(value = "within(xyz.itwill06.aop.AopAnnotationBean")
	//@Before("within(xyz.itwill06.aop.AopAnnotationBean)") 
	@Before("aopPointcut()") 
	public void beforeLog() {
		System.out.println("### [before]핵심관심코드 실행 전에 삽입되어 실행될 횡단관심코드 ###");
	}
	
	//@After : 타겟메소드의 명령(핵심관심코드)이 실행된 후에 무조건 삽입되어 실행될 횡단관심코드를
	//제공하기 위한 어노테이션
	// => Spring Bean Configuration File의 after 엘리먼트와 유사한 기능 제공
	@After("aopPointcut()")
	public void afterLog() {
		System.out.println("### [after]핵심관심코드가 실행된 후에 무조건 삽입되어 실행될 횡단관심코드 ###");
	}
	
	//@AfterReturning : 타겟메소드의 명령(핵심관심코드)이 정상적으로 실행된 후에 삽입되어 실행될 
	//횡단관심코드를 제공하기 위한 어노테이션
	// => Spring Bean Configuration File의 after-returning 엘리먼트와 유사한 기능 제공
	@AfterReturning("aopPointcut()")
	public void afterReturningLog() {
		System.out.println("### [after-returning]핵심관심코드가 정상적으로 실행된 후에 삽입되어 실행될 횡단관심코드 ###");
	}
		
	//@AfterThrowing : 타겟메소드의 명령(핵심관심코드)이 실행시 예외가 발생된 후에 삽입되어 실행될 
	//횡단관심코드를 제공하기 위한 어노테이션
	// => Spring Bean Configuration File의 after-throwing 엘리먼트와 유사한 기능 제공
	@AfterThrowing("aopPointcut()")
	public void afterThrowingLog() {
		System.out.println("### [after-throwing]핵심관심코드가 실행시 예외가 발생된 후에 삽입되어 실행될 횡단관심코드 ###");
	}	
	
	//@Around : 타겟메소드의 명령(핵심관심코드)이 실행 전과 후에 삽입되어 실행될 횡단관심코드를
	//제공하기 위한 어노테이션
	// => Spring Bean Configuration File의 around 엘리먼트와 유사한 기능 제공
	@Around("aopPointcut()")
	public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("### [around]핵심관심코드 실행 전에 삽입되어 실행될 횡단관심코드 ###");
		Object object=joinPoint.proceed();
		System.out.println("### [around]핵심관심코드가 실행된 후에 삽입되어 실행될 횡단관심코드 ###");
		return object;
	}
}











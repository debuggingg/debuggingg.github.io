package xyz.itwill06.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class ExecutionTimeAdvice {
	/*
	//타겟메소드의 명령이 실행되는 시간을 측정하여 기록하기 위한 메소드 - Around Advice Method
	public Object timeWatchLog(ProceedingJoinPoint joinPoint) throws Throwable {
		//타겟메소드의 핵심관심코드 실행 전에 삽입되어 실행될 명령 작성
		long startTime=System.currentTimeMillis();

		//타겟메소드의 핵심관심코드를 실행해 결과값을 반환받아 저장
		Object object=joinPoint.proceed();
		
		//타겟메소드의 핵심관심코드 실행 후에 삽입되어 실행될 명령 작성
		long endTime=System.currentTimeMillis();
		
		String className=joinPoint.getTarget().getClass().getSimpleName();
		String methodName=joinPoint.getSignature().getName();
		System.out.println(className+" 클래스의 "+methodName+"() 메소드 실행시간 = "
				+(endTime-startTime)+"ms");
		
		return object;
	}
	*/
	
	public Object timeWatchLog(ProceedingJoinPoint joinPoint) throws Throwable {
		//StopWatch 클래스 : 시간을 측정하기 위한 기능을 제공하는 클래스
		StopWatch stopWatch=new StopWatch();
		
		//StopWatch.start() : 시간 측정을 시작하는 메소드
		stopWatch.start();
		
		Object object=joinPoint.proceed();
		
		//StopWatch.stop() : 시간 측정을 종료하는 메소드
		stopWatch.stop();
		
		String className=joinPoint.getTarget().getClass().getSimpleName();
		String methodName=joinPoint.getSignature().getName();
		//StopWatch.getTotalTimeMillis() : 측정된 시간을 ms 단위로 반환하는 메소드
		System.out.println(className+" 클래스의 "+methodName+"() 메소드 실행시간 = "
				+stopWatch.getTotalTimeMillis()+"ms");
		
		return object;
	}
}
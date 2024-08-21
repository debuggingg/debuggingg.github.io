package xyz.itwill04.bean;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;

//스프링 컨테이너에 의해 관리될 클래스(Spring Bean)는 다양한 인터페이스를 상속받아 추상메소드를
//오버라이드 선언 가능
// => 오버라이드 선언된 메소드는 스프링 컨테이너에 의해 객체로 생성된 후 또는 소멸 전 자동 
//호출되어 초기화 및 마무리 작업 가능
public class InitDestroyMethodBean implements BeanNameAware, BeanClassLoaderAware {
	public InitDestroyMethodBean() {
		System.out.println("### InitDestroyMethodBean 클래스의 기본 생성자 호출 ###");
	}

	public void init() {
		System.out.println("*** InitDestroyMethodBean 클래스의 init() 메소드 호출 ***");
	}

	public void destroy() {
		System.out.println("*** InitDestroyMethodBean 클래스의 destroy() 메소드 호출 ***");
	}

	public void display() {
		System.out.println("*** InitDestroyMethodBean 클래스의 display() 메소드 호출 ***");
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("*** InitDestroyMethodBean 클래스의 setBeanName() 메소드 호출 ***");
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("*** InitDestroyMethodBean 클래스의 setBeanClassLoader() 메소드 호출 ***");
	}
}
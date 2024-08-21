package xyz.itwill04.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanAttributeApp {
	public static void main(String[] args) {
		System.out.println("=============== Spring Container 초기화 전 ===============");
		ApplicationContext context=new ClassPathXmlApplicationContext("04-2_beanAttribute.xml");
		System.out.println("=============== Spring Container 초기화 후 ===============");
		//ApplicationContext.getBean(String beanName) : 매개변수로 식별자(beanName)를 전달받아 
		//스프링 컨테이너로부터 객체(Spring Bean)를 제공받아 반환하는 메소드
		// => Object 타입의 객체를 반환하므로 반드시 명시적 객체 형변환 사용
		//InitDestroyMethodBean bean=(InitDestroyMethodBean)context.getBean("initDestroyMethodBean");

		//ApplicationContext.getBean(String beanName, Class<T> class) : 매개변수로 식별자(beanName)를 
		//전달받아 스프링 컨테이너로부터 객체(Spring Bean)를 제공받아 객체 형변환하여 반환하는 메소드
		InitDestroyMethodBean bean=context.getBean("initDestroyMethodBean", InitDestroyMethodBean.class);

		//bean 엘리먼트의 init-method 속성을 사용해 객체가 생성된 후에 메소드가 자동 호출되어
		//초기화 처리하는 명령이 실행되도록 설정 가능 
		//bean.init();//객체의 초기화 처리를 위해 호출한 메소드

		bean.display();

		//bean 엘리먼트의 destroy-method 속성을 사용해 객체가 소멸되기 전에 메소드가 자동 호출되어
		//마무리 처리하는 명령이 실행되도록 설정 가능
		//bean.destroy();//객체의 마무리 처리를 위해 호출한 메소드
		System.out.println("=========================================================");
		context.getBean("lazyInitBean", LazyInitBean.class);
		System.out.println("=========================================================");
		ScopeBean bean1=context.getBean("singletonBean", ScopeBean.class);
		ScopeBean bean2=context.getBean("singletonBean", ScopeBean.class);
		ScopeBean bean3=context.getBean("singletonBean", ScopeBean.class);

		System.out.println("bean1 = "+bean1);
		System.out.println("bean2 = "+bean2);
		System.out.println("bean3 = "+bean3);
		System.out.println("=========================================================");
		ScopeBean bean4=context.getBean("prototypeBean", ScopeBean.class);
		ScopeBean bean5=context.getBean("prototypeBean", ScopeBean.class);
		ScopeBean bean6=context.getBean("prototypeBean", ScopeBean.class);

		System.out.println("bean4 = "+bean4);
		System.out.println("bean5 = "+bean5);
		System.out.println("bean6 = "+bean6);

		((ClassPathXmlApplicationContext)context).close();
	}
}
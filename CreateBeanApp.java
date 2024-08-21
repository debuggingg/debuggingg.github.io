package xyz.itwill04.bean;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

@SuppressWarnings("deprecation")
public class CreateBeanApp {
	public static void main(String[] args) {
		System.out.println("1.BeanFactory 객체를 생성하여 스프링 컨테이너로 사용하는 방법");
		System.out.println("=============== Spring Container 초기화 전 ===============");
		//BeanFactory 인터페이스를 상속받은 자식클래스로 객체 생성 - BeanFactory 객체
		// => BeanFactory 객체를 생성할 때 환경설정파일(Spring Bean Configuration File)을 제공받아
		//BeanFactory 객체 초기화 처리
		// => BeanFactory 객체는 환경설정파일에 등록된 클래스로 미리 객체를 생성하지 않고 
		//객체 제공을 요청할 경우에 객체를 생성하여 제공
		BeanFactory factory=new XmlBeanFactory(new FileSystemResource("src/main/resources/04-1_beanCreate.xml"));
		System.out.println("=============== Spring Container 초기화 후 ===============");
		//BeanFactory.getBean(String beanName) : 매개변수로 Spring Bean를 구분하기 위한 식별자
		//(beanName)을 전달받아 스프링 컨테이너로부터 객체(Spring Bean)를 생성하여 반환하는 메소드
		// => Object 타입의 객체를 반환하므로 반드시 명시적 객체 형변환 사용
		// => 매개변수로 전달받은 식별자(beanName)가 없는 경우 NoSuchBeanDefinitionException 발생 
		CreateBean bean1=(CreateBean)factory.getBean("createBean");
		bean1.display();
		System.out.println("=========================================================");
		System.out.println("2.ApplicationContext 객체를 생성하여 스프링 컨테이너로 사용하는 방법");
		System.out.println("=============== Spring Container 초기화 전 ===============");
		//ApplicationContext 인터페이스를 상속받은 자식클래스로 객체 생성 - ApplicationContext 객체
		// => ApplicationContext 객체를 생성할 때 환경설정파일(Spring Bean Configuration File)을 제공받아
		//ApplicationContext 객체 초기화 처리
		// => ApplicationContext 객체는 환경설정파일에 등록된 클래스로 미리 객체 생성
		ApplicationContext context=new ClassPathXmlApplicationContext("04-1_beanCreate.xml");
		System.out.println("=============== Spring Container 초기화 후 ===============");
		//ApplicationContext.getBean(String beanName) : 매개변수로 Spring Bean를 구분하기 위한 식별자
		//(beanName)을 전달받아 스프링 컨테이너로부터 객체(Spring Bean)를 제공받아 반환하는 메소드
		// => Object 타입의 객체를 반환하므로 반드시 명시적 객체 형변환 사용
		// => 매개변수로 전달받은 식별자(beanName)가 없는 경우 NoSuchBeanDefinitionException 발생
		//DL(Denpendency Lookup) : 스프링 컨테이너가 관리하는 객체(Spring Bean)을 검색하여 제공하는 기능
		CreateBean bean2=(CreateBean)context.getBean("createBean");
		bean2.display();
		System.out.println("=========================================================");
		((ClassPathXmlApplicationContext)context).close();
	}
}
package xyz.itwill04.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration : 스프링 컨테이너에 의해 관리될 객체를 생성하여 제공하는 기능의 클래스로
//등록하기 위한 어노테이션 
// => Spring Bean Configuration File과 유사한 기능을 제공하는 어노테이션 
@Configuration
public class AnnotationConfigurationBean {
	//@Bean : 스프링 컨테이너에 의해 관리될 객체를 생성하여 반환하는 메소드를 등록하기 위한 어노테이션
	// => Spring Bean Configuration File의 bean 엘리먼트와 유사한 기능을 제공하는 어노테이션
	// => 메소드의 이름을 식별자(beanName)으로 사용
	// => @Bean 어노테이션의 name 속성을 사용하면 식별자(beanName) 변경 가능
	@Bean
	public AnnotationBean annotationBean() {
		return new AnnotationBean();
	}

}
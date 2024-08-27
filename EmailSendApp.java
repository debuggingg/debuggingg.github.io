package xyz.itwill06.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmailSendApp {
	public static void main(String[] args) throws Exception {
		ApplicationContext context=new ClassPathXmlApplicationContext("06-4_email.xml");
		EmailSendBean bean=context.getBean("emailSendBean", EmailSendBean.class);
		System.out.println("=============================================================");
		bean.sendEmail("beom.kb@gmail.com","mailSendTest","<h1>Java Mail program </h1>");
		System.out.println("=============================================================");
		
		System.out.println("=============================================================");
		((ClassPathXmlApplicationContext)context).close();	
	}
}

package xyz.itwill06.aop;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.mail.javamail.JavaMailSender;

import lombok.Setter;

//JavaMail 기능을 구현하기 위해 spring-context-support 라이브러리와 javax.mail 라이브러리를
//프로젝트에 빌드되도록 처리 - 메이븐 사용 : pom.xml

//메일 전송 기능(JavaMail)을 제공하기 위한 클래스 - 메일 서버를 사용해 이메일 전송
// => 메일 서버(Mail Server) : 메일을 송수신하는 서비스를 제공하는 컴퓨터 
// => 메일 서버는 SMTP 서비스(Simple Message Transfer Protocol - 25)를 이용해 메일을 전송하고
//POP3(Post Office Protocol3 - 110) 서비스 또는 IMAP(Internet Message Access Protocol - 143)
//서비스를 이용해 메일을 수신하여 사용자에게 전달
public class EmailSendBean {
	//JavaMailSender 객체를 저장하기 위한 필드
	// => JavaMailSender 객체 : SMTP 서비스를 제공하는 서버의 정보를 저장하기 위한 객체
	// => Spring Bean Configuration File에서 JavaMailSender 객체를 제공받아 필드에 저장 - DI
	@Setter
	private JavaMailSender javaMailSender;
	
	//SMTP 서비스(JavaMailSender 객체)를 사용하여 메일을 전송하는 메소드 - 핵심관심코드
	// => 메일을 받는 사람의 이메일 주소를 반환
	// => 메일을 받는 사람의 이메일 주소, 메일 제목, 메일 내용을 매개변수로 전달받아 메소드에서 사용 
	public String sendEmail(String email, String subject, String content) throws Exception {
		//JavaMailSender.createMimeMessage() : JavaMailSender 객체로 MimeMessage 객체를 생성하여
		//반환하는 메소드
		//MimeMessage 객체 : 메일 전송 관련 정보를 저장하기 위한 객체
		MimeMessage message=javaMailSender.createMimeMessage();	
		
		//MimeMessage.setSubject(String subject) : MimeMessage 객체의 메일 제목을 변경하는 메소드
		message.setSubject(subject);
		
		//MimeMessage.setText(String content) : MimeMessage 객체의 메일 내용을 변경하는 메소드
		// => 메일 내용을 일반 텍스트 파일의 문서 형태로 저장하여 전달
		//message.setText(content);
		
		//MimeMessage.setContent(Object o, String type) : MimeMessage 객체의 메일 내용을 변경하는 메소드
		// => type 매개변수에 메일 내용을 전달하기 위한 문서의 형태(MimeType)를 전달하여 저장
		message.setContent(content, "text/html; charset=utf-8");//HTML 태그를 사용한 메일 내용 전송
	
		//MimeMessage.setRecipient(RecipientType type, Address address) : MimeMessage 객체의
		//메일을 받는 사람의 이메일 주소 관련 정보를 변경하는 메소드
		// => type : 메일을 받는 사용자를 구분하기 위한 상수필드(RecipientType)를 전달
		// => address : 이메일 주소가 저장된 Address 객체 전달
		////MimeMessage.setRecipients(RecipientType type, Address[] address) : 다수의 사용자에게
		//메일을 전송하기 위한 사용하는 메소드
		message.setRecipient(RecipientType.TO, new InternetAddress(email));
		
		//JavaMailSender.send(MimeMessage message) : 매개변수로 전달받은 MimeMessage 객체의
		//정보를 사용해 JavaMailSender 객체(SMTP)로 메일을 전송하는 메소드
		javaMailSender.send(message);
		
		return email;
	}
}
package mx.com.teclo.geolocalizacionalgwsw;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan( basePackages = "mx.com")
@EntityScan(basePackages = "mx.com")
public class MsAlgWswApplication{

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/sspcdmxalg_v100r5_qa_wsw");
		SpringApplication.run(MsAlgWswApplication.class, args);
	}
	
	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory() {
		return new HibernateJpaSessionFactoryBean();
	}
	@Bean
	public MailSender mailSender() {
		return new JavaMailSenderImpl();
	}
	@Bean
    public PasswordEncoder passwordEncoder() {
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
				 e.printStackTrace();
		}
        PasswordEncoder encoder = new BCryptPasswordEncoder(16, random);
        return encoder;
    }
}

package co.simplon.soninkrala;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SoninKralaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoninKralaApplication.class, args);
		System.out.println("SoninKralaApplication started");
	}

}

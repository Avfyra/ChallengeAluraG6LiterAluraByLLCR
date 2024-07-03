package com.aluradesafios.literalurachallenge;

import com.aluradesafios.literalurachallenge.principal.Principal;
import com.aluradesafios.literalurachallenge.repository.IAutoresRepository;
import com.aluradesafios.literalurachallenge.repository.ILibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraChallengeApplication implements CommandLineRunner {

	@Autowired
	private ILibrosRepository librosRepository;
	@Autowired
	private IAutoresRepository autoresRepository;
	public static void main(String[] args) {
		SpringApplication.run(LiterAluraChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autoresRepository, librosRepository);
		principal.muestraElMenu();
	}
}

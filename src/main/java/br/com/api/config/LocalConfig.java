package br.com.api.config;

import br.com.api.domain.User;
import br.com.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void startDB() {
        User u1 = new User(null, "Mateus", "mateus@email.com", "12345678");
        User u2 = new User(null, "Marcel", "marcel@email.com", "12345678");

        userRepository.saveAll(List.of(u1, u2));
    }
}

package com.library;

 import com.library.user.User;
import com.library.user.UserRepository;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.autoconfigure.domain.EntityScan;
 import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
 import org.springframework.boot.test.context.SpringBootTest;
 import org.springframework.context.annotation.ComponentScan;
 import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
 import org.springframework.stereotype.Service;
 import org.springframework.test.annotation.Rollback;
import org.assertj.core.api.Assertions;
//@ComponentScan({"com.library*"})
//@EntityScan("com.library*")
//@EnableJpaRepositories("com.library*")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)

public class UserRepositoryTests {
    @Autowired private UserRepository repo;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("123@123.com");
        user.setPassword("R3pl1c4nT");
        user.setFirstName("Syed");
        user.setLastName("Anuar");

        User savedUser = repo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testFindNew() {

    }
}

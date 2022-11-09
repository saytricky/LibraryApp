package com.library;

import com.library.book.Book;
import com.library.book.BookRepository;
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

public class BookRepositoryTests {
    @Autowired private BookRepository repo;

    @Test
    public void testAddNew() {
        Book book = new Book();
        book.setIsbn("123456789");
        book.setTitle("Book of Wonders");
        book.setGenre("Fiction");

        Book savedUser = repo.save(book);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testFindNew() {

    }
}

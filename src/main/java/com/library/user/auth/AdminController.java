/**
 * Controller class for Admin
 */
package com.library.user.auth;

import com.library.book.Book;
import com.library.book.BookNotFoundException;
import com.library.book.BookService;
import com.library.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// Annotate as controller
@Controller
public class AdminController {

    // Inject instance of UserService
    @Autowired
    private UserService userService;

    // Inject instance of BookService
    @Autowired
    private BookService bookService;

    // Map HTTP GET requests for '/admin/books'
    @GetMapping("/admin/books")
    public String adminBooks(Model model) {
        // Use listAll() from BookService service class...
        // to get a List of books
        List<Book> listBooks = bookService.listAll();
        // Store List of books in listBooks attribute, so we can populate it in Thymeleaf
        model.addAttribute("listBooks", listBooks);
        // Get size of List of books, so that we know how many entries in the entity
        model.addAttribute("totalItems", listBooks.size());

        // Return templates/admin/books.html
        return "admin/books";
    }

    // Map HTTP POST requests for "/admin/books/add"
    @PostMapping("/admin/books/save")
    // This url accepts POST requests to "save" the book
    public String saveBook(Book book) {
        // Saves the POSTed book entity using instance of BookService
        bookService.save(book);

        // Return a redirect to "/admin/books?add"
        return "redirect:/admin/books?add";
    }

    // Map HTTP GET requests for '/admin/books/add'
    @GetMapping("/admin/books/add")
    public String adminAddBook(Model model) {
        // Return template file with add book form, that's it.
        // Return templates/admin/addBook.html
        return "admin/addBook";
    }

    // Map HTTP GET requests for '/admin/books/edit/{id}'
    @GetMapping("/admin/books/edit/{id}")
    public String adminEditBook(@PathVariable("id") Integer id, Model model) {
        // Wrap in try-catch block, because we might not find a book for the id
        try {
            // Get the book by its id
            Book book = bookService.get(id);
            // Store the book entity in attribute to be used in Thymeleaf
            model.addAttribute("book", book);
            // Customise a page title, so that we can dynamically display...
            // book ID in Thymeleaf
            model.addAttribute("pageTitle", "Edit Book (ID: " + id + ")");

            // Return templates/admin/edit_book.html
            return "admin/edit_book";
            // Book not found
        } catch (BookNotFoundException e) {
            // Return redirect to list of books with parameter to show error
            return "redirect:/admin/books?notExist";
        }
    }
    // Map HTTP GET requests for '/admin/books/delete/{id}'
    @GetMapping("/admin/books/delete/{id}")
    public String adminDeleteBook(@PathVariable("id") Integer id, Model model) {
        // Wrap in try-catch block, because we might not find a book for the id
        try {
            // Delete the book by its id
            bookService.delete(id);

            // Success, redirect to list of books with a param for message
            return "redirect:/admin/books?deleted={id}";
            // Book not found
        } catch (BookNotFoundException e) {
            // Redirect to list of books with a param for message
            return "redirect:/admin/books?notExist";
        }
    }

    // Map HTTP GET requests for '/admin/books/search'
    @GetMapping("/admin/books/search")
    // User will request page with keyword parameter, so we accept keyword as parameter
    public String search(String keyword, Model model) {
        // Call searchByPage with search keyword, model container, and result page 1
        // We assume if user arrives to this url, they are on result page 1
        return searchByPage(keyword, model, 1);
    }

    // Map HTTP GET requests for '/admin/books/search/page/{pageNum}'
    @GetMapping("/admin/books/search/page/{pageNum}")
    public String searchByPage(String keyword, Model model,
                               @PathVariable(name = "pageNum") int pageNum) {

        // Get current page search results
        Page<Book> result = bookService.search(keyword, pageNum);

        // Store contents of current page results in List
        List<Book> listResult = result.getContent();

        // Add attributes to model container so that we can use it in Thymeleaf
        // Get pages total amount of search results
        model.addAttribute("totalPages", result.getTotalPages());
        // Get total amount of results from search
        model.addAttribute("totalItems", result.getTotalElements());
        // Get the current page number
        model.addAttribute("currentPage", pageNum);

        // Start counter to ensure only specified amount of results are displayed per page
        long startCount = (pageNum - 1) * BookService.SEARCH_RESULT_PER_PAGE + 1;
        // Get current start counter value
        model.addAttribute("startCount", startCount);

        // Determine when to stop displaying results.
        long endCount = startCount + BookService.SEARCH_RESULT_PER_PAGE - 1;
        // We are probably on last page of search, but...
        // endCount cannot exceed total amount of results from search
        if (endCount > result.getTotalElements()) {
            // set counter to end at total amount of results
            endCount = result.getTotalElements();
        }

        // Store as attribute to be used in Thymeleaf
        // Get endCount value as attribute
        model.addAttribute("endCount", endCount);
        // Get List of search results for current page
        model.addAttribute("listResult", listResult);
        // Get keyword used for search
        model.addAttribute("keyword", keyword);

        // Return templates/admin/searchBook.html
        return "admin/searchBook";
    }


}

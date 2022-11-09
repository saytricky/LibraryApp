/**
 * Controller class for 'Book'
 */
package com.library.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// Specify as Controller class so that it gets detected in scanning
@Controller
public class BookController {

    // Inject an instance of BookService as bookService
    @Autowired
    BookService bookService;

    // Map HTTP GET requests for '/books'
    @GetMapping("/books")
    public String searchForm() {
        // Return templates/books.html to user, nothing special
        return "books";
    }

    // Map HTTP GET requests for '/books/search'
    @GetMapping("/books/search")
    // User will request page with keyword parameter, so we accept keyword as parameter
    public String search(String keyword, Model model) {
        // Call searchByPage with search keyword, model container, and result page 1
        // We assume if user arrives to this url, they are on result page 1
        return searchByPage(keyword, model, 1);
    }

    // Map HTTP GET requests for '/books/search/page/{pageNum}'
    @GetMapping("/books/search/page/{pageNum}")
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

        // Return templates/search.html
        return "search";
    }

}

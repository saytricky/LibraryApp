/**
 * Entity 'Book' class.
 * Columns - Id, title, isbn, genre
 */
package com.library.book;

import javax.persistence.*;

// Annotate POJO as entity to represent a database entity/object.
@Entity()
// Specify primary table for entity
@Table(name = "books")

public class Book {
    // Specify id as primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
    * @Column annotation
    * nullable = specify whether column can be empty/null
    * unique = specify if column content must be unique
    * length = specify column's amount of characters
    */

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 40, nullable = false)
    private String genre;

    /*
    * Column Id
     */
    // Getter for id
    public Integer getId() {
        return id;
    }

    // Setter for id
    public void setId(Integer id) {
        this.id = id;
    }

    /*
     * Column isbn
     */
    // Getter for isbn
    public String getIsbn() {
        return isbn;
    }

    // Setter for isbn
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /*
     * Column title
     */
    // Getter for title
    public String getTitle() {
        return title;
    }
    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    /*
     * Column genre
     */
    // Getter for genre
    public String getGenre() {
        return genre;
    }
    // Setter for genre
    public void setGenre(String genre) {
        this.genre = genre;
    }
}

package com.larysoft.library.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity(name = "books")
//used to solve sterilization issues
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//used to auto generate table primary key
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_name", length = 150, unique = true)
    private String bookName;

    @Column(name = "author", length = 70)
    private String author;

    @Column(name = "category", length = 70)
    private String category;

    @Column(name = "pages")
    private Long pages;

    @Column(name = "release_year", length = 150)
    private String releaseYear;

    public Books() {
    }

    public Books(Long bookId) {
        this.bookId = bookId;
    }

    public Books(String bookName, String author, String category, Long pages, String releaseYear) {
        this.bookName = bookName;
        this.author = author;
        this.category = category;
        this.pages = pages;
        this.releaseYear = releaseYear;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }
}

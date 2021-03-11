package com.larysoft.library.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity(name = "lend")
//used to solve sterilization issues
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lend {

    @Id//used to specify the Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//used to auto generate table primary key
    @Column(name = "lend_id")
    private Long lendId;

    @ManyToOne(targetEntity = Books.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Books books;

    @ManyToOne(targetEntity = Register.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Register register;

    public Lend(Books books, Register register) {
        this.books = books;
        this.register = register;
    }

    public Long getLendId() {
        return lendId;
    }

    public void setLendId(Long lendId) {
        this.lendId = lendId;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

}

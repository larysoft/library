package com.larysoft.library.controllers;

import com.larysoft.library.models.Books;
import com.larysoft.library.repository.BookRepository;
import com.larysoft.library.service.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    final
    ResponseHandler responseHandler;

    final
    BookRepository bookRepository;

    public BookController(ResponseHandler responseHandler, BookRepository bookRepository) {
        this.responseHandler = responseHandler;
        this.bookRepository = bookRepository;
    }

    @PostMapping
    /** method used to add book into the library*/
    public ResponseEntity<?> addBook(@RequestBody Books book){

        try{

            Books books = bookRepository.saveAndFlush(book);

            return responseHandler.generateResponse(HttpStatus.OK, true, "Book saved successfully", books);

        }catch (Exception e){
            String[] rules = {"Book Name must be unique"};
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Unable to save book.", rules);
        }

    }

    @GetMapping
    /** method used to get all books */
    public ResponseEntity<?> allBooks(){
        try{
            //getting all books
            List<Books> books = bookRepository.findAll();

            //book response
            return responseHandler.generateResponse(HttpStatus.OK, true, "All books.", books);

        }catch (Exception e){
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Unable to get all books", null);
        }
    }

    @GetMapping("{bookId}")/** used to get a single book */
    public ResponseEntity<?> singleBook(@PathVariable("bookId")Long bookId){
        try{
            //getting single books by id
            Books books = bookRepository.getOne(bookId);

            //book response
            return responseHandler.generateResponse(HttpStatus.OK, true, "Success in getting book details", books);

        }catch (Exception e){
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Unable to get single book.", null);
        }
    }

    @DeleteMapping("{bookId}")/** for deleting a single book */
    public ResponseEntity<?> deleteSingleBook(@PathVariable("bookId")Long bookId){
        try{
            if(bookRepository.deleteBook(bookId) > 0){
                return responseHandler.generateResponse(HttpStatus.OK, true, "Book Deleted successfully.", null);
            }else{
                return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Unable to delete book with id " + bookId, null);
            }
        }catch (Exception e){
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "BookId doesn't exist.", null);
        }
    }

    @PutMapping("{bookId}")
    /** method used to update book */
    public ResponseEntity<?> updateBook(@PathVariable("bookId") Long bookId, @RequestBody Books book){
        try{

            ResponseEntity<?> bookFind = bookRepository.findById(bookId)
                    .map(bk -> {
                        bk.setAuthor(book.getAuthor());
                        bk.setBookName(book.getBookName());
                        bk.setCategory(book.getCategory());
                        bk.setReleaseYear(book.getReleaseYear());
                        bk.setPages(book.getPages());

                        Books response = bookRepository.saveAndFlush(bk);

                        //building custom success response
                        return responseHandler.generateResponse(HttpStatus.OK, true, "Book updated successfully.", response);
                    }).orElseGet(() -> {

                        book.setBookId(bookId);

                        Books response = bookRepository.saveAndFlush(book);

                        return responseHandler.generateResponse(HttpStatus.OK, true, "Book updated successfully.", response);
                    });

            return bookFind;

        }catch (Exception e){
            return responseHandler.generateResponse(HttpStatus.FORBIDDEN, false, "Book with bookId " + bookId +" doesn't exist.", null);
        }
    }
}

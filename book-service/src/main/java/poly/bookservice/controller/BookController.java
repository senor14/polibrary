package poly.bookservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import poly.bookservice.dto.BookDto;
import poly.bookservice.jpa.BookEntity;
import poly.bookservice.jpa.BookRepository;
import poly.bookservice.service.BookService;
import poly.bookservice.vo.RequestBook;
import poly.bookservice.vo.ResponseBook;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/book-service")
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @GetMapping("/hello")
    public String Hello() {
        return "Hello";
    }

//    @GetMapping("/books")
//    public String create() {
//        return "/book/list";
//    }

    @PostMapping("/books")
    public ResponseEntity<ResponseBook> createPost(@RequestBody RequestBook requestBook) {

        BookEntity bookEntity = modelMapper.map(requestBook, BookEntity.class);
        log.info("requestBook: {}",requestBook);

        BookEntity bookResult = bookRepository.save(bookEntity);
        log.info("bookResult: {}",bookResult);
        ResponseBook result = modelMapper.map(bookResult, ResponseBook.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

//

    // 책 상세
    @GetMapping("/books/{bookId}")
    public ResponseEntity<ResponseBook> bookDetail(@PathVariable Long bookId) {

        BookEntity bookEntity = bookRepository.findByBookId(bookId);

        ResponseBook result = modelMapper.map(bookEntity, ResponseBook.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
////
////    @PostMapping("/books/update")
////    public ResponseEntity<Boolean> updatePost(@RequestParam Map<String, Object> map) {
////        ModelAndView modelAndView = new ModelAndView();
////
////        log.info("map: {}", map);
////        boolean isUpdateSuccess = this.bookService.edit(map);
////        log.info("isUpdateSuccess: ",isUpdateSuccess);
////        if (isUpdateSuccess) {
////            String bookId = map.get("bookId").toString();
////            log.info("bookId: {}", bookId);
////            modelAndView.setViewName("redirect:/detail?bookId=" + bookId);
////        } else {
////            modelAndView = this.update(map);
////        }
////
////        return ResponseEntity.status(HttpStatus.OK).body(isUpdateSuccess);
////    }
//
////    @PostMapping("/books/delete")
////    public ModelAndView deletePost(@RequestParam Map<String, Object> map) {
////        ModelAndView modelAndView = new ModelAndView();
////
////        boolean isDeleteSuccess = this.bookService.remove(map);
////        if (isDeleteSuccess) {
////            modelAndView.setViewName("redirect:/list");
////        } else {
////            String bookId = map.get("bookId").toString();
////            modelAndView.setViewName("redirect:/detail?bookId=" + bookId);
////        }
////
//
////        return modelAndView;
////    }
//
    @GetMapping("/books/list")
//    public ResponseEntity<List<Map<String, Object>>> list(@RequestParam Map<String, Object> map) {
    public ResponseEntity<List<ResponseBook>> bookSearch(@RequestParam String keyword) {

        List<BookEntity> bookEntities;
        if (keyword == null | keyword == "" ) {
            bookEntities = bookRepository.findAll();
        } else {
            bookEntities = bookRepository.findAllSearch(keyword);
        }

        List<ResponseBook> result = new ArrayList<>();

        bookEntities.forEach((v -> {
            result.add(modelMapper.map(v, ResponseBook.class));
        }));

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }


    // 신간 리스트 100개
    @GetMapping("/books/newBooks")
    public ResponseEntity<List<ResponseBook>> newBookList() {

        ModelMapper mapper = new ModelMapper();
//        List<BookDto> newBook100 = bookService.findNewBook100();
        List<BookEntity> bookList = bookRepository.findTop100ByOrderByBookIdDesc();
        log.info("bookList.size = {}", bookList.size());
        List<ResponseBook> resultList = new ArrayList<>();

        int i = 0;
        while (i < 3) {
            resultList.add(mapper.map(bookList.get((int) Math.round(Math.random() * 100)), ResponseBook.class));
            i++;
        }

        return ResponseEntity.status(HttpStatus.OK).body(resultList);

    }
}

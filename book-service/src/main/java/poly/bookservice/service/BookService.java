package poly.bookservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import poly.bookservice.dto.BookDto;
import poly.bookservice.jpa.BookEntity;
import poly.bookservice.jpa.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDto> findNewBook100() {

        ModelMapper mapper = new ModelMapper();

        List<BookEntity> newBookList = bookRepository.findNewBook100();
        List<BookDto> result  = new ArrayList<>();
        newBookList.forEach( v -> {
            result.add(new ModelMapper().map(v, BookDto.class));
        });

        return result;
    }
}

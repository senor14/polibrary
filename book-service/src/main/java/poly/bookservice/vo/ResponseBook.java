package poly.bookservice.vo;

import lombok.Data;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Data
public class ResponseBook {

    private Long bookId;
    private String isbn;
    private String title;
    private String author;
    private String pubInfo;
    private String pubYear;
    private String itemSeq;
    private String thumbnail;
    private String rentalYn;


}

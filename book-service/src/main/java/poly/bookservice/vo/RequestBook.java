package poly.bookservice.vo;

import lombok.Data;

@Data
public class RequestBook {

    private String isbn;
    private String title;
    private String author;
    private String pubInfo;
    private String pubYear;
    private String itemSeq;
    private String thumbnail;
}

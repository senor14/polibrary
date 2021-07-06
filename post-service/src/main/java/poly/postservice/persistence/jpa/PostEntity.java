package poly.postservice.persistence.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_info")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String userUuid;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column
    private String thumbnail;

    @Column
    private Integer status;

    @Column(nullable = false, length = 1, columnDefinition = "varchar(1) default 1")
    private String dispYn;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private String regDt;

    @PrePersist
    public void prePersist() {
        this.status = this.status == null ? 0 : this.status;
        this.dispYn = this.dispYn == null ? "Y" : this.dispYn;
    }

}

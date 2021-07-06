package poly.userservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;



@Data
@Entity
@Table(name = "USER_INFO", uniqueConstraints = {@UniqueConstraint(columnNames= {"userUuid", "email", "studentId", "password"})})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String userUuid;

    @Column(nullable = false, length = 64, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String studentId;

    @Column(nullable = false, unique = true, name = "password")
    private String encryptedPwd;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer point;

    @Column(nullable = false, length = 1, columnDefinition = "integer default 1")
    private Integer authLv;

    @Column(nullable = false, length = 1, columnDefinition = "varchar(1) default 'Y'")
    private String useYn;

    @Column(nullable = false)
    private String regId;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime regDt;

    @Column(nullable = false, length = 50)
    private String chgId;

    @Column(nullable = false)
    private LocalDateTime chgDt;

    @PrePersist
    public void prePersist() {
        this.point = this.point == null ? 0 : this.point;
        this.authLv = this.authLv == null ? 1 : this.authLv;
        this.useYn = this.useYn == null ? "Y" : this.useYn;
        this.regDt = this.regDt == null ? LocalDateTime.now() : this.regDt;
        this.chgDt = this.chgDt == null ? LocalDateTime.now() : this.chgDt;
    }

}

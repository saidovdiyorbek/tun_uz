package dasturlash.topnews.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Entity
@Table(name = "attach")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attach {
    @Id
    String id;

    @Column(nullable = false, name = "origin_name")
    String original_name;

    @Column(nullable = false)
    String path;

    @Column(nullable = false)
    Long size;

    @Column(nullable = false)
    String extension;

    @Column(nullable = false)
    boolean visible;

    @Column(nullable = false, name = "created_at")
    LocalDateTime created_at;

    @Column(nullable = false)
    LocalDateTime createdDate;
}

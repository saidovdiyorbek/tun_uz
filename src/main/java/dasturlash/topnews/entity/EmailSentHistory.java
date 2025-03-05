package dasturlash.topnews.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_sent_history")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailSentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    int code;

    @Column(nullable = false)
    LocalDateTime sentDate;

    @Column(nullable = false)
    LocalDateTime expiredDate;

    @Column(nullable = false)
    boolean expired;
}

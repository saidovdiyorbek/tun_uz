package dasturlash.tun_uz.entity;

import dasturlash.tun_uz.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "profiles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String surname;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String phone;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ProfileStatus status;

    @OneToMany(mappedBy = "profile")
    private List<ProfileRoleEntity> roles;

    @Column(nullable = false)
    boolean visible;

    @Column(nullable = false)
    LocalDateTime  createdDate;

    @Column(nullable = false)
    String photoId;

}

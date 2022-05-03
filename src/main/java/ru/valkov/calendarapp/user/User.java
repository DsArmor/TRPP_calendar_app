package ru.valkov.calendarapp.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    @Column(unique = true, nullable = false)
    private Long id;
    private String encodedPassword;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}

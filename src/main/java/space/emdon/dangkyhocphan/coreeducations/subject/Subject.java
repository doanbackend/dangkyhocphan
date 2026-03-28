package space.emdon.dangkyhocphan.coreeducations.subject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;

import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String code;

    @Column(nullable = false)
    String name;

    int credits;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    Set<Sectionclass> sections;

}

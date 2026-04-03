package space.emdon.dangkyhocphan.transactions.registration;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;
import space.emdon.dangkyhocphan.enums.RegistrationStatus;
import space.emdon.dangkyhocphan.rbac.user.User;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(
	uniqueConstraints = {@UniqueConstraint(columnNames = {"student_numbered", "sectionclass_name"})})
public class Registration {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private String id;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "student_numbered")
private User student;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "section_class_name")
private Sectionclass sectionclass;

@Enumerated(EnumType.STRING)
@Builder.Default
private RegistrationStatus status = RegistrationStatus.PENDING;

@Builder.Default private LocalDateTime registrationDateTime = LocalDateTime.now();
}

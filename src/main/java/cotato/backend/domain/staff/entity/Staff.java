package cotato.backend.domain.staff.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="staff")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Staff {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="staff_id")
    private Long id;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="age", nullable=false)
    private int age;

    @Column(name="phone_number",nullable=false)
    private String phoneNumber;

    @Column(name="role",nullable=false)
    private String role;

    @Builder
    public Staff(String name, int age, String phoneNumber, String role) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void update(String name, int age, String phoneNumber, String role) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}

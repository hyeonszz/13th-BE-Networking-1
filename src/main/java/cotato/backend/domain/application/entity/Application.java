package cotato.backend.domain.application.entity;

import cotato.backend.domain.applicant.entity.Applicant;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="application")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="application_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="applicant_id", nullable=false)
    private Applicant applicant;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="period", nullable=false)
    private int period;

    @Column(name="age",nullable=false)
    private int age;

    @Column(name="part", nullable=false)
    private String part;

    @Column(name="ability", nullable=false)
    private int ability;

    @Column(name="passion", nullable=false)
    private int passion;

    @Column(name="application_time", nullable=false)
    private LocalDateTime applicationTime;

    @Column(name="phone_number", nullable=false)
    private String phoneNumber;

    @Builder
    public Application(Applicant applicant, String name, int period, int age, String part, int ability, int passion, LocalDateTime applicationTime, String phoneNumber) {
        this.applicant=applicant;
        this.name=name;
        this.period=period;
        this.age=age;
        this.part=part;
        this.ability=ability;
        this.passion=passion;
        this.applicationTime=applicationTime;
        this.phoneNumber=phoneNumber;
    }



}

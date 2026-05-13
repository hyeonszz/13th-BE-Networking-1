package cotato.backend.domain.likes.entity;

import cotato.backend.domain.application.entity.Application;
import cotato.backend.domain.staff.entity.Staff;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="likes")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="application_id", nullable=false)
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="staff_id",nullable=false)
    private Staff staff;

    @Builder
    public Likes(Application application, Staff staff) {
        this.application=application;
        this.staff=staff;
    }
}

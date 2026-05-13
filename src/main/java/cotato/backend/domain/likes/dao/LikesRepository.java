package cotato.backend.domain.likes.dao;

import cotato.backend.domain.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    boolean existsByApplicationIdAndStaffId(Long applicationId, Long staffId);
    long countByApplicationId(Long applicationId);
}

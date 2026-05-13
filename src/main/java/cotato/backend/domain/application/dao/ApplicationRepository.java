package cotato.backend.domain.application.dao;

import cotato.backend.domain.application.entity.Application;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query(
            value = "SELECT a.* FROM application a " +
                    "LEFT JOIN (SELECT application_id, COUNT(*) AS cnt FROM likes GROUP BY application_id) lc " +
                    "ON a.application_id = lc.application_id " +
                    "ORDER BY COALESCE(lc.cnt, 0) DESC",
            countQuery = "SELECT COUNT(*) FROM application",
            nativeQuery = true
    )
    List<Application> findAllOrderByLikes(Pageable pageable);

    @Query("SELECT a FROM Application a ORDER BY a.period ASC")
    List<Application> findAllOrderByPeriod(Pageable pageable);

    @Query(
            value = "SELECT a.* FROM application a " +
                    "LEFT JOIN (SELECT application_id, COUNT(*) AS cnt FROM likes GROUP BY application_id) lc " +
                    "ON a.application_id = lc.application_id " +
                    "ORDER BY a.period ASC, COALESCE(lc.cnt, 0) DESC",
            countQuery = "SELECT COUNT(*) FROM application",
            nativeQuery = true
    )
    List<Application> findAllOrderByPeriodAndLikes(Pageable pageable);

    boolean existsByApplicantIdAndPeriod(Long applicantId, int period);
}

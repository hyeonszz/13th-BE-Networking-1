package cotato.backend.domain.likes.application;

import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.EntityNotFoundException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.domain.application.dao.ApplicationRepository;
import cotato.backend.domain.application.entity.Application;
import cotato.backend.domain.likes.dao.LikesRepository;
import cotato.backend.domain.likes.dto.request.LikesRequest;
import cotato.backend.domain.likes.dto.response.LikesResponse;
import cotato.backend.domain.likes.entity.Likes;
import cotato.backend.domain.staff.dao.StaffRepository;
import cotato.backend.domain.staff.entity.Staff;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesService {

    private final LikesRepository likesRepository;
    private final ApplicationRepository applicationRepository;
    private final StaffRepository staffRepository;

    @Transactional
    public LikesResponse like(Long applicationId, LikesRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND));

        Staff staff = staffRepository.findById(request.staffId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND));

        if (likesRepository.existsByApplicationIdAndStaffId(applicationId, request.staffId())) {
            throw new AppException(ErrorCode.ALREADY_LIKED);
        }

        Likes likes = Likes.builder()
                .application(application)
                .staff(staff)
                .build();

        return LikesResponse.from(likesRepository.save(likes));
    }

    @Transactional
    public void unlike(Long applicationId, Long likesId) {
        Likes likes = likesRepository.findById(likesId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.LIKES_NOT_FOUND));

        if (!likes.getApplication().getId().equals(applicationId)) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        likesRepository.delete(likes);
    }
}

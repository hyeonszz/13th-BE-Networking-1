package cotato.backend.domain.application.application;

import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.EntityNotFoundException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.domain.applicant.dao.ApplicantRepository;
import cotato.backend.domain.applicant.entity.Applicant;
import cotato.backend.domain.application.dao.ApplicationRepository;
import cotato.backend.domain.application.dto.request.ApplicationFilterRequest;
import cotato.backend.domain.application.dto.request.ApplicationRequest;
import cotato.backend.domain.application.dto.response.ApplicationListResponse;
import cotato.backend.domain.application.dto.response.ApplicationResponse;
import cotato.backend.domain.application.entity.Application;
import cotato.backend.domain.likes.dao.LikesRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicantRepository applicantRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public ApplicationResponse save(ApplicationRequest request) {
        Applicant applicant = applicantRepository.findByPhoneNumber(request.phoneNumber())
                .orElseGet(() -> applicantRepository.save(
                        Applicant.builder()
                                .name(request.name())
                                .age(request.age())
                                .phoneNumber(request.phoneNumber())
                                .build()
                ));

        if (applicationRepository.existsByApplicantIdAndPeriod(applicant.getId(), request.period())) {
            throw new AppException(ErrorCode.DUPLICATE_APPLICATION);
        }

        Application application = Application.builder()
                .applicant(applicant)
                .name(request.name())
                .period(request.period())
                .age(request.age())
                .part(request.part())
                .ability(request.ability())
                .passion(request.passion())
                .applicationTime(request.applicationTime())
                .phoneNumber(request.phoneNumber())
                .build();

        return ApplicationResponse.from(applicationRepository.save(application));
    }

    public ApplicationResponse findById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND));
        return ApplicationResponse.from(application);
    }

    public List<ApplicationListResponse> findList(ApplicationFilterRequest request) {
        int page = Math.max(request.page() - 1, 0);
        int pageSize = request.pageSize() > 0 ? request.pageSize() : 10;
        PageRequest pageable = PageRequest.of(page, pageSize);

        List<Application> applications = switch (request.filterBy()) {
            case "likes" -> applicationRepository.findAllOrderByLikes(pageable);
            case "gisu" -> applicationRepository.findAllOrderByPeriod(pageable);
            case "gisu+likes" -> applicationRepository.findAllOrderByPeriodAndLikes(pageable);
            default -> throw new AppException(ErrorCode.INVALID_FILTER_TYPE);
        };

        return applications.stream()
                .map(a -> ApplicationListResponse.from(a, (int) likesRepository.countByApplicationId(a.getId())))
                .toList();
    }
}

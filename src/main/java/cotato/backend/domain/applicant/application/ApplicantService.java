package cotato.backend.domain.applicant.application;

import cotato.backend.common.exception.EntityNotFoundException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.domain.applicant.dao.ApplicantRepository;
import cotato.backend.domain.applicant.dto.request.ApplicantRequest;
import cotato.backend.domain.applicant.dto.response.ApplicantResponse;
import cotato.backend.domain.applicant.entity.Applicant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor(access= AccessLevel.PROTECTED)
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    // 지원자 조회
    public ApplicantResponse findById(long id) {
        return ApplicantResponse.from(
                applicantRepository.findById(id)
                        .orElseThrow(()->new EntityNotFoundException(ErrorCode.NOT_FOUND))
        );
    }

    // 지원자 수정
    @Transactional
    public ApplicantResponse update(Long id, ApplicantRequest request) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(ErrorCode.NOT_FOUND));

        applicant.update(request.name(), request.age(), request.phoneNumber());

        return ApplicantResponse.from(applicant);
    }
}

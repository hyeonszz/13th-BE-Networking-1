package cotato.backend.domain.applicant.dto.response;

import cotato.backend.domain.applicant.entity.Applicant;

// 지원자 조회/수정/등록 결과를 돌려줄 때 보내는 데이터
public record ApplicantResponse(
        Long id,
        String name,
        int age,
        String phoneNumber
) {
    public static ApplicantResponse from(Applicant applicant) {
        return new ApplicantResponse(
            applicant.getId(),
            applicant.getName(),
            applicant.getAge(),
            applicant.getPhoneNumber()
        );
    }
}

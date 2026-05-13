package cotato.backend.domain.applicant.dto.request;

// 클라이언트가 지원자 등록할 때 보내는 데이터
public record ApplicantRequest(
        String name,
        int age,
        String phoneNumber
) {
}

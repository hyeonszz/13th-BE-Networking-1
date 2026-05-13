package cotato.backend.domain.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record ApplicationRequest(
        @NotBlank
        @Pattern(regexp = "^[가-힣]{2,10}$", message = "이름은 한글 2글자 이상 10글자 이하여야 합니다.")
        String name,

        @Min(value = 1, message = "지원 기수는 1 이상이어야 합니다.")
        int period,

        @Min(value = 22, message = "나이는 22살 이상이어야 합니다.")
        @Max(value = 30, message = "나이는 30살 이하여야 합니다.")
        int age,

        @NotBlank
        @Pattern(regexp = "^(기획|디자이너|프론트엔드|백엔드)$", message = "지원 파트는 기획, 디자이너, 프론트엔드, 백엔드 중 하나여야 합니다.")
        String part,

        @Min(value = 0, message = "실력은 0 이상이어야 합니다.")
        @Max(value = 10, message = "실력은 10 이하여야 합니다.")
        int ability,

        @Min(value = 0, message = "열정은 0 이상이어야 합니다.")
        @Max(value = 10, message = "열정은 10 이하여야 합니다.")
        int passion,

        @NotNull(message = "서류 제출 시간을 입력해주세요.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime applicationTime,

        @NotBlank
        @Pattern(regexp = "^010\\d{8}$", message = "휴대폰 번호는 010으로 시작하는 11자리여야 합니다.")
        String phoneNumber
) {
}

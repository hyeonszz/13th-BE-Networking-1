package cotato.backend.domain.application.dto.response;

import cotato.backend.domain.application.entity.Application;

import java.time.LocalDateTime;

public record ApplicationResponse(
        Long applicationId,
        String name,
        int period,
        int age,
        String part,
        int ability,
        int passion,
        LocalDateTime applicationTime,
        String phoneNumber
) {
    public static ApplicationResponse from(Application application){
        return new ApplicationResponse(
                application.getId(),
                application.getName(),
                application.getPeriod(),
                application.getAge(),
                application.getPart(),
                application.getAbility(),
                application.getPassion(),
                application.getApplicationTime(),
                application.getPhoneNumber()
        );
    }
}

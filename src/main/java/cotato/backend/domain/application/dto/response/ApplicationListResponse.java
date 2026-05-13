package cotato.backend.domain.application.dto.response;

import cotato.backend.domain.application.entity.Application;

public record ApplicationListResponse(
        Long id,
        String name,
        int period,
        String part,
        int likeCount
) {
    public static ApplicationListResponse from(Application application, int likeCount){
        return new ApplicationListResponse(
                application.getId(),
                application.getName(),
                application.getPeriod(),
                application.getPart(),
                likeCount
        );
    }
}

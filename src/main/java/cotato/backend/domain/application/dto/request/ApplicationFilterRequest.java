package cotato.backend.domain.application.dto.request;

public record ApplicationFilterRequest(
        String filterBy,
        int page,
        int pageSize
) {
}

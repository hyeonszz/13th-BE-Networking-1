package cotato.backend.domain.staff.dto.request;

public record StaffRequest(
        String name,
        int age,
        String phoneNumber,
        String role
) {
}

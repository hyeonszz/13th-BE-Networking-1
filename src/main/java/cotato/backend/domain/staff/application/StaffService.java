package cotato.backend.domain.staff.application;

import cotato.backend.common.exception.EntityNotFoundException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.domain.staff.dao.StaffRepository;
import cotato.backend.domain.staff.dto.request.StaffRequest;
import cotato.backend.domain.staff.dto.response.StaffResponse;
import cotato.backend.domain.staff.entity.Staff;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StaffService {

    private final StaffRepository staffRepository;

    @Transactional
    public StaffResponse save(StaffRequest request) {
        Staff staff = Staff.builder()
                .name(request.name())
                .age(request.age())
                .phoneNumber(request.phoneNumber())
                .role(request.role())
                .build();
        return StaffResponse.from(staffRepository.save(staff));
    }

    public StaffResponse findById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND));
        return StaffResponse.from(staff);
    }

    @Transactional
    public StaffResponse update(Long id, StaffRequest request) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND));
        staff.update(request.name(), request.age(), request.phoneNumber(), request.role());
        return StaffResponse.from(staff);
    }
}

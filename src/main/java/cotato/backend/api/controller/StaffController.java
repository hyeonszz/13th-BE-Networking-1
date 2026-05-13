package cotato.backend.api.controller;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domain.staff.application.StaffService;
import cotato.backend.domain.staff.dto.request.StaffRequest;
import cotato.backend.domain.staff.dto.response.StaffResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/staffs")
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    public ResponseEntity<DataResponse<StaffResponse>> save(@RequestBody StaffRequest request) {
        return ResponseEntity.ok(DataResponse.created(staffService.save(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<StaffResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(DataResponse.from(staffService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<StaffResponse>> update(
            @PathVariable Long id,
            @RequestBody StaffRequest request) {
        return ResponseEntity.ok(DataResponse.from(staffService.update(id, request)));
    }
}

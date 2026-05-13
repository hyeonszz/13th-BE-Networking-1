package cotato.backend.api.controller;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domain.applicant.application.ApplicantService;
import cotato.backend.domain.applicant.dto.request.ApplicantRequest;
import cotato.backend.domain.applicant.dto.response.ApplicantResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<ApplicantResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(DataResponse.from(applicantService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<ApplicantResponse>> update(
            @PathVariable Long id,
            @RequestBody ApplicantRequest request) {
        return ResponseEntity.ok(DataResponse.from(applicantService.update(id, request)));
    }
}

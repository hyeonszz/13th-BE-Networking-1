package cotato.backend.api.controller;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domain.application.application.ApplicationService;
import cotato.backend.domain.application.dto.request.ApplicationFilterRequest;
import cotato.backend.domain.application.dto.request.ApplicationRequest;
import cotato.backend.domain.application.dto.response.ApplicationListResponse;
import cotato.backend.domain.application.dto.response.ApplicationResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<DataResponse<ApplicationResponse>> save(@Valid @RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(DataResponse.created(applicationService.save(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<ApplicationResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(DataResponse.from(applicationService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<ApplicationListResponse>>> findList(
            @RequestParam String filterBy,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        ApplicationFilterRequest request = new ApplicationFilterRequest(filterBy, page, pageSize);
        return ResponseEntity.ok(DataResponse.from(applicationService.findList(request)));
    }
}

package cotato.backend.api.controller;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domain.likes.application.LikesService;
import cotato.backend.domain.likes.dto.request.LikesRequest;
import cotato.backend.domain.likes.dto.response.LikesResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/applications/{applicationId}/likes")
public class LikesController {

    private final LikesService likesService;

    @PostMapping
    public ResponseEntity<DataResponse<LikesResponse>> like(
            @PathVariable Long applicationId,
            @RequestBody LikesRequest request) {
        return ResponseEntity.ok(DataResponse.created(likesService.like(applicationId, request)));
    }

    @DeleteMapping("/{likesId}")
    public ResponseEntity<DataResponse<Void>> unlike(
            @PathVariable Long applicationId,
            @PathVariable Long likesId) {
        likesService.unlike(applicationId, likesId);
        return ResponseEntity.ok(DataResponse.ok());
    }
}

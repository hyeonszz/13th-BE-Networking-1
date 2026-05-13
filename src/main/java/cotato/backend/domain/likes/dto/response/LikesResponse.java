package cotato.backend.domain.likes.dto.response;

import cotato.backend.domain.likes.entity.Likes;

public record LikesResponse(
        Long likesId,
        Long staffId,
        Long applicationId
) {
    public static LikesResponse from(Likes likes) {
        return new LikesResponse(
                likes.getId(),
                likes.getStaff().getId(),
                likes.getApplication().getId()
        );
    }
}

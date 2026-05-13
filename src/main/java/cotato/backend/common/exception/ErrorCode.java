package cotato.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	//400
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", "COMMON-001"),
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "요청 파라미터가 잘못되었습니다.", "COMMON-002"),
	NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없습니다.", "COMMON-003"),

	// Application
	DUPLICATE_APPLICATION(HttpStatus.BAD_REQUEST, "이미 해당 기수에 지원한 이력이 있습니다.", "APPLICATION-001"),
	INVALID_FILTER_TYPE(HttpStatus.BAD_REQUEST, "잘못된 필터 타입입니다. likes, gisu, gisu+likes 중 하나를 선택하세요.", "APPLICATION-002"),

	// Likes
	ALREADY_LIKED(HttpStatus.BAD_REQUEST, "이미 좋아요를 눌렀습니다.", "LIKES-001"),
	LIKES_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요를 찾을 수 없습니다.", "LIKES-002"),

	//500
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 에러가 발생하였습니다.", "COMMON-004"),
	;

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}
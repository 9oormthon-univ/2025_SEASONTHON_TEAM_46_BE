package jpabasic.newsthinkybe.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //Auth
    USER_NOT_FOUND("4040","유저를 찾을 수 없습니다."),
    PROVIDER_ERROR("1010","해당 로그인 도메인은 없습니다."),
    INVALID_TOKEN("3030","유효하지 않은 토큰입니다."),
    INVALID_JWT_SIGNATURE("3031","jwt의 토큰 시그니처가 유효하지 않습니다."),
    UNAUTHORIZED("3032","로그인이 필요한 요청입니다."),
    INVALID_REFRESH_TOKEN("3033","유효하지 않은 refresh token 입니다."),
    REFRESH_TOKEN_DELETE_ERROR("3034","redis refresh token 삭제에 실패했어요."),

    // News
    NEWS_NOT_FOUND("5050", "뉴스를 찾을 수 없습니다."),
    CSV_READ_ERROR("5100", "CSV 파일을 읽는 도중 에러가 발생했습니다."),

    // Common
    NOT_FOUND("404", "Not Found"),

    //comment
    COMMENT_SAVE_ERROR("2020","댓글 저장에 오류가 발생했습니다"),
    COMMENT_DELETE_FAILURE("2021","댓글 삭제에 오류가 발생했습니다"),
    COMMENT_NOT_FOUND("2022","해당 댓글을 찾을 수 없어요.");

    private final String code;
    private final String message;
}

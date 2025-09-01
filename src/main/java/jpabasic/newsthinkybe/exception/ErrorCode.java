package jpabasic.newsthinkybe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("4040","유저를 찾을 수 없습니다.");

    private final String code;
    private final String message;
}

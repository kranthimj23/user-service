package com.mobilebanking.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    public UserException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    public static UserException userNotFound() {
        return new UserException("User not found", HttpStatus.NOT_FOUND, "USER_001");
    }

    public static UserException userNotFoundByAuthId() {
        return new UserException("User not found for the given auth ID", HttpStatus.NOT_FOUND, "USER_002");
    }

    public static UserException emailAlreadyExists() {
        return new UserException("Email already registered", HttpStatus.CONFLICT, "USER_003");
    }

    public static UserException phoneAlreadyExists() {
        return new UserException("Phone number already registered", HttpStatus.CONFLICT, "USER_004");
    }

    public static UserException authIdAlreadyExists() {
        return new UserException("User already exists for this auth ID", HttpStatus.CONFLICT, "USER_005");
    }

    public static UserException profileNotFound() {
        return new UserException("User profile not found", HttpStatus.NOT_FOUND, "USER_006");
    }

    public static UserException invalidStatusTransition(String from, String to) {
        return new UserException(
                String.format("Invalid status transition from %s to %s", from, to),
                HttpStatus.BAD_REQUEST,
                "USER_007"
        );
    }

    public static UserException userInactive() {
        return new UserException("User account is not active", HttpStatus.FORBIDDEN, "USER_008");
    }
}

package com.jennifer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T>{
    private int status;
    private String message;
    private T data;
    private HttpStatus httpStatus;

    public ApiResponse(int status, String message) {
        this.status = HttpStatus.OK.value();
        this.message = message;
    }
public ApiResponse(String message, T data){
        this.message = message;
        this.data = data;
}

    public ApiResponse(T data, HttpStatus httpStatus) {
        this.data = data;
        this.httpStatus = httpStatus;
    }

    public ApiResponse(String errorMessage) {
    }

    public ApiResponse(String message, T data, HttpStatus httpStatus) {
        this.message = message;
        this.data = data;
        this.httpStatus = httpStatus;
    }

    public static <T> ApiResponse<T> OK(T data){
        return new ApiResponse<>("OK", data);
    }

    public static <T> ApiResponse<T> CREATED(String message){
        return new ApiResponse<>(message);
    }


}

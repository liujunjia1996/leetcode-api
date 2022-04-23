package leetcode.api.aop;

import leetcode.api.pojo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResponse exceptionHandler(Exception ex) {
        return CommonResponse.fail(500, ex.getMessage());
    }


}
package leetcode.api.controller;

import leetcode.api.pojo.CommonResponse;
import leetcode.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    @Resource
    private UserService userService;

    static final CommonResponse NO_NAME = CommonResponse.fail(400, "need username");

    @GetMapping(value = {"/progress/{username}"})
    public CommonResponse getProgress(@PathVariable String username) {
        if (StringUtils.hasLength(username)) {
            return CommonResponse.success(userService.getProgress(username));
        } else {
            return NO_NAME;
        }
    }

    @GetMapping(value = {"/recentSubmit/{username}"})
    public CommonResponse getRecentSubmit(@PathVariable String username) {
        if (StringUtils.hasLength(username)) {
            return CommonResponse.success(userService.getRecentSubmit(username));
        } else {
            return NO_NAME;
        }
    }

    @GetMapping(value = {"/calendar/{username}"})
    public CommonResponse getCalendar(@PathVariable String username) {
        if (StringUtils.hasLength(username)) {
            return CommonResponse.success(userService.getCalendar(username));
        } else {
            return NO_NAME;
        }
    }

}


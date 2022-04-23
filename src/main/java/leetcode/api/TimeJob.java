package leetcode.api;

import leetcode.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TimeJob {

    final UserService userService;

    final CaffeineCacheManager cacheManager;

    static final String MY_SELF = "qaqljj";

    @Scheduled(cron = "0 */4 * * * ?")
    void setCache() {
        log.info("set cache");
        userService.getCalendar(MY_SELF);
        userService.getProgress(MY_SELF);
        userService.getRecentSubmit(MY_SELF);
    }

}

package leetcode.api.service;

import cn.hutool.json.JSONUtil;
import leetcode.api.pojo.BizException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {

    static final String PROGRESS_TEMPLATE = "{\"operationName\":\"userQuestionProgress\",\"variables\":{\"userSlug\":\"%s\"},\"query\":\"query userQuestionProgress($userSlug: String!) {\\n  userProfileUserQuestionProgress(userSlug: $userSlug) {\\n    numAcceptedQuestions {\\n      difficulty\\n      count\\n      __typename\\n    }\\n    numFailedQuestions {\\n      difficulty\\n      count\\n      __typename\\n    }\\n    numUntouchedQuestions {\\n      difficulty\\n      count\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";

    static final String RECENT_SUBMIT_TEMPLATE = "{\"query\":\"\\n    query recentAcSubmissions($userSlug: String!) {\\n  recentACSubmissions(userSlug: $userSlug) {\\n    submissionId\\n    submitTime\\n    question {\\n      translatedTitle\\n      titleSlug\\n      questionFrontendId\\n    }\\n  }\\n}\\n    \",\"variables\":{\"userSlug\":\"%s\"}}";

    static final String CALENDAR_TEMPLATE = "{\"query\":\"\\n    query userProfileCalendar($userSlug: String!, $year: Int) {\\n  userCalendar(userSlug: $userSlug, year: $year) {\\n    streak\\n    totalActiveDays\\n    submissionCalendar\\n  }\\n}\\n    \",\"variables\":{\"userSlug\":\"%s\"}}";

    static final String DATA = "data";

    OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(5, TimeUnit.SECONDS).build();

    static final MediaType MEDIA_TYPE = MediaType.parse("application/json");

    static final String URL = "https://leetcode-cn.com/graphql/";

    static final String URL_NOJ_GO = "https://leetcode-cn.com/graphql/noj-go/";


    @Cacheable(cacheNames = "progress", key = "#username")
    public String getProgress(String username) {
        String responseString = getResponseString(PROGRESS_TEMPLATE, username, URL);
        return JSONUtil.parseObj(responseString).getStr(DATA);
    }

    @Cacheable(cacheNames = "recentSubmit", key = "#username")
    public String getRecentSubmit(String username) {
        String responseString = getResponseString(RECENT_SUBMIT_TEMPLATE, username, URL_NOJ_GO);
        return JSONUtil.parseObj(responseString).getStr(DATA);
    }

    @Cacheable(cacheNames = "calendar", key = "#username")
    public String getCalendar(String username) {
        String responseString = getResponseString(CALENDAR_TEMPLATE, username, URL_NOJ_GO);
        return JSONUtil.parseObj(responseString).getStr(DATA);
    }

    @NotNull
    private String getResponseString(String recentSubmitTemplate, String username, String url) {
        String query = String.format(recentSubmitTemplate, username);
        Request request = new Request.Builder().url(url).post(RequestBody.create(query, MEDIA_TYPE)).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            Objects.requireNonNull(response.body());
            return response.body().string();
        } catch (IOException e) {
            log.error("request error", e);
            throw new BizException("io error");
        }
    }

}

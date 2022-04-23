package leetcode.api.pojo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.util.List;

/*
 * 定义实例类太麻烦了
 * 这个类废弃，直接返回 json 字符串给前端
 */
@Data
public class ProgressResp {

    Data data;

    @lombok.Data
    public static class Data {

        UserProfileUserQuestionProgress userProfileUserQuestionProgress;

    }

    @lombok.Data
    public static class UserProfileUserQuestionProgress {

        private List<Question> numAcceptedQuestions;

        private List<Question> numFailedQuestions;

        private List<Question> numUntouchedQuestions;

    }

    @lombok.Data
    public static class Question {

        private String difficulty;

        private int count;

        @Alias("__typename")
        private String typename;

    }

}

package sjsu.zankhna.quizapp.utils;

public class Constants {

    // API request parameters name
    public static final String PARAM_AMOUNT = "amount";
    public static final String PARAM_CATEGORY = "category";
    public static final String PARAM_DIFFICULTY = "difficulty";
    public static final String PARAM_TYPE = "type";

    // API request parameters value
    public static int VALUE_AMOUNT = 10;
    public static int VALUE_CATEGORY = 9;
    public static String VALUE_DIFFICULTY = "easy";
    public static String VALUE_TYPE = "multiple";

    // API response code
    public static final int RESPONSE_SUCCESS = 0;
    public static final int RESPONSE_NO_RESULT = 1;
    public static final int RESPONSE_INVALID_PARAMS = 2;
    public static final int RESPONSE_TOKEN_NOT_FOUND = 3;
    public static final int RESPONSE_TOKEN_EMPTY = 4;   //Reset token to get new questions


    // App constants
    public static final String RESPONSE_QUESTION_SERVICE = "responseQuestionService";
    public static final String PAYLOAD_QUESTION_SERVICE = "payloadQuestionService";
}

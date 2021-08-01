package requestBody;

public class PostRequest {

    public static String postRequestBody() {
        String payLoad =
                "{\n" +
                        "    \"title\": \"foo\",\n" +
                        "    \"body\": \"bar\",\n" +
                        "    \"userId\": \"1\"\n" +
                        "  }";
        return payLoad;
    }

    public static String putRequestBody() {
        String payLoad =
                "{\n" +
                        "    \"id\": \"1\",\n" +
                        "    \"title\": \"foo\",\n" +
                        "    \"body\": \"bar\",\n" +
                        "    \"userId\": \"1\"\n" +
                        "  }";
        return payLoad;
    }
}

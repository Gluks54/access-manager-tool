package addToGitLab;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;
import java.util.Map;

public class GitLabService {

    public HttpResponse<JsonNode> addToGitLab(String projectId, String privateToken) throws UnirestException {

        Map<String, Object> fields = new HashMap<>();
        fields.put("user_id", "4311345");
        fields.put("access_level", "30");

        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");

        String url =
                String.format("https://gitlab.com/api/v4/projects/%s/members?private_token=%s", projectId, privateToken);

        HttpResponse<JsonNode> jsonResponse =
                Unirest.post(url)
                        .headers(headers)
                        .fields(fields)
                        .asJson();

        System.out.println(jsonResponse.getBody());
        return jsonResponse;
    }
}

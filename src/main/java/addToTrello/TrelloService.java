package addToTrello;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TrelloService {

    public HttpResponse<JsonNode> addToTrello(String projectId, String email, String key) throws UnirestException {
        String url =
                String.format("https://api.trello.com/1/boards/%s/members?email=%s&key=%s", projectId, email, key);

        HttpResponse<JsonNode> jsonResponse = Unirest.put(url).asJson();
        System.out.println(jsonResponse.getBody());
        return jsonResponse;
    }
}

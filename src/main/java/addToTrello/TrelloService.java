package addToTrello;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

public class TrelloService {

    public HttpResponse<JsonNode> addToTrello(String projectId, String email, String key) throws UnirestException {
        String url =
                String.format("https://api.trello.com/1/boards/%s/members?email=%s&key=%s",projectId,email,key);

        HttpResponse<JsonNode> jsonResponse = Unirest.put(url).asJson();
        System.out.println(jsonResponse.getBody());
        System.out.println("successfully added to Trello board");
        return jsonResponse;
    }

    public String getRole(String projectId,String userName,String key) throws UnirestException {
        String url =
                String.format("https://trello.com/1/boards/%s/memberships/?orgMemberType=true&member=true&member_fields=all&key=%s",projectId,key);
        HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();

//        System.out.println(jsonResponse.getBody().getArray().getJSONObject(1).getJSONObject("member").get("username"));

        JSONArray jsonArray = jsonResponse.getBody().getArray();
        int length = jsonArray.length();

        for (int i = 0;i < length;i++) {
            JSONObject responce = jsonResponse
                    .getBody()
                    .getArray()
                    .getJSONObject(i)
                    .getJSONObject("member");

            String resUserName = responce
                    .get("username")
                    .toString();

            if(resUserName.equals(userName)){
                return responce.getString("memberType");
            }
        }
        return "Can't find user with such name";
    }
}


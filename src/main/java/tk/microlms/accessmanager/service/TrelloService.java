package tk.microlms.accessmanager.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

public class TrelloService {
    private String key;
    String projectId;

    public TrelloService(String key,String projectId){
        this.key = key;
        this.projectId = projectId;
    }

    public void addToTrello(String email) throws UnirestException {
        String url =
                String.format("https://api.trello.com/1/boards/%s/members?email=%s&key=%s",projectId,email,key);

         Unirest.put(url).asJson();
    }

    public void getStatus() throws UnirestException {
        String url =
                String.format("https://trello.com/1/boards/%s/memberships/?orgMemberType=true&member=true&member_fields=all&key=%s",projectId,key);

        //disable sending cookies
        HttpClient httpClient = HttpClients.custom()
                .disableCookieManagement()
                .build();
        Unirest.setHttpClient(httpClient);

        HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
        JSONArray jsonArray = jsonResponse.getBody().getArray();
        int length = jsonArray.length();

        for (int i = 0;i < length;i++) {
            JSONObject responce = jsonResponse
                    .getBody()
                    .getArray()
                    .getJSONObject(i)
                    .getJSONObject("member");
            System.out.println("fullName: " + responce.get("fullName").toString()
                    +" username: "+ responce.get("username")
                    +" status: " + responce.get("memberType"));
        }
    }

    public void deleteMember(String username) throws UnirestException {
        String url =
                String.format("https://api.trello.com/1/boards/%s/members/%s?key=%s",projectId,username,key);

        Unirest.delete(url).asJson();
    }
}


package tk.microlms.accessmanager.service;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.NoArgsConstructor;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import tk.microlms.accessmanager.model.TrelloUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class TrelloService {
    private TrelloEmailService trelloEmailService = new TrelloEmailService();
    private String key;
    private String projectId;
    private String token;
    private HttpClient httpClient = HttpClients.custom()
        .disableCookieManagement()
        .build();

    public TrelloService(String key, String projectId, String token) {
        this.key = key;
        this.projectId = projectId;
        this.token = token;
    }

    public void addToTrello(String email) throws UnirestException, IOException {
        String url =
            String.format("https://api.trello.com/1/boards/%s/members?email=%s&key=%s&token=%s",
                projectId, email, key, token);

        List<TrelloUser> usersFormDoc = trelloEmailService.reader();

        JSONArray responce = Unirest
            .put(url)
            .asJson()
            .getBody()
            .getObject()
            .getJSONArray("members");

        List<String> userNames = new ArrayList<>();

        for (int i = 0; i < responce.length(); i++) {
            String username = responce
                .getJSONObject(i)
                .get("username")
                .toString();

            userNames.add(username);
        }
        compareTwoList(usersFormDoc, userNames, email);
    }

    private void compareTwoList(List<TrelloUser> usersFormDoc,
                                List<String> userNames, String email) throws IOException {
        String rezult = null;
        for (int i = 0; i < usersFormDoc.size(); i++) {
            for (int y = 0; y < userNames.size(); y++) {
                if (!usersFormDoc.get(i).getUsername().equals(userNames.get(y))) {
                    rezult = userNames.get(y);
                }
            }
        }

        TrelloUser tempUser = TrelloUser
            .builder()
            .email(email)
            .username(rezult)
            .build();
        trelloEmailService.addUsersToFile(tempUser);
        System.out.println("add to Trello");
    }

    public void getStatus() throws UnirestException, IOException {
        String url =
            String.format(
                "https://trello.com/1/boards/%s/memberships/" +
                    "?orgMemberType=true&member=true&member_fields=all&key=%s&token=%s", projectId, key, token);
        //disable sending cookies
        Unirest.setHttpClient(httpClient);

        HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
        JSONArray jsonArray = jsonResponse.getBody().getArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject responce = jsonResponse
                .getBody()
                .getArray()
                .getJSONObject(i)
                .getJSONObject("member");
            System.out.println("fullName: " + responce.get("fullName").toString()
                + " username: " + responce.get("username")
                + " status: " + responce.get("memberType") + " email: " +
                trelloEmailService.getEmailByUserName(responce.get("username").toString()));
        }
    }

    public void deleteMember(String email) throws UnirestException, IOException {
        String username = trelloEmailService.getUserNameByEmail(email);

        String url =
            String.format("https://api.trello.com/1/boards/%s/members/%s?key=%s&token=%s", projectId, username, key, token);

        Unirest.delete(url).asJson();

        TrelloUser trelloUser = TrelloUser
            .builder()
            .username(username)
            .email(email)
            .build();

        trelloEmailService.removeByUser(trelloUser);
    }

    public TrelloUser getMyData() throws UnirestException {
        String url =
            String.format("https://api.trello.com/1/members/me?token=%s&key=%s", token, key);
        //disable sending cookies
        Unirest.setHttpClient(httpClient);

        JSONObject jsonResponse = Unirest
            .get(url)
            .asJson()
            .getBody()
            .getObject();

        TrelloUser trelloUser = TrelloUser
            .builder()
            .email(jsonResponse.get("email").toString())
            .username(jsonResponse.get("username").toString())
            .build();
        return trelloUser;
    }
}


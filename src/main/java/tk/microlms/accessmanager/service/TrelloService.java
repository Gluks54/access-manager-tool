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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    public TrelloService(String key, String token) {
        this.key = key;
        this.token = token;
    }

    public void addToTrello(String email) throws UnirestException, IOException {
        String url =
            String.format("https://api.trello.com/1/boards/%s/members?email=%s&key=%s&token=%s",
                projectId, email, key, token);

        List<TrelloUser> usersFormDoc = trelloEmailService.reader();
        List<String> userNames = new ArrayList<>();

        JSONArray responce = Unirest
            .put(url)
            .asJson()
            .getBody()
            .getObject()
            .getJSONArray("members");

        for (int i = 0; i < responce.length(); i++) {
            String username = responce
                .getJSONObject(i)
                .getString("username");

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
            System.out.println("FullName: " + responce.get("fullName").toString()
                + " Username: " + responce.get("username")
                + " Status: " + responce.get("memberType").toString().toUpperCase() + " Email: " +
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
            String.format("https://api.trello.com/1/members/me?boards=all&token=%s&key=%s", token, key);
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
            .mapIndexAndBoards(geMapOfTrelloBoards(jsonResponse))
            .mapIndexAndProjectId(getMapOfTrelloProjectId(jsonResponse))
            .build();

        return trelloUser;
    }

    private Map<String, String> geMapOfTrelloBoards(JSONObject jsonResponse) {
        Map<String, String> mapIdAndBoards = new HashMap<>();

        JSONArray jsonArray = jsonResponse.getJSONArray("boards");

        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.length() == 0) {
                System.out.println("Sorry, no boards available to your key\\" +
                    "token pair, you must input board URL\n:");
                break;
            }

            String index = String.valueOf(i);
            String boardName = jsonArray.getJSONObject(i).getString("name");
            mapIdAndBoards.put(index, boardName);
        }
        return mapIdAndBoards;
    }

    public Map<String, String> getMapOfTrelloProjectId(JSONObject jsonResponse) {
        Map<String, String> mapIndexAndProjectId = new HashMap<>();

        JSONArray jsonArray = jsonResponse.getJSONArray("boards");

        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.length() == 0) {
                System.out.println("Sorry, no boards available to your key\\" +
                    "token pair, you must input board URL\n:");
                break;
            }

            String index = String.valueOf(i);
            String projectId = jsonArray.getJSONObject(i).getString("id");
            mapIndexAndProjectId.put(index, projectId);
        }
        return mapIndexAndProjectId;
    }

    public String getProjectIdByURL(String url) throws UnirestException {
        String finalURL =
            String.format("%s.json?token=%s&key=%s", url, token, key);

        Unirest.setHttpClient(httpClient);

        return Unirest
            .get(finalURL)
            .asJson()
            .getBody()
            .getObject()
            .getString("id");
    }
}


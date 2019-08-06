package tk.microlms.accessmanager.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tk.microlms.accessmanager.model.FilePath;
import tk.microlms.accessmanager.model.GitlabUser;
import tk.microlms.accessmanager.model.GoogledriveUser;
import tk.microlms.accessmanager.model.TrelloUser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class ReaderService {
    public static TrelloUser readTrelloConf() {
        JSONParser parser = new JSONParser();
        TrelloUser trelloUser = null;
        try {
            Object obj = parser.parse(new FileReader(
                FilePath.CONF.getPath()));

            JSONObject jsonObject = (JSONObject) obj;
            JSONObject tempUser = (JSONObject) jsonObject.get("trelloUser");

            String projectId = tempUser.get("projectId").toString();
            String key = tempUser.get("key").toString();

            trelloUser = TrelloUser
                .builder()
                .projectId(projectId)
                .key(key)
                .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return trelloUser;
    }

    public static List<GitlabUser> readGitLabConf() {
        JSONParser parser = new JSONParser();
        List<GitlabUser> gitlabUserList = new ArrayList<>();

        try {
            Object obj = parser.parse(new FileReader(
                FilePath.CONF.getPath()));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get("gitlabUser");

            int sizeOfArr = jsonArray.size();

            for (int i = 0; i < sizeOfArr; i++) {
                JSONObject tempUser = (JSONObject) jsonArray.get(i);

                String projectId = tempUser.get("projectId").toString();
                String username = tempUser.get("username").toString();
                String pass = tempUser.get("pass").toString();

                GitlabUser gitlabUser = GitlabUser
                    .builder()
                    .projectId(projectId)
                    .username(username)
                    .pass(pass)
                    .build();

                gitlabUserList.add(gitlabUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gitlabUserList;
    }

    public static GoogledriveUser readGoogleDriveConf() {

        JSONParser parser = new JSONParser();
        GoogledriveUser googledriveUser = null;
        try {
            Object obj = parser.parse(new FileReader(
                FilePath.CONF.getPath()));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject tempUser = (JSONObject) jsonObject.get("googledriveUser");
            String fileId = tempUser.get("fileId").toString();

            googledriveUser = GoogledriveUser.builder().fileId(fileId).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return googledriveUser;
    }
}


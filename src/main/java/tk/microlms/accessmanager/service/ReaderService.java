package tk.microlms.accessmanager.service;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tk.microlms.accessmanager.model.GitlabUser;
import tk.microlms.accessmanager.model.GoogledriveUser;
import tk.microlms.accessmanager.model.TrelloUser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class ReaderService {
    private static String directory = String.format("%s/.m2/repository/tk/microlms360/access-manager-tool/configuration.json",System.getProperty("user.home"));

    public static TrelloUser readTrelloConf() {
        JSONParser parser = new JSONParser();
        TrelloUser trelloUser = null;
        try {
            Object obj = parser.parse(new FileReader(
                    directory));
            JSONObject jsonObject = (JSONObject) obj;

            String tempString = jsonObject.get("trelloUser").toString();
            trelloUser = new Gson().fromJson(tempString, TrelloUser.class);

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
                        directory));

                JSONObject jsonObject = (JSONObject) obj;
                JSONArray jsonArray = (JSONArray) jsonObject.get("gitlab");

                int sizeOfArr = jsonArray.size();

                for(int i = 0;i < sizeOfArr;i++){
                    String tempString =  jsonArray.get(i).toString();
                    GitlabUser gitlabUser = new Gson().fromJson(tempString, GitlabUser.class);
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
                        directory));
                JSONObject jsonObject = (JSONObject) obj;

                String tempString = jsonObject.get("googledriveUser").toString();
                googledriveUser = new Gson().fromJson(tempString, GoogledriveUser.class);

            } catch (Exception e) {
                e.printStackTrace();
            }
        return googledriveUser;
    }
}


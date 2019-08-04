package tk.microlms.accessmanager.service;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tk.microlms.accessmanager.model.GitLab;
import tk.microlms.accessmanager.model.GoogleDrive;
import tk.microlms.accessmanager.model.Trello;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class ReaderService {
    private static String directory = String.format("%s/.m2/repository/tk/microlms360/access-manager-tool/configuration.json",System.getProperty("user.home"));

    public static Trello readTrelloConf()  {
        JSONParser parser = new JSONParser();
        Trello trello = null;
        try {
            Object obj = parser.parse(new FileReader(
                    directory));
            JSONObject jsonObject = (JSONObject) obj;

          String tempString =  jsonObject.get("trello").toString();
          trello = new Gson().fromJson(tempString,Trello.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return trello;
    }

    public static List<GitLab> readGitLabConf(){
            JSONParser parser = new JSONParser();
            List<GitLab> gitLabList = new ArrayList<>();

            try {
                Object obj = parser.parse(new FileReader(
                        directory));

                JSONObject jsonObject = (JSONObject) obj;
                JSONArray jsonArray = (JSONArray) jsonObject.get("gitlab");

                int sizeOfArr = jsonArray.size();

                for(int i = 0;i < sizeOfArr;i++){
                    String tempString =  jsonArray.get(i).toString();
                    GitLab  gitLab = new Gson().fromJson(tempString,GitLab.class);
                    gitLabList.add(gitLab);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return gitLabList;
        }

        public static GoogleDrive readGoogleDriveConf(){

            JSONParser parser = new JSONParser();
            GoogleDrive googleDrive = null;
            try {
                Object obj = parser.parse(new FileReader(
                        directory));
                JSONObject jsonObject = (JSONObject) obj;

                String tempString =  jsonObject.get("googleDrive").toString();
                googleDrive = new Gson().fromJson(tempString,GoogleDrive.class);

            } catch (Exception e) {
                e.printStackTrace();
            }return googleDrive;
    }
}


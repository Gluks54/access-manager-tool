package cli_programm;

import cli_programm.model.GitLab;
import cli_programm.model.GoogleDrive;
import cli_programm.model.Trello;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Reader {
    public static Trello readTrelloConf(){
        JSONParser parser = new JSONParser();
        Trello trello = null;
        try {
            Object obj = parser.parse(new FileReader(
                    "src/main/resources/configuration.json"));
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
                        "src/main/resources/configuration.json"));

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
                        "src/main/resources/configuration.json"));
                JSONObject jsonObject = (JSONObject) obj;

                String tempString =  jsonObject.get("googleDrive").toString();
                googleDrive = new Gson().fromJson(tempString,GoogleDrive.class);

            } catch (Exception e) {
                e.printStackTrace();
            }return googleDrive;
    }
}


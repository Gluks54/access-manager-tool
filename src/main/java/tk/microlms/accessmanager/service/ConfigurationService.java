package tk.microlms.accessmanager.service;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tk.microlms.accessmanager.model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class ConfigurationService {
    public void setGoogleDrive(GoogledriveUser user) {
        JSONParser parser = new JSONParser();
        JSONObject newFileId = new JSONObject();

        newFileId.put("fileId", user.getFileId());
        try {
            Object obj = parser.parse(new FileReader(
                FilePathService.CONF));
            JSONObject jsonObject = (JSONObject) obj;
            jsonObject.replace("googledriveUser", newFileId);

            FileWriter file = new FileWriter(FilePathService.CONF);
            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTrello(TrelloUser user) {
        JSONParser parser = new JSONParser();
        JSONObject newUser = new JSONObject();

        newUser.put("projectId", user.getProjectId());
        newUser.put("key", user.getKey());

        try {
            Object obj = parser.parse(new FileReader(
                FilePathService.CONF));
            JSONObject jsonObject = (JSONObject) obj;
            jsonObject.replace("trelloUser", newUser);

            FileWriter file = new FileWriter(FilePathService.CONF);
            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGoogleDriveClient(GoogleDriveClient client) {
        JSONParser parser = new JSONParser();
        JSONObject newClient = new JSONObject();

        newClient.put("client_id", client.getClient_id());
        newClient.put("project_id", client.getProject_id());
        newClient.put("client_secret", client.getClient_secret());
        newClient.put("auth_uri", client.getAuth_uri());
        newClient.put("token_uri", client.getToken_uri());
        newClient.put("auth_provider_x509_cert_url", client.getAuth_provider_x509_cert_url());

        try {
            Object obj = parser.parse(new FileReader(
                FilePathService.CONF));
            JSONObject jsonObject = (JSONObject) obj;
            jsonObject.replace("web", newClient);

            FileWriter file = new FileWriter(FilePathService.CONF);
            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGitLab(List<GitlabUser> ListOfUser) {
        JSONParser parser = new JSONParser();
        JSONArray newArrayOfUser = new JSONArray();

        for (GitlabUser i : ListOfUser) {
            JSONObject newUser = new JSONObject();

            newUser.put("projectId", i.getProjectId());
            newUser.put("username", i.getUsername());
            newUser.put("pass", i.getPass());

            newArrayOfUser.add(newUser);
        }
        try {
            Object obj = parser.parse(new FileReader(
                FilePathService.CONF));
            JSONObject jsonObject = (JSONObject) obj;

            jsonObject.replace("gitlabUser", newArrayOfUser);

            FileWriter file = new FileWriter(FilePathService.CONF);
            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

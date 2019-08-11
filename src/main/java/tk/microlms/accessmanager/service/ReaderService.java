package tk.microlms.accessmanager.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tk.microlms.accessmanager.model.GitlabUser;
import tk.microlms.accessmanager.model.GoogledriveUser;
import tk.microlms.accessmanager.model.TrelloUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ReaderService {
    final static ObjectMapper objectMapper = new ObjectMapper()
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    final static File file = new File(FilePathService.USER_CONF);

    public static TrelloUser readTrelloConf() throws IOException {
        JsonNode jsonNode = objectMapper.readTree(file).get("trelloUser");
        return objectMapper.treeToValue(jsonNode, TrelloUser.class);
    }

    public static List<GitlabUser> readGitLabConf() throws IOException {
        JsonNode jsonNode = objectMapper.readTree(file);
        JsonNode jsonArr = jsonNode.get("gitlabUser");
        List<GitlabUser> gitlabUsers = new ArrayList<>();

        for (int i = 0; i < jsonArr.size(); i++) {
            GitlabUser tempUser = objectMapper.treeToValue(jsonArr.get(i), GitlabUser.class);
            gitlabUsers.add(tempUser);
        }
        return gitlabUsers;
    }

    public static GoogledriveUser readGoogleDriveConf() throws IOException {
        JsonNode jsonNode = objectMapper.readTree(file).get("googledriveUser");
        return objectMapper.treeToValue(jsonNode, GoogledriveUser.class);
    }
}


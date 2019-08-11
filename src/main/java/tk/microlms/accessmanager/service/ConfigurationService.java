package tk.microlms.accessmanager.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tk.microlms.accessmanager.model.GitlabUser;
import tk.microlms.accessmanager.model.GoogleDriveClient;
import tk.microlms.accessmanager.model.GoogledriveUser;
import tk.microlms.accessmanager.model.TrelloUser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigurationService {
    private final File file = new File(FilePathService.USER_CONF);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void newConf(
        TrelloUser trelloUser,
        List<GitlabUser> gitlabUsers,
        GoogledriveUser googledriveUser,
        GoogleDriveClient googleDriveClient) throws IOException {

        JsonNode confNode = objectMapper
            .createObjectNode()
            .putPOJO("web", googleDriveClient)
            .putPOJO("trelloUser", trelloUser)
            .putPOJO("googledriveUser", googledriveUser)
            .putPOJO("gitlabUser", gitlabUsers);

        objectMapper.writeValue(file, confNode);
    }
}

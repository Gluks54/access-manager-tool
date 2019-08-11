package tk.microlms.accessmanager.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tk.microlms.accessmanager.model.TrelloUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrelloEmailService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private File file = new File(FilePathService.TRELLO_EMAIL);

    public void addUsersToFile(TrelloUser trelloUser) throws IOException {
        List<TrelloUser> tempList = reader();
        tempList.add(trelloUser);
        objectMapper.writeValue(file, tempList);
    }

    public String getEmailByUserName(String username) throws IOException {
        for (TrelloUser i : reader()) {
            if (i.getUsername().equals(username)) {
                return i.getEmail();
            }
        }
        return null;
    }

    public String getUserNameByEmail(String email) throws IOException {
        for (TrelloUser i : reader()) {
            if (i.getEmail().equals(email)) {
                return i.getUsername();
            }
        }
        return null;
    }

    public void intializeDoc(TrelloUser trelloUser) throws IOException {
        List<TrelloUser> tempList = new ArrayList<>();
        tempList.add(trelloUser);
        objectMapper.writeValue(file, tempList);

    }

    public void removeByUser(TrelloUser trelloUser) throws IOException {
        List<TrelloUser> tempList = reader();
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getUsername().equals(trelloUser.getUsername())) {
                tempList.remove(i);
            }
        }
        objectMapper.writeValue(file, tempList);
    }

    public List<TrelloUser> reader() throws IOException {
        List<TrelloUser> tempList = new ArrayList<>();
        JsonNode jsonNode = objectMapper.readTree(file);

        for (int i = 0; i < jsonNode.size(); i++) {
            TrelloUser tempTrelloUser = objectMapper
                .treeToValue(jsonNode.get(i), TrelloUser.class);
            tempList.add(tempTrelloUser);
        }
        return tempList;
    }
}

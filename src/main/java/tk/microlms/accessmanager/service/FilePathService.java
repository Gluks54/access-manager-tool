package tk.microlms.accessmanager.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import tk.microlms.accessmanager.model.TrelloUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Getter
public class FilePathService {
    public final static String USER_HOME = System.getProperty("user.home");
    public final static String USER_CONF = "configuration.json";
    public final static String TRELLO_EMAIL = "email-to-username.json";
    private final String homeFilePath = USER_HOME + "/configuration.json";
    private File userDirFile = new File(USER_CONF);
    private File userHomeFile = new File(USER_HOME + "/configuration.json");
    private File trelloEmail = new File(TRELLO_EMAIL);

    public boolean checkConfFile() throws IOException {
        if (userDirFile.exists()) {
            System.out.println("Program use configuration from: " + System.getProperty("user.dir"));
            return true;
        } else {
            if (userHomeFile.exists()) {
                Files.copy(Paths.get(homeFilePath), Paths.get(USER_CONF));
                System.out.println("Configuration file was copied from: '"
                    + System.getProperty("user.home")
                    + "' to '" + System.getProperty("user.dir") + "'");
                return true;
            }
        }
        return false;
    }

    public void checkEmailFile() throws IOException, UnirestException {
        if (!trelloEmail.exists()) {
            TrelloUser trelloConf = ReaderService.readTrelloConf();

            TrelloService trelloService = new TrelloService(
                trelloConf.getKey(),
                trelloConf.getProjectId(),
                trelloConf.getToken());

            TrelloEmailService trelloEmailService = new TrelloEmailService();
            TrelloUser tempUser = trelloService.getMyData();
            trelloEmailService.intializeDoc(tempUser);
            System.out.println("file 'email-to-username.json' was created there: "
                + System.getProperty("user.dir"));
        }
    }
}


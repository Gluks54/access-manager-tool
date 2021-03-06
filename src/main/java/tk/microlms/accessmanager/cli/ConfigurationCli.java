package tk.microlms.accessmanager.cli;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.gitlab4j.api.GitLabApiException;
import tk.microlms.accessmanager.model.GitlabUser;
import tk.microlms.accessmanager.model.GoogleDriveClient;
import tk.microlms.accessmanager.model.GoogledriveUser;
import tk.microlms.accessmanager.model.TrelloUser;
import tk.microlms.accessmanager.service.GitlabService;
import tk.microlms.accessmanager.service.GoogledriveService;
import tk.microlms.accessmanager.service.TrelloService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigurationCli {
    private Scanner scanner = new Scanner(System.in);

    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isURL(String strURL) {
        Pattern pattern = Pattern
            .compile("(https:\\/\\/trello.com\\/b)");
        Matcher matcher;
        matcher = pattern.matcher(strURL);

        return matcher.find();
    }

    public boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public TrelloUser newTrelloConf() throws UnirestException {
        System.out.println("\n\nTrello settings... \n'key' and 'token' - you can get by next steps:\n" +
            "You should visit next page 'https://developers.trello.com/page/authorization' and press\n" +
            "on the link 'https://trelloUser.com/app-key' copy 'key' after that press again on the link 'Token' " +
            "and copy your token...\n\n'key: '");
        String key = scanner.next();

        System.out.println("\n'token: '");
        String token = scanner.next();

        System.out.println("\nPlease input a board URL (e.g. https://trello.com/b/xYzAaAbc) or " +
            "\nenter an index of your board (e.g. 1) from a list, here is the list of your boards:\n");

        TrelloService trelloService = new TrelloService(key, token);

        trelloService
            .getMyData()
            .getMapIndexAndBoards()
            .forEach((x, y) -> System.out.println("Index: " + x + "." + " Name: " + y));

        String tempString = scanner.next();
        String projectId;

        if (isURL(tempString)) {
            projectId = trelloService
                .getProjectIdByURL(tempString);
        } else {
            while (true) {
                tempString = scanner.next();

                if (isNumeric(tempString)) {
                    projectId = trelloService
                        .getMyData()
                        .getMapIndexAndProjectId()
                        .get(tempString);
                    break;
                } else {
                    System.out.println("Please enter the number...");
                }
            }
        }
        return TrelloUser
            .builder()
            .key(key)
            .projectId(projectId)
            .token(token)
            .build();
    }

    public GoogledriveUser newGoogleDriveConf() throws Exception {
        System.out.println("\n\nGoogleDrive settings..." +
            "\nEnter an index of your file (e.g. 1) " +
            "from a list, here is the list of your files:\n");

        GoogledriveService googledriveService = new GoogledriveService();
        googledriveService.getListOfFiles();

        System.out.println("\n'index: '");
        String fileId;

        while (true) {
            String tempString = scanner.next();

            if (isNumeric(tempString)) {
                fileId = googledriveService
                    .getFileIdByIndex(Integer.valueOf(tempString));
                break;
            } else {
                System.out.println("Please enter the number...");
            }

        }
        return GoogledriveUser
            .builder()
            .fileId(fileId)
            .build();
    }

    public GoogleDriveClient newGoogleDriveClientConf() {
        System.out.println("\nGoogleDrive client configurations...\nYou can download all credentials for your GoogleDrive client there:\n" +
            "'https://console.developers.google.com/apis/api/drive/overview'\n" +
            "But first of all you should create your own client.More information you can find there:'https://developers.google.com/drive'\n" +
            "Attention!!!! Your 'Authorised redirect URIs' must be 'http://localhost:8888/Callback';" +
            "\n\n'client_id: '");

        String client_id = scanner.next();

        System.out.println("\n'project_id: '");
        String project_id = scanner.next();

        System.out.println("\n'client_secret: '");
        String client_secret = scanner.next();

        System.out.println("\n'auth_uri: '");
        String auth_uri = scanner.next();

        System.out.println("\n'token_uri: '");
        String token_uri = scanner.next();

        System.out.println("\n'auth_provider_x509_cert_url: '");
        String auth_provider_x509_cert_url = scanner.next();

        return GoogleDriveClient
            .builder()
            .client_id(client_id)
            .project_id(project_id)
            .client_secret(client_secret)
            .auth_uri(auth_uri)
            .token_uri(token_uri)
            .auth_provider_x509_cert_url(auth_provider_x509_cert_url)
            .build();
    }

    public List<GitlabUser> newGitLabConf() throws GitLabApiException {
        System.out.println("GitLab settings...\n'userName'(your userName),'pass'(your password) - all of that you can find in your GitLab page;\n" +
            "\n\nEnter number of project which you want to share:");

        List<GitlabUser> gitlabUsers = new ArrayList<>();


        while (true) {
            String tempString = scanner.next();
            if (isNumeric(tempString)) {
                int numberOfProjects = Integer.valueOf(tempString);

                for (int i = 0; i < numberOfProjects; i++) {
                    System.out.println("\n'username: '");
                    String username = scanner.next();

                    System.out.println("\n'pass: '");
                    String pass = scanner.next();

                    GitlabService gitlabService = new GitlabService(username, pass);

                    System.out.println("\nIteration: " + (i + 1) + "\n" +
                        "\nEnter an index of your project (e.g. 1) from a list, here is the list of your projects:\n");

                    gitlabService.getGitLabProjects();
                    String projectId;

                    while (true) {
                        tempString = scanner.next();

                        if (isNumeric(tempString)) {
                            projectId = gitlabService
                                .getProjectIdByIndex(Integer.valueOf(tempString));
                            break;
                        } else {
                            System.out.println("Please enter the number...");
                        }
                    }

                    GitlabUser gitlabUser = GitlabUser
                        .builder()
                        .username(username)
                        .projectId(projectId)
                        .pass(pass)
                        .build();

                    gitlabUsers.add(gitlabUser);
                }
                break;
            } else {
                System.out.println("Please enter the number...");
            }
        }
        return gitlabUsers;
    }
}


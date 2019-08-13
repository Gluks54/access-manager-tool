package tk.microlms.accessmanager.cli;

import tk.microlms.accessmanager.model.GitlabUser;
import tk.microlms.accessmanager.model.GoogleDriveClient;
import tk.microlms.accessmanager.model.GoogledriveUser;
import tk.microlms.accessmanager.model.TrelloUser;
import tk.microlms.accessmanager.service.ConfigurationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigurationCli {
    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

    public void newConfigurations() throws IOException {
        Scanner scanner = new Scanner(System.in);
        ConfigurationService configurationService = new ConfigurationService();

        System.out.println("Trello settings... \n'projectId','key' and 'token' - you can get by next steps:\n" +
            "You should visit next page 'https://developers.trello.com/page/authorization' and press\n" +
            "on the link 'https://trelloUser.com/app-key' copy 'key' after that press again on the link 'Token' " +
            "and copy your token...\n" +
            "'projectId'(or id of the Board) you can get by 'Get' request:\n" +
            "'https://api.trelloUser.com/1/members/{usrName}?key={your key}&token={your token}'\n\nimpute 'key':");

        String key = scanner.next();
        System.out.println("impute 'projectId':");
        String projectId = scanner.next();

        System.out.println("impute 'token':");
        String token = scanner.next();

        TrelloUser trelloUser = TrelloUser
            .builder()
            .key(key)
            .projectId(projectId)
            .token(token)
            .build();

        System.out.println("\n\nfileId - that is Id of the folder which should be share.\n" +
            "'https://developers.google.com/drive/api/v3/reference/files/list' - just use that page for getting list of 'id' your files\n" +
            "(you don't should impute any param for request);");

        System.out.println("\nimpute 'fileId:");
        String fileId = scanner.next();

        GoogledriveUser googledriveUser = GoogledriveUser
            .builder()
            .fileId(fileId)
            .build();

        System.out.println("\n'projectId'(your projectId),'userName'(your userName),'pass'(your password) - all of that you can find in your GitLab page;\n" +
            "\n\nimpute number of project which you want to share:");

        List<GitlabUser> gitlabUsers = new ArrayList<>();

        while (true) {
            String tempString = scanner.next();
            if (isNumeric(tempString)) {
                int numberOfProjects = Integer.valueOf(tempString);

                for (int i = 0; i < numberOfProjects; i++) {

                    System.out.println("\nIteration: " + (i + 1) + "\nimpute 'projectId':");
                    String projId = scanner.next();

                    System.out.println("\nimpute 'username':");
                    String username = scanner.next();

                    System.out.println("\nimpute 'pass':");
                    String pass = scanner.next();

                    GitlabUser gitlabUser = GitlabUser
                        .builder()
                        .username(username)
                        .projectId(projId)
                        .pass(pass)
                        .build();

                    gitlabUsers.add(gitlabUser);
                }
                break;
            } else {
                System.out.println("Please impute the number...");
            }
        }

        System.out.println("\nYou can download all credentials for your GoogleDrive client there:\n" +
            "'https://console.developers.google.com/apis/api/drive/overview'\n" +
            "But first of all you should create your own client.More information you can find there:'https://developers.google.com/drive'\n" +
            "Attention!!!! Your 'Authorised redirect URIs' must be 'http://localhost:8888/Callback';" +
            "\n\nimpute 'client_id':");

        String client_id = scanner.next();

        System.out.println("\nimpute 'project_id':");
        String project_id = scanner.next();

        System.out.println("\nimpute 'client_secret':");
        String client_secret = scanner.next();

        System.out.println("\nimpute 'auth_uri':");
        String auth_uri = scanner.next();

        System.out.println("impute 'token_uri':");
        String token_uri = scanner.next();

        System.out.println("impute 'auth_provider_x509_cert_url':");
        String auth_provider_x509_cert_url = scanner.next();

        GoogleDriveClient googleDriveClient = GoogleDriveClient
            .builder()
            .client_id(client_id)
            .project_id(project_id)
            .client_secret(client_secret)
            .auth_uri(auth_uri)
            .token_uri(token_uri)
            .auth_provider_x509_cert_url(auth_provider_x509_cert_url)
            .build();

        configurationService
            .newConf(trelloUser,
                gitlabUsers,
                googledriveUser,
                googleDriveClient);
    }
}


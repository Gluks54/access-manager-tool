package tk.microlms.accessmanager.cli;

import tk.microlms.accessmanager.model.GitlabUser;
import tk.microlms.accessmanager.model.GoogleDriveClient;
import tk.microlms.accessmanager.model.GoogledriveUser;
import tk.microlms.accessmanager.model.TrelloUser;
import tk.microlms.accessmanager.service.ConfigurationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigurationCli {
    public void UserConfigurations() {
        Scanner scanner = new Scanner(System.in);
        ConfigurationService configurationService = new ConfigurationService();

        System.out.println("Trello settings... \nYou can check 'key' there: https://trelloUser.com/app-key,\n" +
            "'projectId'(or id of the Board) you can get by Get request:\n" +
            "https://api.trelloUser.com/1/members/{usrName}?key={your key}\n\nimpute 'key':");

        String key = scanner.next();
        System.out.println("impute 'projectId':");
        String projectId = scanner.next();

        TrelloUser trelloUser = TrelloUser
            .builder()
            .key(key)
            .projectId(projectId)
            .build();

        configurationService.setTrello(trelloUser);

        System.out.println("\n\nGoogleDrive settings.... \nfileId - that is Id of the folder which should be share\n" +
            "https://developers.google.com/drive/api/v3/reference/files/list - for getting list of id your files");

        System.out.println("\nimpute 'fileId:");
        String fileId = scanner.next();

        GoogledriveUser googledriveUser = GoogledriveUser
            .builder()
            .fileId(fileId)
            .build();

        configurationService.setGoogleDrive(googledriveUser);

        System.out.println("\nGitLab settings..." +
            "\nprojectId,userName,pass(your password) - all of that you can find in your GitLab page;" +
            "\n\nimpute number of project which you want to share:");

        int numberOfProjects = scanner.nextInt();
        List<GitlabUser> userList = new ArrayList<>();

        for (int i = 0; i < numberOfProjects; i++) {

            System.out.println("\nYour iteration number: " + (i + 1) + "\nimpute 'projectId':");
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

            userList.add(gitlabUser);
        }
        configurationService.setGitLab(userList);
    }

    public void ClientCredentialsConfig() {
        Scanner scanner = new Scanner(System.in);
        ConfigurationService configurationService = new ConfigurationService();

        System.out.println("\nYou can download all credentials for your GoogleDrive client there:\nhttps://console.developers.google.com/apis/api/drive/overview" +
            "\nBut first of all you should create own client.More information you can find there:https://developers.google.com/drive" +
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

        configurationService.setGoogleDriveClient(googleDriveClient);
    }
}


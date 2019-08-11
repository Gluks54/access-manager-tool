package tk.microlms.accessmanager.cli;

import tk.microlms.accessmanager.model.GitlabRolePermission;
import tk.microlms.accessmanager.model.GitlabUser;
import tk.microlms.accessmanager.model.TrelloUser;
import tk.microlms.accessmanager.service.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        final int EXIT_STATUS = 0;
        Scanner scanner = new Scanner(System.in);
        FilePathService filePathService = new FilePathService();

        if (!filePathService.checkConfFile()) {
            String errorMassage = String.format("configuration.json is found neither in the current directory (%s)" +
                "\nnor in home dir (%s), would you like to create a new configuration y/n?", FilePathService.USER_CONF, FilePathService.USER_HOME);
            System.out.println(errorMassage);

            String result = scanner.next();
            if (result.equals("y")) {
                ConfigurationCli configurationCli = new ConfigurationCli();
                configurationCli.newConfigurations();
            } else {
                System.exit(EXIT_STATUS);
            }
        }

        filePathService.checkEmailFile();

        TrelloUser trelloUser = ReaderService.readTrelloConf();
        String googleFileId = ReaderService.readGoogleDriveConf().getFileId();
        TrelloService trelloService = new TrelloService(
            trelloUser.getKey(),
            trelloUser.getProjectId(),
            trelloUser.getToken());
        GoogledriveService googleDriveService = new GoogledriveService(googleFileId);

        System.out.println(
            "Add to GitlabUser repositories,GoogledriveUser directory,TrelloUser board impute 'a'" +
                "\nGet status impute 's' " +
                "\nQuit the program impute 'q'" + "\nDelete a member impute'd'");

        while (true) {
            String result = scanner.next();
            if (result.equals("q")) {
                break;
            }

            if (result.equals("a")) {
                System.out.println("impute email:");
                String email = scanner.next();

                trelloService.addToTrello(email);
                googleDriveService.addToGoogleDrive(email);

                List<GitlabUser> gitlabUsers = ReaderService.readGitLabConf();
                for (int i = 0; i < gitlabUsers.size(); i++) {

                    String tempUserName = ReaderService.readGitLabConf().get(i).getUsername();
                    String tempPass = ReaderService.readGitLabConf().get(i).getPass();
                    String tempProjectId = ReaderService.readGitLabConf().get(i).getProjectId();

                    GitlabService gitLabService = new GitlabService(tempUserName, tempPass);
                    gitLabService.addToGitLab(Integer.valueOf(tempProjectId), email,
                        GitlabRolePermission.DEVELOPER.getLevel());
                }
                System.out.println("add to GitLab");
            }
            if (result.equals("d")) {
                System.out.println("impute email:");
                String email = scanner.next();

                trelloService.deleteMember(email);
                System.out.println("delete from trello");

                googleDriveService.delete(email);
                System.out.println("delete from GoogleDrive");

                List<GitlabUser> gitlabUsers = ReaderService.readGitLabConf();

                for (int i = 0; i < gitlabUsers.size(); i++) {
                    String tempUserName = ReaderService.readGitLabConf().get(i).getUsername();
                    String tempPass = ReaderService.readGitLabConf().get(i).getPass();
                    String tempProjectId = ReaderService.readGitLabConf().get(i).getProjectId();

                    GitlabService gitLabService = new GitlabService(tempUserName, tempPass);
                    gitLabService.delete(Integer.valueOf(tempProjectId), email);
                }
                System.out.println("delete from GitLab");
            }

            if (result.equals("s")) {
                System.out.println("TrelloUser status:");
                trelloService.getStatus();

                System.out.println("\nGoogledriveUser status:");
                googleDriveService.getStatus();

                System.out.println("\nGitlabUser status:");

                List<GitlabUser> gitlabUsers = ReaderService.readGitLabConf();
                for (int i = 0; i < gitlabUsers.size(); i++) {

                    String tempUserName = ReaderService.readGitLabConf().get(i).getUsername();
                    String tempPass = ReaderService.readGitLabConf().get(i).getPass();
                    String tempProjectId = ReaderService.readGitLabConf().get(i).getProjectId();

                    GitlabService gitLabService = new GitlabService(tempUserName, tempPass);
                    System.out.println("\nProjectId:" + tempProjectId);
                    gitLabService.getStatus(tempProjectId);
                }
            }
        }
    }
}




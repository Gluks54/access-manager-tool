package tk.microlms.accessmanager.cli;

import tk.microlms.accessmanager.model.*;
import tk.microlms.accessmanager.service.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        ConfigurationService configurationService = new ConfigurationService();
        FilePathService filePathService = new FilePathService();
        ConfigurationCli configurationCli = new ConfigurationCli();
        Scanner scanner = new Scanner(System.in);
        final int EXIT_STATUS = 0;

        if (!filePathService.checkConfFile()) {
            String errorMassage = String.format("File 'configuration.json' is found neither in the current directory (%s)" +
                "\nnor in home dir (%s), would you like to create a new configuration y/n?", FilePathService.USER_DIR, FilePathService.USER_HOME);
            System.out.println(errorMassage);

            while (true) {
                String result = scanner.next();
                result = result.toLowerCase();

                if (result.equals("y")) {

                    GoogleDriveClient googleDriveClientConf = configurationCli.newGoogleDriveClientConf();
                    configurationService.saveGoogleDriveClientToFile(googleDriveClientConf);
                    GoogledriveUser googledriveUserConf = configurationCli.newGoogleDriveConf();
                    List<GitlabUser> gitlabUsersConf = configurationCli.newGitLabConf();
                    TrelloUser trelloUserConf = configurationCli.newTrelloConf();

                    configurationService
                        .newConf(trelloUserConf,
                            gitlabUsersConf,
                            googledriveUserConf,
                            googleDriveClientConf);
                    break;
                }
                if (result.equals("n")) {
                    System.exit(EXIT_STATUS);
                }
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
            "\nAdd to GitlabUser repositories,GoogledriveUser directory,TrelloUser board enter 'a'" +
                "\nGet status enter 's' " +
                "\nQuit the program enter 'q'" + "\nDelete a member enter 'd'");

        while (true) {
            String result = scanner.next();
            result = result.toLowerCase();

            if (result.equals("q")) {
                break;
            }

            if (result.equals("a")) {
                while (true) {
                    System.out.println("Enter email:");
                    String email = scanner.next();
                    if (configurationCli.isValid(email)) {
                        email = email.toLowerCase();

                        trelloService.addToTrello(email);
                        System.out.println("add to Trello");

                        googleDriveService.addToGoogleDrive(email);
                        System.out.println("add to GoogleDrive");

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
                        break;
                    } else {
                        System.out.println("Email is not valid...\n");
                    }
                }
            }
            if (result.equals("d")) {
                while (true) {
                    System.out.println("Enter email:");
                    String email = scanner.next();

                    if (configurationCli.isValid(email)) {
                        email = email.toLowerCase();

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
                        break;
                    } else {
                        System.out.println("Email is not valid...\n");
                    }
                }
            }

            if (result.equals("s")) {
                System.out.println("TrelloUser status:");
                trelloService.getStatus();

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

                System.out.println("\nGoogledriveUser status:");
                googleDriveService.getStatus();
            }
        }
    }
}





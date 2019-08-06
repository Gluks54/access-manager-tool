package tk.microlms.accessmanager.cli;

import tk.microlms.accessmanager.model.FilePath;
import tk.microlms.accessmanager.model.GitlabRolePermission;
import tk.microlms.accessmanager.model.GitlabUser;
import tk.microlms.accessmanager.service.GitlabService;
import tk.microlms.accessmanager.service.GoogledriveService;
import tk.microlms.accessmanager.service.ReaderService;
import tk.microlms.accessmanager.service.TrelloService;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File(FilePath.CONF.getPath());
        if (file.exists() && file.isFile()) {
        } else {
            Scanner scanner = new Scanner(System.in);

            ConfigurationCli configurationCli = new ConfigurationCli();
            configurationCli.copyFile(new File(FilePath.DEFAULT_CONF.getPath()),
                new File(FilePath.CONF.getPath()));

            System.out.println("File configuration.json doesn't exist...\nUse default settings(y/n)?");

            String rezult = scanner.next();
            while (true) {
                if (rezult.equals("y")) {
                    break;
                }
                if (rezult.equals("n")) {
                    configurationCli.UserConfigurations();
                    configurationCli.ClientCredentialsConfig();
                    break;
                }
            }
        }

        String trelloKey = ReaderService.readTrelloConf().getKey();
        String trelloProjectId = ReaderService.readTrelloConf().getProjectId();
        String googleFileId = ReaderService.readGoogleDriveConf().getFileId();

        TrelloService trelloService = new TrelloService(trelloKey, trelloProjectId);
        GoogledriveService googleDriveService = new GoogledriveService(googleFileId);
        Scanner scanner = new Scanner(System.in);

        System.out.println(
            "Add to GitlabUser repositories,GoogledriveUser directory,TrelloUser board impute 'a'" +
                "\nGet status impute 's' " +
                "\nQuit the program impute 'q'" + "\nDelete a member impute'd'" +
                "\nSet GoogleDrive,Gitlab and Trello profile impute 'set'");

        while (true) {
            String result = scanner.next();

            if (result.equals("q")) {
                break;
            }

            if (result.equals("a")) {
                System.out.println("Write your email for TrelloUser:");
                String trelloEmail = scanner.next();

                trelloService.addToTrello(trelloEmail);

                System.out.println("TrelloUser status:");
                trelloService.getStatus();

                System.out.println("Write your email for gitlab");
                String email = scanner.next();

                List<GitlabUser> gitlabUsers = ReaderService.readGitLabConf();
                for (int i = 0; i < gitlabUsers.size(); i++) {

                    String tempUserName = ReaderService.readGitLabConf().get(i).getUsername();
                    String tempPass = ReaderService.readGitLabConf().get(i).getPass();
                    String tempProjectId = ReaderService.readGitLabConf().get(i).getProjectId();

                    GitlabService gitLabService = new GitlabService(tempUserName, tempPass);
                    gitLabService.addToGitLab(Integer.valueOf(tempProjectId), email, GitlabRolePermission.DEVELOPER.getLevel());

                    System.out.println("\nProjectId:" + tempProjectId);
                    gitLabService.getStatus(tempProjectId);

                }

                System.out.println("\nWrite your email for google");
                String googleEmail = scanner.next();

                googleDriveService.addToGoogleDrive(googleEmail);

                System.out.println("\nGoogledriveUser status:");
                googleDriveService.getStatus();
            }
            if (result.equals("d")) {
                System.out.println("delete from TrelloUser impute 'dtrell' " +
                    "\ndelete from GoogledriveUser impute 'dgoogl' " +
                    "\ndelete from GitlabUser inpute 'dGit'");
            }

            if (result.equals("dtrell")) {
                System.out.println("Write UserName:");
                String userName = scanner.next();
                trelloService.deleteMember(userName);
                trelloService.getStatus();
            }

            if (result.equals("dgoogl")) {
                System.out.println("Write userId:");
                String userId = scanner.next();
                googleDriveService.delete(userId);
                googleDriveService.getStatus();
            }
            if (result.equals("dGit")) {
                System.out.println("Write email:");
                String email = scanner.next();

                List<GitlabUser> gitlabUsers = ReaderService.readGitLabConf();
                for (int i = 0; i < gitlabUsers.size(); i++) {

                    String tempUserName = ReaderService.readGitLabConf().get(i).getUsername();
                    String tempPass = ReaderService.readGitLabConf().get(i).getPass();
                    String tempProjectId = ReaderService.readGitLabConf().get(i).getProjectId();

                    GitlabService gitLabService = new GitlabService(tempUserName, tempPass);
                    gitLabService.delete(Integer.valueOf(tempProjectId), email);

                    System.out.println("\nProjectId:" + tempProjectId);
                    gitLabService.getStatus(tempProjectId);
                }
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

            if (result.equals("set")) {
                ConfigurationCli configurationCli = new ConfigurationCli();
                configurationCli.UserConfigurations();
            }
        }
    }
}




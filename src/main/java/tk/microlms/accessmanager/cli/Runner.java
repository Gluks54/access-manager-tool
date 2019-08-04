package tk.microlms.accessmanager.cli;

import tk.microlms.accessmanager.model.GitLab;
import tk.microlms.accessmanager.service.GitLabService;
import tk.microlms.accessmanager.service.GoogleDriveService;
import tk.microlms.accessmanager.service.ReaderService;
import tk.microlms.accessmanager.service.TrelloService;

import java.util.List;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) throws Exception {

        int accessLevelGitLab = 30;
        TrelloService trelloService = new TrelloService(ReaderService.readTrelloConf().getKey(), ReaderService.readTrelloConf().getProjectId());
        GoogleDriveService googleDriveService = new GoogleDriveService(ReaderService.readGoogleDriveConf().getFileId());
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "Add to GitLab repositories,GoogleDrive directory,Trello board impute 'a'" +
                        "\nGet status impute 's' "+
                        "\nQuit the program impute 'q'"+"\ndelete a member impute'd'");

        while (true){
            String  result  = scanner.next();

            if(result.equals("q")){break;}

            if (result.equals("a")) {
                System.out.println("Write your email for Trello:");
                String trelloEmail = scanner.next();

                trelloService.addToTrello(trelloEmail);

                System.out.println("Trello status:");
                trelloService.getStatus();

                System.out.println("Write your email for gitlab");
                String email = scanner.next();

                List<GitLab> gitLabs = ReaderService.readGitLabConf();
                for (int i = 0;i < gitLabs.size();i++){

                    String tempUserName = ReaderService.readGitLabConf().get(i).getUserName();
                    String tempPass = ReaderService.readGitLabConf().get(i).getPass();
                    String tempProjectId = ReaderService.readGitLabConf().get(i).getProjectId();

                    GitLabService gitLabService = new GitLabService(tempUserName,tempPass);
                    gitLabService.addToGitLab(Integer.valueOf(tempProjectId),email,accessLevelGitLab);

                    System.out.println("\nProjectId:" + tempProjectId);
                    gitLabService.getStatus(tempProjectId);

                }

                System.out.println("\nWrite your email for google");
                String googleEmail = scanner.next();

                googleDriveService.addToGoogleDrive(googleEmail);

                System.out.println("\nGoogleDrive status:");
                googleDriveService.getStatus();
            }
            if(result.equals("d")){
                    System.out.println("delete from Trello impute 'dtrell' " +
                            "\ndelete from GoogleDrive impute 'dgoogl' " +
                            "\ndelete from GitLab inpute 'dGit'");
            }

            if(result.equals("dtrell")){
                System.out.println("Write UserName:");
                String userName = scanner.next();
                trelloService.deleteMember(userName);
                trelloService.getStatus();
            }

            if(result.equals("dgoogl")){
                System.out.println("Write userId:");
                String userId = scanner.next();
                googleDriveService.delete(userId);
                googleDriveService.getStatus();
            }
            if(result.equals("dGit")){
                System.out.println("Write email:");
                String email = scanner.next();

                List<GitLab> gitLabs = ReaderService.readGitLabConf();
                for (int i = 0;i < gitLabs.size();i++){

                    String tempUserName = ReaderService.readGitLabConf().get(i).getUserName();
                    String tempPass = ReaderService.readGitLabConf().get(i).getPass();
                    String tempProjectId = ReaderService.readGitLabConf().get(i).getProjectId();

                    GitLabService gitLabService = new GitLabService(tempUserName,tempPass);
                    gitLabService.delete(Integer.valueOf(tempProjectId),email);

                    System.out.println("\nProjectId:" + tempProjectId);
                    gitLabService.getStatus(tempProjectId);
                }
            }

            if(result.equals("s")){

                System.out.println("Trello status:");
                trelloService.getStatus();

                System.out.println("\nGoogleDrive status:");
                googleDriveService.getStatus();

                System.out.println("\nGitLab status:");

                List<GitLab> gitLabs = ReaderService.readGitLabConf();
                for (int i = 0;i < gitLabs.size();i++){

                    String tempUserName = ReaderService.readGitLabConf().get(i).getUserName();
                    String tempPass = ReaderService.readGitLabConf().get(i).getPass();
                    String tempProjectId = ReaderService.readGitLabConf().get(i).getProjectId();

                    GitLabService gitLabService = new GitLabService(tempUserName,tempPass);
                    System.out.println("\nProjectId:" + tempProjectId);
                    gitLabService.getStatus(tempProjectId);

                }
            }
        }
    }
}




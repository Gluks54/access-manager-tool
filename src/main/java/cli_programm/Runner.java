package cli_programm;

import addGoogleDrive.GoogleDriveService;
import addToGitLab.GitLabService;
import addToTrello.TrelloService;
import cli_programm.model.GitLab;
import java.util.List;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) throws Exception {

        int accessLevelGitLab = 30;
        TrelloService trelloService = new TrelloService(Reader.readTrelloConf().getKey(),Reader.readTrelloConf().getProjectId());
        GoogleDriveService googleDriveService = new GoogleDriveService(Reader.readGoogleDriveConf().getFileId());
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "Add to GitLab repositories,GoogleDrive directory,Trello board impute 'a'" +
                        "\nGet status impute 's' "+
                        "\nQuit the program impute 'q'");

        while (true){
            String  result  = scanner.next();

            if(result.equals("q")){break;}

            if (result.equals("a")) {
                System.out.println("Write your email for Trello:");
                String trelloEmail = scanner.next();
                trelloService.addToTrello(trelloEmail);

                System.out.println("Trello status:");
                trelloService.getStatus();

                System.out.println("Write your email for google");

                String googleEmail = scanner.next();
                googleDriveService.addToGoogleDrive(googleEmail);

                System.out.println("\nGoogleDrive status:");
                googleDriveService.getStatus();

                System.out.println("Write your email for gitlab");
                String email = scanner.next();

                List<GitLab> gitLabs = Reader.readGitLabConf();
                for (int i = 0;i < gitLabs.size();i++){

                    String tempUserName = Reader.readGitLabConf().get(i).getUserName();
                    String tempPass = Reader.readGitLabConf().get(i).getPass();
                    String tempProjectId = Reader.readGitLabConf().get(i).getProjectId();

                    GitLabService gitLabService = new GitLabService(tempUserName,tempPass);
                    gitLabService.addToGitLab(Integer.valueOf(tempProjectId),email,accessLevelGitLab);

                    System.out.println("\nProjectId:" + tempProjectId);
                    gitLabService.getStatus(tempProjectId);

                }
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
                System.out.println("Write email:");
                String email = scanner.next();
                googleDriveService.delete(email);
                googleDriveService.getStatus();
            }
            if(result.equals("dGit")){
                System.out.println("Write email:");
                String email = scanner.next();

                List<GitLab> gitLabs = Reader.readGitLabConf();
                for (int i = 0;i < gitLabs.size();i++){

                    String tempUserName = Reader.readGitLabConf().get(i).getUserName();
                    String tempPass = Reader.readGitLabConf().get(i).getPass();
                    String tempProjectId = Reader.readGitLabConf().get(i).getProjectId();

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

                List<GitLab> gitLabs = Reader.readGitLabConf();
                for (int i = 0;i < gitLabs.size();i++){

                    String tempUserName = Reader.readGitLabConf().get(i).getUserName();
                    String tempPass = Reader.readGitLabConf().get(i).getPass();
                    String tempProjectId = Reader.readGitLabConf().get(i).getProjectId();

                    GitLabService gitLabService = new GitLabService(tempUserName,tempPass);
                    System.out.println("\nProjectId:" + tempProjectId);
                    gitLabService.getStatus(tempProjectId);

                }
            }
        }
    }
}




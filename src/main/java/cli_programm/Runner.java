package cli_programm;

import addGoogleDrive.GoogleDriveService;
import addToGitLab.GitLabService;
import addToTrello.TrelloService;
import cli_programm.model.GitLab;
import cli_programm.model.GoogleDrive;
import cli_programm.model.Trello;
import java.util.List;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) throws Exception {

        TrelloService trelloService = new TrelloService();
        GitLabService gitLabService = new GitLabService();
        GoogleDriveService googleDriveService = new GoogleDriveService();
        Reader reader = new Reader();
        Scanner scanner = new Scanner(System.in);

        Trello trelloConf = reader.readTrelloConf();
        System.out.println(reader.readTrelloConf().toString());

        List<GitLab> gitLabListConf = reader.readGitLabConf();
        System.out.println(gitLabListConf);

        GoogleDrive googleDrive = reader.readGoogleDriveConf();
        System.out.println(googleDrive.toString());

        System.out.println(
                "If you want to be added to GitLab repositories,GoogleDrive directory,Trello board impute 'a'" +
                        "\nIf you want to quit program impute 'q'");

        while (true){
            String  result  = scanner.next();


            if(result.equals("q")){break;}

            if (result.equals("a")) {
                System.out.println("Write your email for Trello:");
                String trelloEmail = scanner.next();
                trelloService.addToTrello(trelloConf.getProjectId(),trelloEmail,trelloConf.getKey());

                System.out.println("Write your user_id for gitlab");

                String user_id = scanner.next();
                int sizeOfGitLabList = gitLabListConf.size();

                for (int i = 0;i < sizeOfGitLabList;i++){
                    gitLabService.addToGitLab(
                            gitLabListConf.get(i).getProjectId(),
                            gitLabListConf.get(i).getPrivateToken(),
                            user_id);
                }

                System.out.println("Write your email for google");

                String googleEmail = scanner.next();
                googleDriveService.addToGoogleDrive(googleDrive.getFileId(),googleEmail);

            }

        }

    }

}


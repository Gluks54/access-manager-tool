package addToGitLab;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.gitlab4j.api.GitLabApiException;

public class Runner {
    public static void main(String[] args) throws GitLabApiException {

        int projectId = 13343892;
        String userName = "Gluks54";
        String pass = "Jk8293196";
        String email ="offiseword@mail.ru";

        GitLabService gitLabService = new GitLabService(userName,pass);
        System.out.println("initiate state");
        gitLabService.getStatus(String.valueOf(projectId));
        System.out.println("-----------");

        System.out.println("add memeber");
        gitLabService.addToGitLab(projectId,email,30);
        gitLabService.getStatus(String.valueOf(projectId));
        System.out.println("-------------");

        System.out.println("delete memder");
        gitLabService.delete(projectId,email);
        gitLabService.getStatus(String.valueOf(projectId));
    }
}

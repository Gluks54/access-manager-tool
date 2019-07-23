package addToGitLab;

import com.mashape.unirest.http.exceptions.UnirestException;

public class Runner {
    public static void main(String[] args) throws UnirestException {
        String projectId = "13381233";
        String privateToken = "uX34zMyF7UbeEge5xsz8";
        String user_id = "4311345";

        GitLabService gitLabService = new GitLabService();
        gitLabService.addToGitLab(projectId,privateToken,user_id);
    }
}

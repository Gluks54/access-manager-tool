package tk.microlms.accessmanager.service;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.ProjectUser;

import java.util.List;

public class GitlabService {

    public GitlabService(String userName, String pass) throws GitLabApiException {
        this.userName = userName;
        this.pass = pass;
        gitLabApi = getCredential(userName,pass);
    }

   private  String userName;
   private  String pass;
   private  GitLabApi gitLabApi = null;

    public GitLabApi getCredential(String userName,String pass) throws GitLabApiException {
        return GitLabApi.oauth2Login("https://gitlab.com", userName,pass);
    }

    public void addToGitLab(int projectId,String email,int accessLevel) throws GitLabApiException {
        gitLabApi.getProjectApi().addMember(projectId,getUserByEmail(email),accessLevel);
    }

    public void getStatus(String projectId) throws  GitLabApiException {
        List<ProjectUser> projectPager =  gitLabApi
                .getProjectApi()
                .getProjectUsers(projectId);

        for (ProjectUser i:projectPager) {
            System.out.println("name: " + i.getName()
            + " username: " + i.getUsername() + " id: " + i.getId() + " email: " + i.getEmail());
        }
    }
    public void delete(int projectId,String email) throws GitLabApiException {
        gitLabApi.getProjectApi().removeMember(projectId,getUserByEmail(email));
    }

    public Integer getUserByEmail(String email) throws GitLabApiException {
       return gitLabApi.getUserApi().getUserByEmail(email).getId();
    }
}

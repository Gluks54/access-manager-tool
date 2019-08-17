package tk.microlms.accessmanager.service;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.Project;
import tk.microlms.accessmanager.model.GitlabRolePermission;

import java.util.List;

public class GitlabService {
    private String userName;
    private String pass;
    private GitLabApi gitLabApi;

    public GitlabService(String userName, String pass) throws GitLabApiException {
        this.userName = userName;
        this.pass = pass;
        gitLabApi = getCredential(userName, pass);
    }

    public GitLabApi getCredential(String userName, String pass) throws GitLabApiException {
        return GitLabApi.oauth2Login("https://gitlab.com", userName, pass);
    }

    public void addToGitLab(int projectId, String email, int accessLevel) throws GitLabApiException {
        gitLabApi.getProjectApi().addMember(projectId, getUserByEmail(email), accessLevel);
    }

    public void getStatus(String projectId) throws GitLabApiException {
        List<Member> projectPager = gitLabApi
            .getProjectApi()
            .getAllMembers(projectId);

        for (Member i : projectPager) {
            System.out.println("FullName: " + i.getName()
                + " Username: " + i.getUsername()
                + " Status: " + getStatusByAccessLevel(i.getAccessLevel().value)
                + " Email: " + i.getEmail());
        }
    }

    public Enum getStatusByAccessLevel(Integer accessLevel) {
        if (accessLevel == GitlabRolePermission.GUEST.getLevel()) {
            return GitlabRolePermission.GUEST;
        }
        if (accessLevel == GitlabRolePermission.REPORTER.getLevel()) {
            return GitlabRolePermission.REPORTER;
        }
        if (accessLevel == GitlabRolePermission.DEVELOPER.getLevel()) {
            return GitlabRolePermission.DEVELOPER;
        }
        if (accessLevel == GitlabRolePermission.MAINTAINER.getLevel()) {
            return GitlabRolePermission.MAINTAINER;
        }
        if (accessLevel == GitlabRolePermission.OWNER.getLevel()) {
            return GitlabRolePermission.OWNER;
        }
        return null;
    }

    public void delete(int projectId, String email) throws GitLabApiException {
        gitLabApi.getProjectApi().removeMember(projectId, getUserByEmail(email));
    }

    public Integer getUserByEmail(String email) throws GitLabApiException {
        return gitLabApi.getUserApi().getUserByEmail(email).getId();
    }

    public void getGitLabProjects() throws GitLabApiException {
        List<Project> tempList = gitLabApi.getProjectApi().getMemberProjects();

        for (int i = 0; i < tempList.size(); i++) {
            String nameOfProject = tempList.get(i).getName();
            System.out.println("Index: " + i + "." + " Name: " + nameOfProject);
        }
    }

    public String getProjectIdByIndex(int index) throws GitLabApiException {
        return gitLabApi
            .getProjectApi()
            .getMemberProjects()
            .get(index)
            .getId()
            .toString();
    }
}

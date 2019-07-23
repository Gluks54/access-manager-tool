package cli_programm.model;

public class GitLab {


    public String getProjectId() {
        return projectId;
    }

    public String getPrivateToken() {
        return privateToken;
    }


    @Override
    public String toString() {
        return "GitLab{" +
                "projectId='" + projectId + '\'' +
                ", privateToken='" + privateToken + '\'' +
                '}';
    }

    String projectId;
    String privateToken;
}

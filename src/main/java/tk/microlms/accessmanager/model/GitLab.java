package tk.microlms.accessmanager.model;

public class GitLab {

    public String getProjectId() {
        return projectId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public String toString() {
        return "GitLab{" +
                "projectId='" + projectId + '\'' +
                ", userName='" + userName + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

    String projectId;
    String userName;
    String pass;

}

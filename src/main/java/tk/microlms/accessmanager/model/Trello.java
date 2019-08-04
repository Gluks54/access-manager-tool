package tk.microlms.accessmanager.model;

public class Trello {
    public String getProjectId() {
        return projectId;
    }

    public String getEmail() {
        return email;
    }

    public String getKey() {
        return key;
    }

    String projectId;
    String email;
    String key;

    @Override
    public String toString() {
        return "Trello{" +
                "projectId='" + projectId + '\'' +
                ", email='" + email + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}

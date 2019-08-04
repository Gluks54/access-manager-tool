package tk.microlms.accessmanager.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GitlabUser {
    private String projectId;
    private String username;
    private String pass;
}

package tk.microlms.accessmanager.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class GitlabUser {
    private String projectId;
    private String username;
    private String pass;
}

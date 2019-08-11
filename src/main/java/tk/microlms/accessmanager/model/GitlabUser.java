package tk.microlms.accessmanager.model;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GitlabUser {
    private String projectId;
    private String username;
    private String pass;
}

package tk.microlms.accessmanager.model;


import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrelloUser {
    private String projectId;
    private String email;
    private String key;
    private String token;
    private String username;
}

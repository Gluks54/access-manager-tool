package tk.microlms.accessmanager.model;


import lombok.*;

import java.util.Map;

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
    private Map<String, String> mapIdAndBoards;
}

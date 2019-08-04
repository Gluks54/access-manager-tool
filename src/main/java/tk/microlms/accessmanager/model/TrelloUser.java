package tk.microlms.accessmanager.model;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class TrelloUser {
    private String projectId;
    private String email;
    private String key;
}

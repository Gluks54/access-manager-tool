package tk.microlms.accessmanager.model;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TrelloUser {
    private String projectId;
    private String email;
    private String key;
}

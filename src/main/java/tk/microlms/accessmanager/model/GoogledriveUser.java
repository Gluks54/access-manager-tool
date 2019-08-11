package tk.microlms.accessmanager.model;


import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoogledriveUser {
    private String fileId;
}

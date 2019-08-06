package tk.microlms.accessmanager.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class GoogleDriveClient {
    String client_id;
    String project_id;
    String client_secret;
    String auth_uri;
    String token_uri;
    String auth_provider_x509_cert_url;
}

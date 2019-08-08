package tk.microlms.accessmanager.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class GoogledriveService {

    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final int CALLBACK_PORT = 8888;
    private String fileId;

    public GoogledriveService(String fileId) {
        this.fileId = fileId;
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws Exception {
        InputStream in = new FileInputStream(new File(FilePathService.CONF));

        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + FilePathService.CONF);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(CALLBACK_PORT).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
        @Override
        public void onFailure(GoogleJsonError e,
                              HttpHeaders responseHeaders)
            throws IOException {
            // Handle error
            System.err.println(e.getMessage());
        }

        @Override
        public void onSuccess(Permission permission,
                              HttpHeaders responseHeaders)
            throws IOException {
            System.out.println("Permission ID: " + permission.toPrettyString());
        }
    };

    public void addToGoogleDrive(String email) throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();

        BatchRequest batch = drive.batch();
        Permission userPermission = new Permission()
            .setType("user")
            .setRole("writer")
            .setEmailAddress(email);
        drive.permissions().create(fileId, userPermission)
            .setFields("id")
            .queue(batch, callback);
        batch.execute();
    }

    public void getStatus() throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();

        PermissionList listPerm = drive
            .permissions()
            .list(fileId).setFields("permissions(displayName,emailAddress,id,role)")
            .execute();

        List<Permission> tempPermList = listPerm.getPermissions();

        for (Permission i : tempPermList) {
            System.out.println("username: " + i.getDisplayName() + " email: " +
                i.getEmailAddress() + " status: " + i.getRole() + " Id: " + i.getId());
        }
    }


    public void delete(String userId) throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();

        drive.permissions().delete(fileId, userId).execute();
    }
}



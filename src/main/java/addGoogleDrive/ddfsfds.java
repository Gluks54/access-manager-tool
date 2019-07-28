package addGoogleDrive;

public class ddfsfds {
    public void asas(){
        String fileId = "1sTWaJ_j7PkjzaBWtNc3IzovK5hQf21FbOw9yLeeLPNQ";
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
                System.out.println("Permission ID: " + permission.getId());
            }
        };
        BatchRequest batch = driveService.batch();
        Permission userPermission = new Permission()
                .setType("user")
                .setRole("writer")
                .setEmailAddress("user@example.com");
        driveService.permissions().create(fileId, userPermission)
                .setFields("id")
                .queue(batch, callback);

        Permission domainPermission = new Permission()
                .setType("domain")
                .setRole("reader")
                .setDomain("example.com");
        driveService.permissions().create(fileId, domainPermission)
                .setFields("id")
                .queue(batch, callback);

        batch.execute();
    }
}

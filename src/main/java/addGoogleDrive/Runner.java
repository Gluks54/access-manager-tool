package addGoogleDrive;

public class Runner {
    public static void main(String[] args) throws Exception {
        String email = "YahorSpy50322@gmail.com";
        String fileId = "1_Ds7jQ9r4OpjFm9gduf7WzvWxG9mKVpv";

        GoogleDriveService googleDriveService = new GoogleDriveService();
        googleDriveService.addToGoogleDrive(fileId, email);
    }
}

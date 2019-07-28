package addGoogleDrive;

public class Runner {
    public static void main(String[] args) throws Exception {
        String email = "YahorSpy50322@gmail.com";
        String fileId = "10tvx0hfEZi8rYD5zkDkARpVORAkOriur";
        String userID = "14199973712130044906";

        GoogleDriveService googleDriveService = new GoogleDriveService(fileId);

        System.out.println("initial state -----------------");
        googleDriveService.getStatus();

        System.out.println("share folder ---------------------");
        googleDriveService.addToGoogleDrive(email);
        googleDriveService.getStatus();

        System.out.println("delete permission");
        googleDriveService.delete(userID);
        googleDriveService.getStatus();
    }
}

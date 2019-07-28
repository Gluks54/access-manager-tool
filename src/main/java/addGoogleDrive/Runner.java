package addGoogleDrive;

public class Runner {
    public static void main(String[] args) throws Exception {
        String email = "YahorSpy50322@gmail.com";
        String fileId = "1mO7LZpxHXDfhkt7QEkDnoL7JWdGVp_WH";

        GoogleDriveService googleDriveService = new GoogleDriveService(fileId);

        System.out.println("initial state -----------------");
        googleDriveService.getStatus();
//
        System.out.println("share folder ---------------------");
        googleDriveService.addToGoogleDrive(email);
        googleDriveService.getStatus();


//        System.out.println("delete permission");
//        googleDriveService.delete(email);
//        googleDriveService.getStatus();
    }
}

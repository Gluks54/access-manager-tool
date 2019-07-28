package cli_programm.model;

public class GoogleDrive {
    public String getFileId() {
        return fileId;
    }

    @Override
    public String toString() {
        return "GoogleDrive{" +
                "fileId='" + fileId + '\'' +
                '}';
    }

    String fileId;
//    String

}

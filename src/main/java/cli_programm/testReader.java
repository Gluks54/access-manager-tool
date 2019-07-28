package cli_programm;

public class testReader {
    public static void main(String[] args) {
        System.out.println(Reader.readGitLabConf().get(0));
        System.out.println(Reader.readTrelloConf());
        System.out.println(Reader.readGoogleDriveConf());
    }
}

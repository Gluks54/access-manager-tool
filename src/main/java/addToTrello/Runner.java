package addToTrello;

import com.mashape.unirest.http.exceptions.UnirestException;

public class Runner {
    public static void main(String[] args) throws UnirestException {
        String projectId = "5d32e1d1027030023622dd81";
        String email = "YahorSpy50322@gmail.com";
        String key = "ea206614eb8c90f0df59e064b2662b45&token=7edf7707ab1ac85b7836132b49a4c2e74ee6642e63af58d28a16f1ba49092d3e";
        String userName = "yahorspy50322spyking3343";

        TrelloService trelloService = new TrelloService(key,projectId);

        trelloService.getStatus();
        System.out.println("----------------");

        trelloService.addToTrello(email);
        trelloService.getStatus();
        System.out.println("--------------------");

        trelloService.deleteMember(userName);
        trelloService.getStatus();

    }
}

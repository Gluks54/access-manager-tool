package tk.microlms.accessmanager.service;

import lombok.Getter;

@Getter
public class FilePathService {
    public final static String HOME = System.getProperty("user.home");
    public final static String CONF = String.format("%s/configuration.json",
        System.getProperty("user.home"));
}


package tk.microlms.accessmanager.model;

import lombok.Getter;

@Getter
public enum FilePath {
    CONF("configuration.json"),DEFAULT_CONF("src/main/resources/configuration.json");

    private final String path;

    FilePath(String path) {
        this.path = path;
    }
}

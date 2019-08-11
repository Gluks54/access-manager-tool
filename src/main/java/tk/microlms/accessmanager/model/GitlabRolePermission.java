package tk.microlms.accessmanager.model;

import lombok.Getter;

@Getter
public enum GitlabRolePermission {
    GUEST(10), REPORTER(20), DEVELOPER(30), MAINTAINER(40), OWNER(50);

    private final int level;

    GitlabRolePermission(int level) {
        this.level = level;
    }
}

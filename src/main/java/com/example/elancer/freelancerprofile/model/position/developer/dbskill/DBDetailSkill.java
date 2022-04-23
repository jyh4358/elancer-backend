package com.example.elancer.freelancerprofile.model.position.developer.dbskill;

import lombok.Getter;

@Getter
public enum DBDetailSkill {
    ORACLE("Oracle"),
    MSSQL("MSSQL"),
    MYSQL("MySQL"),
    MARIADB("MariaDB"),
    MONGODB("MongoDB"),
    POSTGRESQL("Prostgresql"),
    CUBRID("CUBRID"),
    TIBERO("Tibero")
    ;

    private String desc;

    DBDetailSkill(String desc) {
        this.desc = desc;
    }
}

package com.example.elancer.freelancerprofile.model.position.developer.phpaspskill;

import lombok.Getter;

@Getter
public enum PhpOrAspDetailSkill {
    PHP("PHP"),
    LARAVEL("laravel"),
    CODEIGNITER("Codeigniter"),
    SYMFONY("Symfony"),
    ZEND_FRAMEWORK("Zend Framework"),
    WORDPRESS("WordPress"),
    ASP("Asp")
    ;

    private String desc;

    PhpOrAspDetailSkill(String desc) {
        this.desc = desc;
    }
}

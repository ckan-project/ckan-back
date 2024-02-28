create table if not exists hanyang.data_request
(
    data_request_id bigint auto_increment
        primary key,
    organization    varchar(255) null,
    purpose         varchar(255) null,
    title           varchar(255) null,
    content         tinytext     null
);

create table if not exists hanyang.dataset
(
    created_date date                                                                                              null,
    download     int                                                                                               null,
    update_date  date                                                                                              null,
    view         int                                                                                               null,
    dataset_id   bigint auto_increment
        primary key,
    organization enum ('경상대학', '공과대학', '과학기술융합대학', '국제문화대학', '디자인대학', '소프트융합대학', '약학대학', '언론정보대학', '예체능대학', '입학처') null,
    title        varchar(255)                                                                                      null,
    description  tinytext                                                                                          null,
    FULLTEXT idx_title (title)
);

create table if not exists hanyang.dataset_theme
(
    dataset_id       bigint                                                       null,
    dataset_theme_id bigint auto_increment
        primary key,
    theme            enum ('국제', '복지', '입학', '장학', '재정', '취창업', '학사', '학생', '학술') null,
    constraint FKgcerm44anuenpnm627hscd0gx
        foreign key (dataset_id) references hanyang.dataset (dataset_id)
);

create table if not exists hanyang.resource
(
    dataset_id    bigint       null,
    resource_id   bigint auto_increment
        primary key,
    resource_name varchar(255) null,
    type          varchar(255) null,
    resource_url  tinytext     null,
    constraint UK_hqeb2ar8dige4e0iq76ew1ol6
        unique (dataset_id),
    constraint FKjk5ejvrt4y85vj3wset6qbnck
        foreign key (dataset_id) references hanyang.dataset (dataset_id)
);

create table if not exists hanyang.user
(
    is_active bit                              not null,
    user_id   bigint auto_increment
        primary key,
    email     varchar(255)                     null,
    name      varchar(255)                     null,
    password  varchar(255)                     null,
    role      enum ('ROLE_ADMIN', 'ROLE_USER') null,
    constraint UK_gj2fy3dcix7ph7k8684gka40c
        unique (name),
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
        unique (email)
);

create table if not exists hanyang.download
(
    dataset_id  bigint null,
    download_id bigint auto_increment
        primary key,
    resource_id bigint null,
    user_id     bigint null,
    constraint FK988l42tyuuthysfys9mkh7g92
        foreign key (dataset_id) references hanyang.dataset (dataset_id),
    constraint FKo7cc91sofromqu0551ocgpn96
        foreign key (user_id) references hanyang.user (user_id)
);

create table if not exists hanyang.notice
(
    create_date date         null,
    view        int          null,
    notice_id   bigint auto_increment
        primary key,
    user_id     bigint       null,
    content     varchar(255) null,
    title       varchar(255) null,
    constraint FKcvf4mh5se36inrxn7xlh2brfv
        foreign key (user_id) references hanyang.user (user_id)
);

create table if not exists hanyang.question
(
    date          date                          null,
    view          int                           null,
    question_id   bigint auto_increment
        primary key,
    user_id       bigint                        null,
    answer_status enum ('Completed', 'Waiting') null,
    title         varchar(255)                  null,
    content       tinytext                      null,
    constraint FK4ekrlbqiybwk8abhgclfjwnmc
        foreign key (user_id) references hanyang.user (user_id)
);

create table if not exists hanyang.answer
(
    date        date     null,
    answer_id   bigint auto_increment
        primary key,
    question_id bigint   null,
    user_id     bigint   null,
    content     tinytext null,
    constraint UK_eix9du6u2r4wxwu415wq8yb99
        unique (question_id),
    constraint FK68tbcw6bunvfjaoscaj851xpb
        foreign key (user_id) references hanyang.user (user_id),
    constraint FK8frr4bcabmmeyyu60qt7iiblo
        foreign key (question_id) references hanyang.question (question_id)
);

create table if not exists hanyang.scrap
(
    dataset_id bigint null,
    scrap_id   bigint auto_increment
        primary key,
    user_id    bigint null,
    constraint FK8bv7733l4pv76lu8xxkyqe5ix
        foreign key (dataset_id) references hanyang.dataset (dataset_id),
    constraint FKgt91kwgqa4f4oaoi9ljgy75mw
        foreign key (user_id) references hanyang.user (user_id)
);


drop table if exists user_mission;
drop table if exists user_lecture;
drop table if exists user_ground;
drop table if exists mission;
drop table if exists ground;
drop table if exists category;
drop table if exists user;

create table user
(
    id            int primary key auto_increment,
    oauth_id      varchar(50),
    username      varchar(50) unique  not null,
    password      varchar(255)        not null,
    name          varchar(50)         not null,
    nickname      varchar(50)         not null,
    phone         varchar(11)         not null,
    email         varchar(255) unique not null,
    address       varchar(255)        not null,
    point         int                 not null,
    mission_score int default 1000    not null,
    role          varchar(50)         not null,
    provider      varchar(255),
    providerId    varchar(255)
);

create table category
(
    id          int primary key auto_increment,
    main_filter varchar(30) not null,
    sub_filter  varchar(30) not null
);



create table ground
(
    id           int primary key auto_increment,
    lecture_id   int                           not null,
    category_id  int                           not null,
    title        varchar(255)                  not null,
    information  text                          not null,
    level        int                           not null,
    capacity     int                           not null,
    deposit      int                           not null,
    is_validated boolean     default false     not null,
    is_premium   boolean     default false     not null,
    created_at   datetime    default now(),
    start_at     date                          not null,
    end_at       date                          not null,
    validated_at datetime,
    status       varchar(30) default 'waiting' not null,
    drop_count   int         default 1         not null
);

create table mission
(
    id        int primary key auto_increment,
    ground_id int          not null,
    task      varchar(255) not null,
    start_at  date         not null,
    end_at    date         not null
);

create table user_ground
(
    id          int primary key auto_increment,
    user_id     int                  not null,
    ground_id   int                  not null,
    remain      boolean default true not null,
    result      boolean,
    rating      int,
    comment     text,
    prev_point  int,
    after_point int
);

create table user_lecture
(
    id         int primary key auto_increment,
    user_id    int not null,
    lecture_id int not null,
    status     varchar(50)
);

create table user_mission
(
    id         int primary key auto_increment,
    user_id    int                           not null,
    mission_id int                           not null,
    submit_at  datetime    default now()     not null,
    accepted   varchar(50) default 'waiting' not null
);
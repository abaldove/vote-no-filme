
    create table MOVIE (
        id_movie bigint not null,
        imdbId varchar(255),
        name varchar(255),
        posterName varchar(255),
        releaseDate date,
        sinopse varchar(255),
        weekMovie integer,
        primary key (id_movie)
    );

    create table USERS (
        id_user bigint not null,
        email varchar(255),
        login varchar(255),
        primary key (id_user)
    );

    create table VOTE (
        id_vote bigint not null,
        voteDate date,
        id_movie bigint not null,
        id_user bigint not null,
        primary key (id_vote)
    );

    alter table VOTE 
        add constraint FK284AEAB60FC051 
        foreign key (id_movie) 
        references MOVIE;

    alter table VOTE 
        add constraint FK284AEAA2CE1385 
        foreign key (id_user) 
        references USERS;

    create table T_ID (
         key varchar(255),
         valor integer 
    ) ;


create table admins
(
    admin_id int primary key auto_increment,
    firstname varchar(100) not null,
    lastname varchar(100) not null,
    username varchar(200) not null unique,
    password varchar(500) not null
);

create table pages
(
    page_id int primary key auto_increment,
    url varchar(1000) not null unique,
    title varchar(5000) not null
);

create table indexing_histories
(
    admin_id int not null,
    page_id int not null unique,

    foreign key (page_id) references pages (page_id),
    foreign key (admin_id) references admins (admin_id)
);

create table words
(
    word_id int primary key auto_increment,
    word varchar (500) not null,
    frequency int not null,
    page_id int not null,

    UNIQUE KEY page_to_word_key (page_id, word),

    foreign key (page_id) references pages (page_id)
);

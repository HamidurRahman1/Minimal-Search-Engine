
insert into admins (firstname, lastname, username, password) values
                                                                 ('first1', 'last1', 'user1', 'pass1'),
                                                                 ('first2', 'last2', 'user2', 'pass2'),
                                                                 ('first3', 'last3', 'user3', 'pass3');


insert into pages (title, url) values
                                   ('Google Search Page', 'https://www.google.com/'),
                                   ('Facebook Login', 'https://www.facebook.com/'),
                                   ('Reddit Home', 'https://www.reddit.com/');


insert into indexing_histories (admin_id, page_id) values
                                                       (1, 1),
                                                       (1, 3),
                                                       (2, 2);


insert into words (word, frequency, page_id) values
                                                 ('first1', 10, 1),
                                                 ('first2', 5, 1),
                                                 ('third1', 4, 3),
                                                 ('second', 7, 2),
                                                 ('third2', 8, 3);
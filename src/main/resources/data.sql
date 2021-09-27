
INSERT INTO users (id,email, password, phone, username) VALUES (nextval('hibernate_sequence'),'admin@admin.com','$2a$12$6EdE4Q4RTZimBS1QgAevnOjFD/ue5MmfHvkpyG9NWY4X1NpFKsm0W', '5343342323', 'admin' );

INSERT INTO users (id,email, password, phone, username) VALUES (nextval('hibernate_sequence'),'user@user.com','$2a$12$6EdE4Q4RTZimBS1QgAevnOjFD/ue5MmfHvkpyG9NWY4X1NpFKsm0W', '5343342322', 'user' );

INSERT INTO privileges  (ID,privilege) VALUES(nextval('hibernate_sequence'),'ADMIN' );
INSERT INTO privileges  (ID,privilege) VALUES(nextval('hibernate_sequence'),'USERS' );



INSERT INTO users_privileges(USER_ID, privileges_id) VALUES ( (SELECT ID FROM users WHERE USERNAME='admin'),(SELECT ID FROM privileges WHERE privilege='ADMIN'));
INSERT INTO users_privileges(USER_ID, privileges_id) VALUES ( (SELECT ID FROM users WHERE USERNAME='admin'),(SELECT ID FROM privileges WHERE privilege='USERS'));
INSERT INTO users_privileges(USER_ID, privileges_id) VALUES ( (SELECT ID FROM users WHERE USERNAME='user'),(SELECT ID FROM privileges WHERE privilege='USERS'));




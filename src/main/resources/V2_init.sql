insert into users (created,
                   updated,
                   active,
                   email,
                   firstname,
                   lastname,
                   password,
                   role,
                   username)
values ('2023-01-20 14:38:42.000000',
        '2023-01-20 14:38:43.000000',
        true,
        'admin@mail.kg',
        'admin',
        'admin',
        '$2a$12$v8gAMZ2gJzN5k6oWJ2AD8.AaKw7HyN9bpdDtQ.nlzNYr6mZlajMny',
        'ADMIN',
        'admin'),
       ('2023-01-20 14:38:42.000000',
        '2023-01-20 14:38:43.000000',
        true,
        'doctor@mail.kg',
        'Nursultan',
        'Kudaikulov',
        '$2a$12$v8gAMZ2gJzN5k6oWJ2AD8.AaKw7HyN9bpdDtQ.nlzNYr6mZlajMny',
        'DOCTOR',
        'doctor');

insert into departments (name, description) values ('Уролог', 'Уролог');
insert into departments (name, description) values ('Кардиолог', 'Кардиолог');
insert into departments (name, description) values ('Нефролог', 'Нефролог');
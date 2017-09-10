
create schema something;

create table something.test (
    id identity primary key,
    test_int int,
    test_bool boolean,
    test_currency decimal(20,2),
    test_double double,
    test_time time,
    test_date date,
    test_string varchar(255),
    test_binary blob
);

create table something.test2 (
    registration_number int,
    contact_person_name varchar(255),
    description varchar(255)
);
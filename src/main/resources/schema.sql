
create schema SOME_REGISTER;

create table SOME_REGISTER.TEST (
    id identity primary key,
    test_int int,
    test_bool boolean,
    test_currency decimal(20,2),
    test_double double,
    test_time time,
    test_date date,
    test_string varchar(255)
);
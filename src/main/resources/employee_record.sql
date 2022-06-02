CREATE DATABASE employee_record;
USE employee_record;
CREATE TABLE employee (
    emp_id INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    gender VARCHAR(255),
    dob VARCHAR(255),
    age INT(3),
    salary DECIMAL(10,2),
    status VARCHAR(255)
);
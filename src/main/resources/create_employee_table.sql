CREATE TABLE employee (
    emp_id INT(3) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL,
    dob DATE NOT NULL,
    age INT(3) NOT NULL,
    salary DECIMAL(7,2) NOT NULL,
    status VARCHAR(255) NOT NULL,
    PRIMARY KEY (emp_id)
);
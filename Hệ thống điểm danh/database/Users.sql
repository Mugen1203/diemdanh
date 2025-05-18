CREATE TABLE Users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(100),
    role ENUM('student', 'teacher', 'admin'),
    name VARCHAR(100)
);

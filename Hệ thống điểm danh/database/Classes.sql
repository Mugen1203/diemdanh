CREATE TABLE Classes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    class_name VARCHAR(100),
    teacher_id INT,
    FOREIGN KEY (teacher_id) REFERENCES Users(id)
);

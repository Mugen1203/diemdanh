CREATE TABLE Attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    class_id INT,
    attendance_date DATE,
    status ENUM('present', 'absent'),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (class_id) REFERENCES Classes(id)
);

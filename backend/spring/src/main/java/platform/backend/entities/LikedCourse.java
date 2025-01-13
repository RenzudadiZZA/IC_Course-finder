package platform.backend.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "likedcourses")
@IdClass(LikedCourseId.class) // Specify the composite key class
public class LikedCourse {

    @Id
    private String username;

    @Id
    private String courseCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public LikedCourse() {}

    public LikedCourse(String username, String courseCode, LocalDateTime createdAt) {
        this.username = username;
        this.courseCode = courseCode;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

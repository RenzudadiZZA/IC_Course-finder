package platform.backend.entities;

import java.io.Serializable;
import java.util.Objects;
/* Reference 1 - taken from Chatgpt, it told me how to Implement the back end to fit the composite primary key */
public class LikedCourseId implements Serializable {
    private String username;
    private String courseCode;

    public LikedCourseId() {}

    public LikedCourseId(String username, String courseCode) {
        this.username = username;
        this.courseCode = courseCode;
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

    // equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikedCourseId that = (LikedCourseId) o;
        return Objects.equals(username, that.username) && Objects.equals(courseCode, that.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, courseCode);
    }
}
/* end of reference 1 */


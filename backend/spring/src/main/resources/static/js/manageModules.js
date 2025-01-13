document.addEventListener("DOMContentLoaded", () => {
    // Handle add course form submission
    const addCourseForm = document.getElementById("addCourseForm");
    addCourseForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        // Get from data
        const courseData = {
            courseCode: document.getElementById("courseCode").value.trim(),
            title: document.getElementById("title").value.trim(),
            term: document.getElementById("term").value.trim(),
            description: document.getElementById("description").value.trim(),
            learningOutcomes: document.getElementById("learningOutcomes").value.trim(),
            moduleContent: document.getElementById("moduleContent").value.trim(),
            prerequisites: document.getElementById("prerequisites").value.trim(),
            lecturer: document.getElementById("lecturer").value.trim(),
        };

        try {
            // Send POST request to add the course
            const response = await fetch("http://localhost:8080/api/courses", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(courseData),
            });

            if (!response.ok) {
                throw new Error(`Failed to add course: ${response.statusText}`);
            }

            const addedCourse = await response.json();
            alert(`Course added successfully: ${addedCourse.courseCode} - ${addedCourse.title}`);
            addCourseForm.reset(); // Clear the form
        } catch (error) {
            console.error("Error adding course:", error);
            alert("Failed to add course. Please try again.");
        }
    });

    // Handle delete course form submission
    const deleteCourseForm = document.getElementById("deleteCourseForm");
    deleteCourseForm.addEventListener("submit", async (e) => {
        e.preventDefault(); // Prevent the default form submission behavior

        // Get the course code to delete
        const courseCode = document.getElementById("deleteCourseCode").value.trim();

        try {
            // Send DELETE request to remove the course
            const response = await fetch(`http://localhost:8080/api/courses/${courseCode}`, {
                method: "DELETE",
            });

            if (!response.ok) {
                throw new Error(`Failed to delete course: ${response.statusText}`);
            }

            alert(`Course deleted successfully: ${courseCode}`);
            deleteCourseForm.reset(); // Clear the form
        } catch (error) {
            console.error("Error deleting course:", error);
            alert("Failed to delete course. Please try again.");
        }
    });
});
document.addEventListener('DOMContentLoaded', () => {
    // Get the courseCode from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const courseCode = urlParams.get('courseCode');

    // Elements to populate
    const courseTitle = document.getElementById('course-title');
    const courseDescription = document.getElementById('course-description');
    const courseTerm = document.getElementById('course-term');
    const courseLearningOutcomes = document.getElementById('course-learning-outcomes');
    const courseModuleContent = document.getElementById('course-module-content');
    const coursePrerequisites = document.getElementById('course-prerequisites');
    const courseLecturer = document.getElementById('course-lecturer');

    // Fetch course details from API
    fetch(`/api/courses/${courseCode}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch course details');
            }
            return response.json();
        })
        .then(data => {
            // Populate the elements with course details
            courseTitle.textContent = data.title;
            courseDescription.textContent = data.description || 'N/A';
            courseTerm.textContent = data.term || 'N/A';
            courseLearningOutcomes.textContent = data.learningOutcomes || 'N/A';
            courseModuleContent.textContent = data.moduleContent || 'N/A';
            coursePrerequisites.textContent = data.prerequisites || 'N/A';
            courseLecturer.textContent = data.lecturer || 'N/A';
        })
        .catch(error => {
            console.error(error);
            courseTitle.textContent = 'Error loading course details';
        });

    // Add to Favorites button functionality
    document.getElementById('add-to-favorites').addEventListener('click', () => {
        fetch('/api/likedCourses', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username: 'testUser', courseCode: courseCode }) // Replace 'testUser' with logged-in user
        })
            .then(response => response.json())
            .then(data => {
                alert('Course added to favorites!');
            })
            .catch(error => console.error('Error adding to favorites:', error));
    });

    // Back to list button functionality
    document.getElementById('back-to-list').addEventListener('click', () => {
        window.location.href = 'index.html'; // Redirect to course list
    });
});

document.addEventListener("DOMContentLoaded", function () {
    // Get the course code from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const courseCode = urlParams.get("courseCode");

    if (!courseCode) {
        alert("No course code provided!");
        return;
    }

    fetch(`/api/courses/${courseCode}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch course details");
            }
            return response.json();
        })
        .then(data => {
            // 更新页面内容
            document.querySelector("#course-description").textContent = data.description || "N/A";
            document.querySelector("#course-term").textContent = data.term || "N/A";
            document.querySelector("#course-learning-outcomes").textContent = data.learningOutcomes || "N/A";
            document.querySelector("#course-module-content").textContent = data.moduleContent || "N/A";
            document.querySelector("#course-prerequisites").textContent = data.prerequisites || "N/A";
            document.querySelector("#course-lecturer").textContent = data.lecturer || "N/A";
        })
        .catch(error => {
            console.error("Error loading course details:", error);
            alert("Failed to load course details. Please try again later.");
        });
});

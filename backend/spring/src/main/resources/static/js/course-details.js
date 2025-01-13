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

    // If other code on the page also listens for the DOMContentLoaded event and overwrites it, your event may not execute
    // Change from DOMcontentLoaded to load! --ziang
    // Add to Favorites button functionality

    /* Reference 2 - This part is quite confusing, Chatgpt help me to make it works and most works are done by myself
    * all the error halnding and alert are suggested by Chatgpt and the rest of the code is done by myself */
    window.addEventListener('DOMContentLoaded', () => {
        // console.log('Testing script execution');
        const button = document.getElementById('add-to-favorites');
        const courseCode = new URLSearchParams(window.location.search).get('courseCode');
        const username = localStorage.getItem('username');
        if (!username) {
            alert('You must be logged in to use this feature.');
            button.disabled = true;
            return;
        }

        // get all liked courses
        fetch(`/api/likedcourses/${username}`, {
            method: 'GET'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch favorite courses');
                }
                return response.json();
            })
            .then(favorites => {
                // Check if the course is already in favorites
                const isFavorite = favorites.some(course => course.courseCode === courseCode);

                if (isFavorite) {
                    // Update button text, color, and action when loaded
                    button.textContent = 'Remove from Favorites';
                    button.dataset.action = 'remove';
                    button.style.backgroundColor = 'red';
                } else {
                    button.textContent = 'Add to Favorites';
                    button.dataset.action = 'add';
                    button.style.backgroundColor = 'blue';
                }
            })
            .catch(error => {
                console.error('Error fetching favorite courses:', error);
            });

        // Add event listener to button
        button.addEventListener('click', () => {
            const action = button.dataset.action;

            if (action === 'remove') {
                const confirmRemove = confirm('Are you sure you want to remove this course from your favorites?');
                if (!confirmRemove) {
                    return;
                }
            }

            // Set up API endpoint and method based on action
            const apiEndpoint = action === 'add' ? '/api/likedcourses' : '/api/likedcourses/remove';
            const apiMethod = action === 'add' ? 'POST' : 'DELETE';

            fetch(apiEndpoint, {
                method: apiMethod,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, courseCode })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(
                            action === 'add' ? 'Failed to add course to favorites' : 'Failed to remove course from favorites'
                        );
                    }
                    return response.text();
                })
                .then(message => {
                    alert(message);

                    if (action === 'add') {
                        button.textContent = 'Remove from Favorites';
                        button.dataset.action = 'remove';
                        button.style.backgroundColor = 'red';
                    } else {
                        button.textContent = 'Add to Favorites';
                        button.dataset.action = 'add';
                        button.style.backgroundColor = 'blue';
                    }
                })
                .catch(error => {
                    console.error('Error processing favorite action:', error);
                    alert('An error occurred. Please try again.');
                });
        });
    });
    /* Reference 2 end */

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
// Redirect to Home Page when clicking on back to list button
document.addEventListener('DOMContentLoaded', () => {
    const backToListButton = document.getElementById('back-to-list');
    if (backToListButton) {
        backToListButton.addEventListener('click', () => {
            window.location.href = '../html/Main.html';
        });
    }
});

document.addEventListener('DOMContentLoaded', () => {
    const username = localStorage.getItem('username');
    const sidebar = document.getElementById('sidebar3');
    const courseListItems = document.getElementById('course-list-items');

    if (!username) {
        console.error('User not logged in.');
        return;
    }

    // get all liked courses
    fetch(`/api/likedcourses/likedlist/${username}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch favorite courses');
            }
            return response.json();
        })
        .then(favorites => {
            if (favorites.length === 0) {
                courseListItems.innerHTML = '<li>No favorite courses added yet.</li>';
                return;
            }

            // generate list items for each favorite course
            const favoriteItems = favorites.map(course => `
                <li>
                    <a href="/html/InfoPage.html?courseCode=${course.courseCode}">${course.title}</a>
                    <button class="star-button" data-course-code="${course.courseCode}">★</button>
                </li>
            `).join('');
            courseListItems.innerHTML = favoriteItems;

            // add event listeners to star buttons
            const starButtons = document.querySelectorAll('.star-button');
            starButtons.forEach(button => {
                button.addEventListener('click', () => {
                    const courseCode = button.dataset.courseCode;

                    // call API to remove course from favorites
                    fetch(`/api/likedcourses/remove`, {
                        method: 'DELETE',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ username, courseCode })
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Failed to remove course from favorites');
                            }
                            return response.text();
                        })
                        .then(message => {
                            alert(message);
                            button.closest('li').remove();
                        })
                        .catch(error => {
                            console.error('Error removing favorite course:', error);
                            alert('An error occurred. Please try again.');
                        });
                });
            });
        })
        .catch(error => {
            console.error('Error fetching favorite courses:', error);
        });

    sidebar.style.display = 'block';
});
document.addEventListener('DOMContentLoaded', function () {
    const courseListButton = document.getElementById('course-list-button2');
    const sidebar2 = document.getElementById('sidebar3'); // Course List Sidebar

    courseListButton.addEventListener('click', function (event) {
        event.stopPropagation(); // Prevent the click from propagating to the document

        // Close the sidebar if it's open
        if (sidebar2.style.width === '250px') {
            sidebar2.style.width = '0';
        }

        // close the sidebar if it clicked outside
        document.addEventListener('click', function (event) {
            if (event.target !== courseListButton) {
                sidebar2.style.width = '0';
            }
        });
        // Toggle the course list sidebar
        sidebar2.style.width = sidebar2.style.width === '250px' ? '0' : '250px';

    });



});
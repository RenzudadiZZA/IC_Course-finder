// Search function and related interactions
document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM fully loaded and parsed'); // Temporary debugging log

    const searchButton = document.querySelector('.search-button');
    const clearButton = document.querySelector('.clear-button');
    const keywordInput = document.querySelector('#course-keywords');
    const courseListElement = document.getElementById('course-list');

    if (!searchButton || !clearButton || !keywordInput || !courseListElement) {
        console.error('Required DOM elements not found');
        return;
    }

    clearButton.addEventListener('click', () => {
        keywordInput.value = '';
        courseListElement.innerHTML = '';
    });

    searchButton.addEventListener('click', () => {
        const keyword = keywordInput.value.trim();


        courseListElement.innerHTML = '<tr><td colspan="4">Loading...</td></tr>';

        fetch(`/api/courses/search?keywords=${encodeURIComponent(keyword)}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch search results');
                }
                return response.json();
            })
            .then(data => {
                console.log('Search results:', data); // Temporary debugging log

                if (data.length === 0) {
                    courseListElement.innerHTML = '<tr><td colspan="4">No courses found.</td></tr>';
                    return;
                }

                const rows = data.map(course => `
                    <tr>
                        <td><a href="/html/InfoPage.html?courseCode=${course.courseCode}">${course.title}</a></td>
                        <td>${course.level || 'N/A'}</td>
                        <td>${course.term || 'N/A'}</td>
                        <td>${course.courseCode}</td>
                    </tr>
                `).join('');
                courseListElement.innerHTML = rows;
            })
            .catch(error => {
                console.error('Error fetching search results:', error);
                courseListElement.innerHTML = '<tr><td colspan="4">Error fetching results. Please try again later.</td></tr>';
            });
    });
});


// Handle user login state and menu interactions
document.addEventListener('DOMContentLoaded', function () {
    // Get username and role from localStorage or URL parameter
    const urlParams = new URLSearchParams(window.location.search);
    const username = urlParams.get('username') || localStorage.getItem('username');
    const role = localStorage.getItem('role');
    if (username) {
        // Store username and role back into localStorage to ensure persistence
        localStorage.setItem('username', username);
        if (role) {
            localStorage.setItem('role', role);
        }

        // Add functionality to display username and role in the menu
        const menuButton = document.querySelector('.menu button');
        const sidebar = document.getElementById('sidebar');
        const userInfo = document.getElementById('user-info'); // Element to display user info

        menuButton.addEventListener('click', function () {
            if (sidebar.style.width === '250px') {
                sidebar.style.width = '0';
            } else {
                sidebar.style.width = '250px';

                // Update user info in the sidebar
                if (userInfo) {
                    userInfo.innerHTML = `
                        <p><strong>Username:</strong> ${username}</p>
                        <p><strong>Role:</strong> ${role || 'N/A'}</p>
                    `;
                }
            }
        });
        // If clicked outside the sidebar, close it
        window.addEventListener('click', function (event) {
            if (event.target !== menuButton && sidebar.style.width === '250px') {
                sidebar.style.width = '0';
            }
        });

        // Add logout functionality
        const logoutButton = document.getElementById('logout-button');
        if (logoutButton) {
            logoutButton.style.display = 'block';
            logoutButton.addEventListener('click', function () {
                // Clear username and role from localStorage
                localStorage.removeItem('username');
                localStorage.removeItem('role');
                window.location.href = '../Index.html'; // Redirect to home page
            });
        }

    } else {
        // Redirect to home page if not logged in
        alert('You are not logged in. Redirecting to home page.');
        window.location.href = '../Index.html';
    }
});

// Toggle advanced search functionality
document.addEventListener('DOMContentLoaded', () => {

    const contentSearchButton = document.querySelector('.advanced-button');
    const keywordInput = document.querySelector('#course-keywords');
    const courseListElement = document.getElementById('course-list');
    contentSearchButton.addEventListener('click', () => {
        console.log('Search module content button clicked'); // Temporary debugging log
        const keyword = keywordInput.value.trim();

        courseListElement.innerHTML = '<tr><td colspan="4">Loading...</td></tr>';

        /* refrence4 taken from chatgpt to debug*/
        const apiEndpoint = keyword
            ? `/api/courses/search/content?keyword=${encodeURIComponent(keyword)}`
            : `/api/courses`;

        fetch(apiEndpoint)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch courses');
                }
                return response.json();
            })
            .then(data => {
                console.log('Search module content results:', data); // Temporary debugging log

                /* end of refrence4 */
                if (data.length === 0) {
                    courseListElement.innerHTML = '<tr><td colspan="4">No courses found.</td></tr>';
                    return;
                }

                const rows = data.map(course => `
                    <tr>
                        <td><a href="/html/InfoPage.html?courseCode=${course.courseCode}">${course.title}</a></td>
                        <td>${course.level || 'N/A'}</td>
                        <td>${course.term || 'N/A'}</td>
                        <td>${course.courseCode}</td>
                    </tr>
                `).join('');
                courseListElement.innerHTML = rows;
            })
            .catch(error => {
                console.error('Error fetching module content search results:', error);
                courseListElement.innerHTML = '<tr><td colspan="4">Error fetching results. Please try again later.</td></tr>';
            });
    });
});




document.addEventListener('DOMContentLoaded', function () {
    const courseListButton = document.getElementById('course-list-button');
    const sidebar2 = document.getElementById('sidebar2'); // Course List Sidebar
    const menuSidebar = document.getElementById('sidebar'); // Menu Sidebar

    courseListButton.addEventListener('click', function (event) {
        event.stopPropagation(); // Prevent the click from propagating to the document

        // Close the menu sidebar if it's open
        if (menuSidebar.style.width === '250px') {
            menuSidebar.style.width = '0';
        }

        // Toggle the course list sidebar
        sidebar2.style.width = sidebar2.style.width === '250px' ? '0' : '250px';
    });

    // Hide sidebar2 when clicking outside of it
    document.addEventListener('click', function (event) {
        if (!sidebar2.contains(event.target) && event.target !== courseListButton && sidebar2.style.width === '250px') {
            sidebar2.style.width = '0';
        }
    });
});


// Fetch all courses and display in a table
document.addEventListener('DOMContentLoaded', () => {
    const courseListElement = document.getElementById('course-list');
    const apiEndpoint = '/api/courses/list'; // Use the new /list endpoint

    fetch(apiEndpoint)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch courses');
            }
            return response.json();
        })
        .then(data => {
            const rows = data.map(course => `
                <tr>
                    <td>
                        <a href="/html/InfoPage.html?courseCode=${course.courseCode}" 
                           class="course-link" 
                           data-course-code="${course.courseCode}">
                            ${course.title}
                        </a>
                    </td>
                    <td>${course.level || 'N/A'}</td>
                    <td>${course.term || 'N/A'}</td>
                    <td>${course.courseCode}</td>
                </tr>
            `).join('');

            courseListElement.innerHTML = rows;
            // Add click event listener to course links
            const courseLinks = document.querySelectorAll('.course-link');
            courseLinks.forEach(link => {
                link.addEventListener('click', (event) => {
                    event.preventDefault(); // Temporarily prevent default navigation

                    const courseCode = link.dataset.courseCode;
                    const username = localStorage.getItem('username') || 'guest'; // Replace with actual logic

                    console.log(`Saving recently viewed course: ${courseCode}, username: ${username}`); // Debugging log

                    // Send request to save recently viewed course
                    fetch('/api/recently-visited', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ username, courseCode })
                    }).then(response => {
                        if (!response.ok) {
                            throw new Error(`Failed to save course. Status: ${response.status}`);
                        }
                        console.log('Successfully saved course');

                        // After saving, navigate to the course details page
                        window.location.href = link.href;
                    }).catch(error => {
                        console.error('Error saving recently visited course:', error);

                        // Even if saving fails, navigate to the course details page
                        window.location.href = link.href;
                    });
                });
            });
        })
        .catch(error => {
            console.error('Error loading courses:', error);
            courseListElement.innerHTML = `<tr><td colspan="4">Failed to load courses. Please try again later.</td></tr>`;
        });
});

// Redirect to Home Page when clicking on the IC logo
document.addEventListener('DOMContentLoaded', () => {
    const icLogo = document.getElementById('logo');
    if (icLogo) {
        icLogo.addEventListener('click', () => {
            window.location.href = '../Index.html ';
        });
    }
});

// Add logout functionality
document.addEventListener('DOMContentLoaded', () => {
    const logoutButton = document.getElementById('logout-button');
    if (logoutButton) {
        logoutButton.addEventListener('click', () => {
            // Clear user login info and redirect to home
            localStorage.removeItem('user');
            window.location.href = '../Index.html';
        });
    }
});

// Fetch and display all liked courses on sidebar
document.addEventListener('DOMContentLoaded', () => {
    const username = localStorage.getItem('username');
    const sidebar = document.getElementById('sidebar2');
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

// filter courses by department
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("filterButton").addEventListener("click", async () => {
        const department = document.getElementById("departmentFilter").value;

        try {
            const response = await fetch("/api/courses");
            const courses = await response.json();
            console.log("Fetched courses:", courses);
            const courseList = document.getElementById("course-list");
            if (!courseList) {
                console.error("course-list element not found!");
                return;
            }

            // Clear existing course list
            courseList.innerHTML = "";

            const filteredCourses = courses.filter(course => {
                console.log("Filtering course:", course.courseCode, "with department:", department);
                if (!course || !course.courseCode) {
                    console.warn("Invalid course object:", course);
                    return false;
                }
                if (department === "All") return true;
                if (department === "Computing") return course.courseCode.startsWith("COMP");
                if (department === "Bioengineering") return course.courseCode.startsWith("BIOE");
                if (department === "Mathematics") return course.courseCode.startsWith("MATH");
                if (department === "Aeronautics") return course.courseCode.startsWith("AERO");
                return false;
            });

            if (filteredCourses.length === 0) {
                courseList.innerHTML = "<tr><td colspan='4'>No courses found.</td></tr>";
            } else {
                filteredCourses.forEach(course => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td><a href="/html/InfoPage.html?courseCode=${course.courseCode}">${course.title || "No title available"}</a></td>
                        <td>${course.level || "N/A"}</td>
                        <td>${course.term || "N/A"}</td>
                        <td>${course.courseCode}</td>
                    `;
                    courseList.appendChild(row);
                });
            }
        } catch (error) {
            console.error("Error fetching courses:", error);
            const courseList = document.getElementById("course-list");
            courseList.innerHTML = "<tr><td colspan='4'>Error fetching results. Please try again later.</td></tr>";
        }
    });
});
// Clearing inputs and resetting filters
document.addEventListener('DOMContentLoaded', () => {
    const searchButton = document.querySelector('.search-button');
    const clearButton = document.querySelector('.clear-button');
    const keywordInput = document.querySelector('#course-keywords');
    const areaSelect = document.querySelector('#course-area');

    clearButton.addEventListener('click', () => {
        keywordInput.value = '';
        areaSelect.value = 'all';
    });

    // Handle search button click with keyword and area values
    searchButton.addEventListener('click', () => {
        const keyword = keywordInput.value;
        const area = areaSelect.value;
        console.log(`Search for keyword: ${keyword}, area: ${area}`);
    });
});
document.addEventListener('DOMContentLoaded', function() {
    const menuButton = document.querySelector('.menu button');
    const sidebar = document.getElementById('sidebar');
    const loginForm = document.getElementById('login-form');
    const userInfo = document.getElementById('user-info');
    const userRoleSpan = document.getElementById('user-role');

    menuButton.addEventListener('click', function() {
        if (sidebar.style.width === '250px') {
            sidebar.style.width = '0';
        } else {
            sidebar.style.width = '250px';
        }
    });

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // Simulate login and role assignment
        let userRole = 'student'; // Default role
        if (username === 'admin' && password === 'admin') {
            userRole = 'admin';
        }

        userRoleSpan.textContent = userRole;
        loginForm.style.display = 'none';
        userInfo.style.display = 'block';
    });
});
document.addEventListener('DOMContentLoaded', () => {
    const advancedButton = document.getElementById('advanced-button');
    const keywordLabel = document.querySelector('label[for="course-keywords"]');

    document.getElementById('course-keywords');
    advancedButton.addEventListener('click', () => {
        if (advancedButton.classList.contains('active')) {
            // Revert to normal search mode
            keywordLabel.textContent = 'Search by module name:';
            advancedButton.textContent = 'Search module content';
            advancedButton.classList.remove('active');
        } else {
            // Switch to advanced search mode
            keywordLabel.textContent = 'Search module by course content keywords:';
            advancedButton.textContent = 'Search module name';
            advancedButton.classList.add('active');
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const courseListButton = document.getElementById('course-list-button'); // My Course List button
    const sidebar2 = document.getElementById('sidebar2'); // Sidebar element for My Module List

    // Toggle sidebar visibility
    courseListButton.addEventListener('click', function () {
        if (sidebar2.style.width === '250px') {
            sidebar2.style.width = '0'; // Close sidebar
        } else {
            sidebar2.style.width = '250px'; // Open sidebar
        }
    });
});
// active button when lick on star
document.addEventListener('DOMContentLoaded', function () {
    const starButtons = document.querySelectorAll('.star-button');

    starButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            button.classList.toggle('active');
        });
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const advancedButton = document.getElementById('toggle-advanced');
    const advancedFilters = document.getElementById('advanced-filters');

    // Toggle advanced filters visibility
    advancedButton.addEventListener('click', function () {
        if (advancedFilters.style.display === 'none') {
            advancedFilters.style.display = 'block';
            advancedButton.textContent = 'Hide Advanced Filters'; // Update button text
        } else {
            advancedFilters.style.display = 'none';
            advancedButton.textContent = 'Show Advanced Filters'; // Update button text
        }
    });
});

// Get all courses from the API and display them in a table
document.addEventListener('DOMContentLoaded', () => {
    const courseListElement = document.getElementById('course-list');
    // Fetch all courses from db ( /api/courses/list ) dont use /api/courses otherwise it will give error
    const apiEndpoint = '/api/courses/list';

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
            window.location.href = '../index.html';
        });
    }
});


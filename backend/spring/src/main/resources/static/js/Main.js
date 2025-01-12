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
    const advancedButton = document.getElementById('advanced-button');
    const keywordLabel = document.querySelector('label[for="course-keywords"]');

    advancedButton.addEventListener('click', () => {
        if (advancedButton.classList.contains('active')) {
            keywordLabel.textContent = 'Search by module name:';
            advancedButton.textContent = 'Search module content';
        } else {
            keywordLabel.textContent = 'Search module by course content keywords:';
            advancedButton.textContent = 'Search module name';
        }
        advancedButton.classList.toggle('active');
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const courseListButton = document.getElementById('course-list-button');
    const sidebar2 = document.getElementById('sidebar2');

    courseListButton.addEventListener('click', function (event) {
        event.stopPropagation(); // Prevent the click from propagating to the document
        sidebar2.style.width = sidebar2.style.width === '250px' ? '0' : '250px';
    });

    // Hide sidebar2 when clicking outside of it
    document.addEventListener('click', function (event) {
        if (!sidebar2.contains(event.target) && sidebar2.style.width === '250px') {
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
                    <td><a href="/html/InfoPage.html?courseCode=${course.courseCode}">${course.title}</a></td>
                    <td>${course.level || 'N/A'}</td>
                    <td>${course.term || 'N/A'}</td>
                    <td>${course.courseCode}</td>
                </tr>
            `).join('');

            courseListElement.innerHTML = rows;
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
            window.location.href = '/index.html'; // Adjust path if needed
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
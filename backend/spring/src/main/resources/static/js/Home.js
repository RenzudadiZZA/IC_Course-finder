// This is the js for home page -- the page with IC logo and welcome message
document.addEventListener('DOMContentLoaded', function () {
    // Initialize particles.js background
    particlesJS('particles-js', {
        particles: {
            number: {
                value: 100,
                density: {
                    enable: true,
                    value_area: 800
                }
            },
            color: {
                value: "#0055a6"
            },
            shape: {
                type: "circle",
                stroke: {
                    width: 0,
                    color: "#000000"
                }
            },
            opacity: {
                value: 0.5,
                random: false
            },
            size: {
                value: 3,
                random: true
            },
            line_linked: {
                enable: true,
                distance: 150,
                color: "#0055a6",
                opacity: 0.4,
                width: 1
            },
            move: {
                enable: true,
                speed: 2,
                direction: "none",
                random: false,
                straight: false,
                out_mode: "out",
                bounce: false
            }
        },
        interactivity: {
            detect_on: "canvas",
            events: {
                onhover: {
                    enable: true,
                    mode: "repulse"
                },
                onclick: {
                    enable: true,
                    mode: "push"
                },
                resize: true
            }
        },
        retina_detect: true
    });

    // Button click event to redirect to Main.html if logged in
    const getStartedButton = document.getElementById('get-started-button');
    getStartedButton.addEventListener('click', function () {
        const username = localStorage.getItem('username');
        const role = localStorage.getItem('role'); // Get the role from localStorage

        if (username) {
            // Redirect to Main.html with username and role as query parameters
            window.location.href = `../html/Main.html?username=${username}&role=${role || ''}`;
        } else {
            alert('Please log in before accessing the main page.');
        }
    });

    // Sidebar and overlay functionality
    const loginButton = document.querySelector('.login-button');
    const registerButton = document.querySelector('.register-button');
    const sidebar = document.getElementById('auth-sidebar');
    const overlay = document.getElementById('sidebar-overlay');
    const sidebarTitle = document.getElementById('sidebar-title');
    const authSubmitButton = document.getElementById('auth-submit-button');
    const registerTeacherLabel = document.getElementById('register-teacher-label');
    const registerAsTeacherCheckbox = document.getElementById('register-as-teacher');
    const usernameLabel = document.getElementById('username-label');
    const usernameInput = document.getElementById('username');

    // Login click logic
    loginButton.addEventListener('click', () => {
        sidebar.classList.add('open');
        overlay.classList.add('show');
        sidebarTitle.textContent = 'Login';
        authSubmitButton.textContent = 'Login';

        // Hide checkbox
        registerTeacherLabel.style.display = 'none';
        registerAsTeacherCheckbox.checked = false;
        usernameLabel.textContent = 'Username:';
        usernameInput.placeholder = 'Enter your username';
    });

    // Register click logic
    registerButton.addEventListener('click', () => {
        sidebar.classList.add('open');
        overlay.classList.add('show');
        sidebarTitle.textContent = 'Register';
        authSubmitButton.textContent = 'Register';

        // Display checkbox
        console.log("Showing Register as a Teacher checkbox");
        registerTeacherLabel.style.display = 'flex';
        registerAsTeacherCheckbox.checked = false;
    });

    // switch content when click check box
    registerAsTeacherCheckbox.addEventListener('change', () => {
        if (registerAsTeacherCheckbox.checked) {
            console.log("Register as Teacher checked");
            usernameLabel.textContent = 'Staff ID:';
            usernameInput.placeholder = 'Enter your Staff ID';
        } else {
            console.log("Register as Teacher unchecked");
            usernameLabel.textContent = 'Username:';
            usernameInput.placeholder = 'Enter your username';
        }
    });

    overlay.addEventListener('click', () => {
        sidebar.classList.remove('open');
        overlay.classList.remove('show');
    });

    // Register and Login
    document.getElementById('auth-submit-button').addEventListener('click', async () => {
        const usernameInput = document.getElementById('username').value;
        const passwordInput = document.getElementById('password').value;
        const sidebarTitle = document.getElementById('sidebar-title').textContent;

        if (sidebarTitle === 'Register') {
            const isTeacher = document.getElementById('register-as-teacher').checked;
            const registerButton = document.getElementById('auth-submit-button');

            try {
                if (isTeacher) {
                    // Register as Teacher
                    const response = await fetch('/api/users/registerAdmin', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                            staffId: usernameInput,
                            password: passwordInput,
                        }),
                    });

                    if (response.ok) {
                        alert('Admin registration successful!');
                        document.querySelector('.login-button').click();
                    } else {
                        const errorMessage = await response.text();
                        alert(`Error: ${errorMessage}`);
                    }
                } else {
                    // Normal user registration
                    const response = await fetch('/api/users/register', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                            username: usernameInput,
                            password: passwordInput,
                        }),
                    });

                    if (response.ok) {
                        alert('Registration successful! Redirecting to login...');
                        document.querySelector('.login-button').click();
                    } else {
                        const errorMessage = await response.text();
                        alert(`Error: ${errorMessage}`);
                    }
                }
            } catch (error) {
                alert(`An error occurred: ${error.message}`);
            }
        } else if (sidebarTitle === 'Login') {
            try {
                const response = await fetch('/api/users/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        username: usernameInput,
                        password: passwordInput,
                    }),
                });

                if (response.ok) {
                    // Store username to localStorage
                    const responseData = await response.json();
                    localStorage.setItem('username', responseData.username);
                    localStorage.setItem('role', responseData.role);

                    // Redirect to Home page
                    window.location.href = '../Index.html';
                } else {
                    const errorMessage = await response.text();
                    alert(`Error: ${errorMessage}`); // Warning of error message
                }
            } catch (error) {
                alert(`An error occurred: ${error.message}`);
            }
        }
    });

    // After Login and Logout
    const username = localStorage.getItem('username'); // Check for username
    const welcomeMessage = document.getElementById('welcome-message');
    const logoutButton = document.getElementById('logout-button');

    if (username) {
        // Display personal welcome message if username exists in local
        welcomeMessage.textContent = `Welcome back, ${username}!`;

        // Display Logout button and hide Login and Register button
        loginButton.style.display = 'none';
        registerButton.style.display = 'none';
        logoutButton.style.display = 'block';
    } else {
        // Display default welcome message
        welcomeMessage.textContent = 'Welcome to the Imperial Course Finder';

        loginButton.style.display = 'block';
        registerButton.style.display = 'block';
        logoutButton.style.display = 'none';
    }

    // Logout Functionality
    logoutButton.addEventListener('click', function () {
        // Clear username in local
        localStorage.removeItem('username');

        // Refresh Home page
        window.location.reload();
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const recentCoursesContainer = document.getElementById('recent-courses-container');
    const recentCoursesList = document.getElementById('recent-courses-list');
    const username = localStorage.getItem('username') || 'guest'; // Replace guest with an actual username if needed

    // Fetch recently visited courses
    fetch(`/api/recently-visited/${username}`)
        .then(response => response.json())
        .then(data => {
            if (data.length > 0) {
                // Populate the list with course data
                const listItems = data.map(course => `
                    <li>
                        <a href="/html/InfoPage.html?courseCode=${course.courseCode}">
                            ${course.title} (${course.courseCode})
                        </a>
                    </li>
                `).join('');
                recentCoursesList.innerHTML = listItems;

                // Show the "Recently Viewed Courses" section
                recentCoursesContainer.style.display = 'block';
            } else {
                // Hide the "Recently Viewed Courses" section if no data
                recentCoursesContainer.style.display = 'none';
            }
        })
        .catch(error => {
            console.error('Error fetching recently visited courses:', error);
            // Hide the section on error
            recentCoursesContainer.style.display = 'none';
        });
});




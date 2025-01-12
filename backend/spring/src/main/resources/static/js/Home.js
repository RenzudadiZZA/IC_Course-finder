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

    loginButton.addEventListener('click', () => {
        sidebar.classList.add('open');
        overlay.classList.add('show');
        sidebarTitle.textContent = 'Login';
        authSubmitButton.textContent = 'Login';
    });

    registerButton.addEventListener('click', () => {
        sidebar.classList.add('open');
        overlay.classList.add('show');
        sidebarTitle.textContent = 'Register';
        authSubmitButton.textContent = 'Register';
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
            try {
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
                    document.querySelector('.login-button').click(); // Redirect to Login
                } else {
                    const errorMessage = await response.text();
                    alert(`Error: ${errorMessage}`); // Alert error message
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





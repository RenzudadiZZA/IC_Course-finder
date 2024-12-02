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
    const keywordInput = document.getElementById('course-keywords');

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

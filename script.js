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

// Set the course level dropdown default based on URL
document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const courseLevel = urlParams.get('level'); // Get the "level" parameter from URL

    const courseLevelDropdown = document.getElementById('course-level');

    if (courseLevel && courseLevelDropdown) {
        courseLevelDropdown.value = courseLevel;
    }
});

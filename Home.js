document.addEventListener('DOMContentLoaded', function() {
    const searchBox = document.getElementById('search-box');
    const recentSearches = document.getElementById('recent-searches');

    searchBox.addEventListener('mouseover', function() {
        recentSearches.style.display = 'block';
    });

    searchBox.addEventListener('mouseout', function() {
        recentSearches.style.display = 'none';
    });

    recentSearches.addEventListener('mouseover', function() {
        recentSearches.style.display = 'block';
    });

    recentSearches.addEventListener('mouseout', function() {
        recentSearches.style.display = 'none';
    });
});
  // JavaScript to handle button clicks
  document.getElementById('menu-management-btn').addEventListener('click', function () {
    document.getElementById('dynamic-content').style.display = 'block'; // Show the dynamic div
    window.scrollTo(0, document.getElementById('dynamic-content').offsetTop); // Scroll to the div
});

document.getElementById('close-btn').addEventListener('click', function () {
    document.getElementById('dynamic-content').style.display = 'none'; // Hide the dynamic div
});
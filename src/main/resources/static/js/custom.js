document.addEventListener('DOMContentLoaded', () => {
  const sidebar = document.getElementById('sidebar');
  const overlay = document.getElementById('overlay');
  const openSidebar = document.getElementById('openSidebar');
  const closeSidebar = document.getElementById('closeSidebar');

  // Open sidebar
  openSidebar.addEventListener('click', () => {
    sidebar.classList.add('active');
    overlay.classList.add('active');
  });

  // Close sidebar
  closeSidebar.addEventListener('click', () => {
    sidebar.classList.remove('active');
    overlay.classList.remove('active');
  });

  // Close sidebar when clicking on the overlay
  overlay.addEventListener('click', () => {
    sidebar.classList.remove('active');
    overlay.classList.remove('active');
  });
});
document.addEventListener('DOMContentLoaded', function () {
  const resetPasswordLink = document.getElementById('resetPasswordLink');
  const resetPasswordModal = document.getElementById('resetPasswordModal');

  // Open the modal when "Reset Password" is clicked
  resetPasswordLink.addEventListener('click', function (event) {
    event.preventDefault();
    resetPasswordModal.style.display = 'flex';
  });

  // Close the modal when clicking outside the modal content
  window.addEventListener('click', function (event) {
    if (event.target === resetPasswordModal) {
      resetPasswordModal.style.display = 'none';
    }
  });
});
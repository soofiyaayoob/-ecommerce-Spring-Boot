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
function openProductModalFromData(element) {
  const imageBase64 = element.getAttribute('data-image-base64');
  const name = element.getAttribute('data-name');
  const description = element.getAttribute('data-description');

  // Call the modal-opening function
  openProductModal(imageBase64, name, description);
}

function openProductModal(image, name, description) {
  // Populate modal fields
  document.getElementById('modalProductImage').src = image;
  document.getElementById('modalProductName').innerText = name;
  document.getElementById('modalProductDescription').innerText = description;

  // Display the modal
  const modal = document.getElementById('productDetailsModal');
  modal.style.display = 'block';
}

// Close modal on clicking outside the content
window.onclick = function(event) {
  const modal = document.getElementById('productDetailsModal');
  if (event.target === modal) {
    modal.style.display = 'none';
  }
};
function closeModal() {
  const modal = document.getElementById('productDetailsModal');
  modal.style.display = 'none';
}


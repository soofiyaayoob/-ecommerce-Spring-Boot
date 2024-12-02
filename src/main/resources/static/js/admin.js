// Function to show the Add Admin form
function showAddAdminForm() {
    document.getElementById("add-admin-overlay").style.display = "flex";
    document.getElementById("add-admin-form").style.display = "block";
}

// Function to hide the Add Admin form
function hideAddAdminForm() {
    document.getElementById("add-admin-overlay").style.display = "none";
    document.getElementById("add-admin-form").style.display = "none";
}

// Close the Add Admin form when clicking outside of it
document.getElementById("add-admin-overlay").addEventListener("click", function(event) {
    if (event.target === this) {
        hideAddAdminForm();
    }
});


    // Function to show the Add Restaurant form
    function showAddRestaurantForm() {
        document.getElementById("add-restaurant-overlay").style.display = "flex";
        document.getElementById("add-restaurant-form").style.display = "block";
    }

    // Function to hide the Add Restaurant form
    function hideAddRestaurantForm() {
        document.getElementById("add-restaurant-overlay").style.display = "none";
        document.getElementById("add-restaurant-form").style.display = "none";
    }

    // Close the Add Restaurant form when clicking outside of it
    document.getElementById("add-restaurant-overlay").addEventListener("click", function(event) {
        if (event.target === this) {
            hideAddRestaurantForm();
        }
    });




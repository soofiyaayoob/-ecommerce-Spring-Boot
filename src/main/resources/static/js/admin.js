function showAddProductForm() {
    document.getElementById("add-product-overlay").style.display = "flex";
    document.getElementById("add-product-form").style.display = "block";
}

function hideAddProductForm() {
    document.getElementById("add-product-overlay").style.display = "none";
    document.getElementById("add-product-form").style.display = "none";
}



// Close the form when clicking outside of it
document.getElementById("add-product-overlay").addEventListener("click", function(event) {
    if (event.target === this) {
        hideAddProductForm();
    }
});


function showAddAdminForm() {
    document.getElementById("add-admin-overlay").style.display = "flex";
    document.getElementById("add-admin-form").style.display = "block";
}

function hideAddAdminForm() {
    document.getElementById("add-admin-overlay").style.display = "none";
    document.getElementById("add-admin-form").style.display = "none";
}

document.getElementById("add-admin-overlay").addEventListener("click", function(event) {
    if (event.target === this) {
        hideAddAdminForm();
    }
});
// Function to show the Add Category overlay
function showAddCategoryForm() {
    document.getElementById('add-category-overlay').style.display = 'flex';
    document.getElementById('add-category-form').style.display = 'block';
}

// Function to hide the Add Category overlay
function hideAddCategoryForm() {
    document.getElementById('add-category-overlay').style.display = 'none';
    document.getElementById('add-category-form').style.display = 'none';
}

// Close overlay when clicking outside the form
document.getElementById("add-category-overlay").addEventListener("click", function(event) {
    if (event.target === this) {
        hideAddCategoryForm(); // Changed to `hideAddCategoryForm`
    }
});





// Function to show the Add Offer form
function showAddOfferForm() {
    document.getElementById("add-offer-overlay").style.display = "flex";
    document.getElementById("add-offer-form").style.display = "block";
}

// Function to hide the Add Offer form
function hideAddOfferForm() {
    document.getElementById("add-offer-overlay").style.display = "none";
    document.getElementById("add-offer-form").style.display = "none";
}

// Event listener to close the Add Offer form if clicked outside the form (on the overlay)
document.getElementById("add-offer-overlay").addEventListener("click", function(event) {
    if (event.target === this) {
        hideAddOfferForm();
    }
});


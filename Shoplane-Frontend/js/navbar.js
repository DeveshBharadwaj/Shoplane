// js/navbar.js

function checkNavbarSession() {
    const authContainer = document.getElementById("auth-container");
    const userData = localStorage.getItem("user");

    if (userData && userData !== "undefined" && userData !== "null") {
        try {
            const user = JSON.parse(userData);
            
            if (user && user.name && authContainer) {
                // Get the user's first name
                const firstName = user.name.split(' ')[0];

                // Update the navbar with the greeting and a red Logout button
                authContainer.innerHTML = `
                    <div class="d-inline-flex align-items-center">
                        <span class="fw-bold me-3 text-white">
                            <i class="fas fa-user-circle me-1"></i> Hi, ${firstName}
                        </span>
                        <a href="#" class="btn btn-danger btn-sm px-3 fw-bold" onclick="logoutUser(event)">
                            Logout
                        </a>
                    </div>
                `;
                return;
            }
        } catch (e) {
            console.error("Error parsing user session:", e);
        }
    }
    
    // Fallback: Show default Login/Register button if not logged in
    if (authContainer) {
        authContainer.innerHTML = `
            <a class="btn btn-outline-light btn-sm px-3 fw-bold" href="login.html" id="auth-link">Login / Register</a>
        `;
    }
}

// Global logout function
window.logoutUser = function(event) {
    if (event) event.preventDefault();
    
    // Remove user data from localStorage
    localStorage.removeItem("user");
    alert("You have been logged out successfully!");
    
    // Redirect cleanly to the homepage
    window.location.replace("index.html");
};

// Run checking logic on load
document.addEventListener("DOMContentLoaded", checkNavbarSession);
window.addEventListener("load", checkNavbarSession);
const AUTH_BASE_URL = "http://localhost:8080";

// Toggles visibility between Login and Register boxes
function toggleAuth() {
    const loginBox = document.getElementById("login-box");
    const registerBox = document.getElementById("register-box");

    if (loginBox && registerBox) {
        // Toggle the classes depending on your CSS naming rules
        loginBox.classList.toggle("auth-box-hidden");
        registerBox.classList.toggle("auth-box-hidden");
        loginBox.classList.toggle("hidden");
        registerBox.classList.toggle("hidden");
    }
}

// Make toggleAuth accessible globally to HTML onclick events
window.toggleAuth = toggleAuth;

// DOM Initialization wrapper
document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("login-form");
    const registerForm = document.getElementById("register-form");

    // ---- REGISTER USER TO BACKEND (With Auto-Login) ----
    if (registerForm) {
        registerForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const name = document.getElementById("reg-name").value;
            const email = document.getElementById("reg-email").value;
            const password = document.getElementById("reg-password").value; // Contact field completely removed

            try {
                const response = await fetch(`${AUTH_BASE_URL}/users/register`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ name, email, password })
                });

                if (response.ok) {
                    const registeredUser = await response.json();

                    // 💾 Auto-Login: Save the session data immediately to local storage
                    localStorage.setItem("user", JSON.stringify({
                        id: registeredUser.id,
                        name: registeredUser.name,
                        email: registeredUser.email
                    }));

                    alert(`🎉 Account created! Welcome, ${registeredUser.name}!`);
                    
                    // Redirect straight to the home page!
                    window.location.replace("index.html"); 
                } else {
                    const errorText = await response.text();
                    alert("Registration failed: " + errorText);
                }
            } catch (error) {
                console.error("Registration endpoint error:", error);
                alert("Server connection failed. Is your backend running?");
            }
        });
    }

    // ---- LOGIN USER TO BACKEND ----
    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const email = document.getElementById("login-email").value;
            const password = document.getElementById("login-password").value;

            try {
                const response = await fetch(`${AUTH_BASE_URL}/users/login`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ email, password })
                });

                if (response.ok) {
                    const loggedInUser = await response.json(); 
                    
                    // Core Session Step: Store the session payload
                    localStorage.setItem("user", JSON.stringify({
                        id: loggedInUser.id,
                        name: loggedInUser.name,
                        email: loggedInUser.email
                    }));

                    alert(`Welcome back, ${loggedInUser.name}!`);
                    
                    // Redirect straight to the home page
                    window.location.replace("index.html"); 
                } else {
                    alert("Invalid email credentials or password matching error!");
                }
            } catch (error) {
                console.error("Login endpoint error:", error);
                alert("Could not complete authentication validation sequence.");
            }
        });
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const searchForm = document.getElementById("search-form");
    const searchInput = document.getElementById("search-input");

    if (!searchForm || !searchInput) return;

    searchForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const query = searchInput.value.trim();

        if (!query) return;

        try {
            const response = await fetch(`http://localhost:8080/products/search?query=${query}`);
            const filteredProducts = await response.json();
            
            if (typeof displayProducts === "function") {
                displayProducts(filteredProducts); 
            }
        } catch (error) {
            console.error(error);
        }
    });
});
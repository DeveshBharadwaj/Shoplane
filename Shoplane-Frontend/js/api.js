const BASE_URL = "http://localhost:8080";

async function loadProducts(searchQuery = "") {
    try {
        let url = `${BASE_URL}/products`;
        
        if (searchQuery) {
            url = `${BASE_URL}/products/search?query=${encodeURIComponent(searchQuery)}`;
        }

        const response = await fetch(url);
        const products = await response.json();
        
        let trendingList = document.getElementById("trending-products");
        let clothingList = document.getElementById("clothing-products");
        let electronicsList = document.getElementById("electronics-products");

        if (trendingList) trendingList.innerHTML = "";
        if (clothingList) clothingList.innerHTML = "";
        if (electronicsList) electronicsList.innerHTML = "";

        products.forEach((product) => {
            const safeName = product.name.replace(/'/g, "\\'");

            let productCard = `
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100">
                        <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title">${product.name}</h5>
                            <p class="card-text">${product.description}</p>
                            <p class="price"><strong>₹${product.price}</strong></p>
                            <button class="btn btn-primary mt-auto"
                            onclick="addToCart(${product.id}, '${safeName}', ${product.price}, '${product.imageUrl}')">
                            Add to Cart
                            </button>
                        </div>
                    </div>
                </div>
            `;

            if (product.category === "Clothing" && clothingList) {
                clothingList.innerHTML += productCard;
            } else if (product.category === "Electronics" && electronicsList) {
                electronicsList.innerHTML += productCard;
            } else if (trendingList) {
                trendingList.innerHTML += productCard;
            }
        });
    } catch (error) {
        console.error(error);
    }
}

window.loadProducts = loadProducts;

window.addToCart = function(id, name, price, imageUrl) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    price = parseFloat(price);
    
    let itemIndex = cart.findIndex((item) => item.id === id);
    if (itemIndex !== -1) {
        cart[itemIndex].quantity += 1;
    } else {
        cart.push({
            id: id,
            name: name,
            price: price,
            imageUrl: imageUrl,
            quantity: 1
        });      
    }
    localStorage.setItem("cart", JSON.stringify(cart));
    window.updateCartCounter();
    alert(`${name} added to cart!`);
};

window.updateCartCounter = function() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    let badge = document.querySelector(".cart-badge");
    if (badge) {
        let totalCount = cart.reduce((sum, item) => sum + item.quantity, 0);
        badge.innerText = totalCount;
    }
};

document.addEventListener("DOMContentLoaded", () => {
    loadProducts();
    window.updateCartCounter();
});


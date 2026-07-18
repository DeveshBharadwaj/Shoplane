(() => {
    const BASE_URL = "http://localhost:8080";

window.loadCart = function() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    let cartItems = document.getElementById("cart-items");
    let totalAmountElement = document.getElementById("total-amount");
    let totalAmount = 0;

    if (!cartItems) return; // Guard logic: exits cleanly if we are on index.html
    cartItems.innerHTML = "";

    cart.forEach((item, index) => {
        let itemTotal = item.price * item.quantity;
        totalAmount += itemTotal;

        cartItems.innerHTML += `
            <tr>
                <td><img src="${item.imageUrl}" width="50" style="border-radius: 5px;"></td>
                <td class="align-middle">${item.name}</td>
                <td class="align-middle">₹ ${item.price}</td>
                <td class="align-middle">
                    <button class="btn btn-sm btn-secondary me-2" onclick="changeQuantity(${index},-1)">-</button>
                    <span>${item.quantity}</span>
                    <button class="btn btn-sm btn-secondary ms-2" onclick="changeQuantity(${index},1)">+</button>
                </td>
                <td class="align-middle">₹ ${itemTotal.toFixed(2)}</td>
                <td class="align-middle"><button class="btn btn-danger btn-sm" onclick="removeFromCart(${index})">X</button></td>
            </tr>
        `;
    });
    
    if (totalAmountElement) {
        totalAmountElement.innerText = totalAmount.toFixed(2);
    }
};

window.changeQuantity = function(index, change) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    if (!cart[index]) return;

    cart[index].quantity += change;
    if (cart[index].quantity <= 0) {
        cart.splice(index, 1);
    }
    
    localStorage.setItem("cart", JSON.stringify(cart));
    window.loadCart();
    window.updateCartCounter();
};

window.removeFromCart = function(index) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    cart.splice(index, 1);
    
    localStorage.setItem("cart", JSON.stringify(cart));
    window.loadCart();
    window.updateCartCounter();
};

window.updateCartCounter = function() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    let badge = document.querySelector(".cart-badge");
    if (badge) {
        let totalCount = cart.reduce((sum, item) => sum + item.quantity, 0);
        badge.innerText = totalCount;
    }
};

window.checkout = async function() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    if (cart.length === 0) return alert("Your cart is empty!");

    // 🚨 DYNAMIC USER CHECK: Get logged in user's ID from localStorage
    const userData = localStorage.getItem("user");
    if (!userData) {
        alert("Please log in to proceed to checkout!");
        window.location.href = "login.html";
        return;
    }

    const user = JSON.parse(userData);
    const userId = user.id; // ⚡ Dynamic User ID (e.g. 4 for Devesh)
    
    const totalAmount = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);

    try {
        const response = await fetch(`${BASE_URL}/payments/create`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ amount: totalAmount }) 
        });
        if (!response.ok) throw new Error("Payment initialization failed.");
        const razorpayOrderId = await response.text();

        const options = {
            "key": "rzp_test_TE1UQhDtwGM3gW", 
            "amount": totalAmount * 100,      
            "currency": "INR",
            "name": "SHOPIFY Marketplace",
            "description": "Checkout Transaction Payment",
            "order_id": razorpayOrderId,
            "config": {
                "display": {
                    "blocks": {
                        "upi": {
                            "name": "Pay via UPI",
                            "instruments": [
                                {
                                     "method": "upi"
                                }
                            ]
                        }
                    },
                    "sequence": ["block.upi", "block.other"],
                    "preferences": {
                        "show_default_blocks": true
                    }
                }    
            },
            "handler": async function (razorpayResponse) {
                const productQuantities = {};
                cart.forEach(item => productQuantities[item.id] = item.quantity);

                // ⚡ Places order using the dynamic userId
                const orderResponse = await fetch(`${BASE_URL}/orders/place/${userId}`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        productQuantities: productQuantities,
                        totalAmount: totalAmount,
                        razorpayPaymentId: razorpayResponse.razorpay_payment_id
                    })
                });

                if (orderResponse.ok) {
                    alert("🛒 Order Placed successfully!");
                    localStorage.removeItem("cart");
                    window.location.href = "index.html";
                }
            }
        };
        new Razorpay(options).open();
    } catch (error) {
        console.error("Checkout error:", error);
        alert("Checkout failed. Make sure the backend is running!");
    }
};

document.addEventListener("DOMContentLoaded", () => {
    window.loadCart();
    window.updateCartCounter();
})
})();
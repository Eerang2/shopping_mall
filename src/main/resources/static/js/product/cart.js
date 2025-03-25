function fetchCartData() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    let productIds = cart.map(item => item.productId);

    $.ajax({
        type: "POST",
        url: "/api/product/cart/fetch",
        contentType: "application/json",
        data: JSON.stringify(productIds),
        success: function (response) {
            renderCart(response, cart);
        },
        error: function () {
            alert("상품 정보를 불러오는데 실패했습니다.");
        }
    });
}

function renderCart(products, cart) {
    let cartItemsDiv = document.getElementById("cart-items");
    cartItemsDiv.innerHTML = "";

    if (cart.length === 0) {
        cartItemsDiv.innerHTML = "<p>장바구니가 비었습니다.</p>";
        return;
    }

    cart.forEach((item, index) => {
        let product = products.find(p => Number(p.key) === Number(item.productId));

        if (!product) return;

        cartItemsDiv.innerHTML += `
                    <div class="cart-item">
                        <input type="checkbox" data-id="${product.key}" class="select-item" data-price="${product.productPrice}" data-index="${index}" onclick="calculateTotal()">
                        <img src="${'/images/' + product.productImage}" alt="상품 이미지">
                        <span>${product.productName} - ${product.productPrice}원</span>
                        <button class="quantity-btn" onclick="updateQuantity(${index}, -1)">➖</button>
                        <span>${item.quantity}</span>
                        <button class="quantity-btn" onclick="updateQuantity(${index}, 1)">➕</button>
                    </div>
                `;
    });
}

function updateQuantity(index, change) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    if (cart[index]) {
        cart[index].quantity += change;
        if (cart[index].quantity <= 0) {
            cart.splice(index, 1);
        }
        localStorage.setItem("cart", JSON.stringify(cart));
        fetchCartData();
    }
}

function calculateTotal() {
    let selectedItems = document.querySelectorAll(".select-item:checked");

    if (selectedItems.length === 0) {
        document.getElementById("totalAmount").innerText = "0";
        return;
    }

    let total = 0;
    let cart = JSON.parse(localStorage.getItem("cart")) || [];

    selectedItems.forEach(item => {
        let productId = item.getAttribute("data-id");
        let price = Number(item.getAttribute("data-price")) || 0;
        let cartItem = cart.find(c => Number(c.productId) === Number(productId));

        let quantity = cartItem ? cartItem.quantity : 0;

        total += price * quantity;
    });
    document.getElementById("totalAmount").innerText = total.toLocaleString();
}


function checkout() {
    const token = getCookie("JWT_TOKEN");
    if (!token) {
        alert("로그인이 필요합니다! 🔒");
        window.location.href = "/login";
        return;
    }

    let selectedItems = [];
    document.querySelectorAll(".select-item:checked").forEach(item => {
        let index = item.getAttribute("data-index");
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        if (cart[index]) {
            selectedItems.push(cart[index]);
        }
    });

    if (selectedItems.length === 0) {
        alert("결제할 상품을 선택하세요.");
        return;
    }

    localStorage.setItem("selectedCart", JSON.stringify(selectedItems));
    window.location.href = "/checkout";
}

function getCookie(name) {
    let cookies = document.cookie.split("; ");
    let cookie = cookies.find(row => row.startsWith(name + "="));
    return cookie ? cookie.split("=")[1] : null;
}

fetchCartData();
// 페이지 로딩 시 장바구니 데이터를 서버에서 가져옵니다.
function fetchCartData() {
    // 서버에서 장바구니 데이터를 요청
    $.ajax({
        type: "POST",
        url: "/api/product/cart/fetch",  // 서버에 GET 요청으로 장바구니 데이터를 받아옴
        success: function (response) {
            renderCart(response);  // 서버에서 받아온 데이터로 장바구니 렌더링
        },
        error: function () {
            alert("장바구니 정보를 불러오는데 실패했습니다.");
        }
    });
}

// 장바구니 아이템을 HTML로 렌더링하는 함수
function renderCart(cart) {
    let cartItemsDiv = document.getElementById("cart-items");
    cartItemsDiv.innerHTML = "";

    if (cart.length === 0) {
        cartItemsDiv.innerHTML = "<p>장바구니가 비었습니다.</p>";
        return;
    }

    cart.forEach((item, index) => {
        cartItemsDiv.innerHTML += `
            <div class="cart-item">
                <input type="checkbox" data-id="${item.productKey}" class="select-item" data-price="${item.productPrice}" data-index="${index}" onclick="calculateTotal()">
                <img src="${'/images/' + item.productImage}" alt="상품 이미지">
                <span>${item.productName} - ${item.productPrice}원</span>
                <button class="quantity-btn" onclick="updateQuantity(this, ${item.productKey}, -1)">➖</button>
                <span class="quantity-display">${item.quantity}</span>
                <button class="quantity-btn" onclick="updateQuantity(this, ${item.productKey}, 1)">➕</button>
            </div>
        `;
    });
}

// 장바구니 아이템의 수량을 업데이트하는 함수
function updateQuantity(button, productId, change) {
    const itemContainer = button.parentElement;
    const quantityDisplay = itemContainer.querySelector('.quantity-display');
    const currentQuantity = parseInt(quantityDisplay.innerText);
    const newQuantity = currentQuantity + change;

    if (newQuantity <= 0) {
        return;
    }

    // 화면에 즉시 반영
    quantityDisplay.innerText = newQuantity;
    calculateTotal();

    // DB 업데이트
    $.ajax({
        type: "POST",
        url: "/api/product/cart/update",
        contentType: "application/json",
        data: JSON.stringify({
            productKey: productId,
            changeQuantity: change
        }),
        error: function () {
            // DB 업데이트 실패시 원래 수량으로 복구
            quantityDisplay.innerText = currentQuantity;
            calculateTotal();
            alert("장바구니 수량 업데이트에 실패했습니다.");
        }
    });
}

// 총 금액 계산 함수
function calculateTotal() {
    let selectedItems = document.querySelectorAll(".select-item:checked");

    if (selectedItems.length === 0) {
        document.getElementById("totalAmount").innerText = "0";
        return;
    }

    let total = 0;

    selectedItems.forEach(item => {
        let price = parseInt(item.getAttribute("data-price")) || 0;
        let quantity = parseInt(item.parentElement.querySelector(".quantity-display").innerText) || 0;
        total += price * quantity;
    });

    document.getElementById("totalAmount").innerText = total.toLocaleString();
}

// 결제 진행 함수
function checkout() {
    let selectedItems = [];
    document.querySelectorAll(".select-item:checked").forEach(item => {
        let productId = item.getAttribute("data-id");
        let quantity = item.parentElement.querySelector(".quantity-btn + span").innerText;
        selectedItems.push({productId: productId, quantity: quantity});
    });

    if (selectedItems.length === 0) {
        alert("결제할 상품을 선택하세요.");
        return;
    }

    // 선택된 상품을 백엔드로 전달하고 결제 페이지로 이동
    let queryString = selectedItems.map(item => `products=${item.productId},${item.quantity}`).join("&");
    window.location.href = `/payment/cart?${queryString}`;
}

fetchCartData();  // 페이지 로딩 시 장바구니 데이터 불러오기

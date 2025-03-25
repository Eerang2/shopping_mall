// 🛒 장바구니 담기 (LocalStorage 저장)
function addToCart(productId) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    let item = cart.find(item => item.productId === productId);

    if (item) {
        item.quantity += 1; // 이미 장바구니에 있으면 수량을 1 증가
    } else {
        item = {productId, quantity: 1}; // 새 상품 추가
        cart.push(item);
    }

    // LocalStorage에 장바구니 업데이트
    localStorage.setItem("cart", JSON.stringify(cart));

    // 쿠키에서 JWT 토큰 가져오기
    const token = getCookie("JWT_TOKEN");

    const formData = {
        productId: productId,
        quantity: item.quantity
    };

    if (token) {
        console.log(item.quantity)
        // 로그인 상태에서만 DB에 저장
        $.ajax({
            type: "POST",
            url: "/api/product/cart",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (response) {
                alert("장바구니에 추가되었습니다! ✅");
                console.log("DB 저장완료");
            },
            error: function (xhr) {
                let errorMsg = xhr.responseJSON?.message || "장바구니 저장에 실패했습니다.";
                alert(errorMsg);
            }
        });
    } else {
        alert("장바구니에 추가되었습니다! ✅"); // 비로그인 상태에서는 LocalStorage에만 저장
    }
}

// 💳 바로 구매 (로그인 여부 확인 후 이동)
function buyNow(productId) {
    // 🍪 JWT 쿠키 확인
    const token = getCookie("JWT_TOKEN");
    console.log(token)

    if (!token) {
        alert("로그인이 필요합니다! 🔒");
        window.location.href = "/login"; // 로그인 페이지로 이동
        return;
    }

    // ✅ 로그인된 상태면 구매 페이지로 이동
    window.location.href = `/payment/` + productId;
}

// 🍪 쿠키 가져오는 함수
function getCookie(name) {
    let cookies = document.cookie.split("; ");
    let cookie = cookies.find(row => row.startsWith(name + "="));
    return cookie ? cookie.split("=")[1] : null;
}

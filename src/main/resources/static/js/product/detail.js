// 🛒 장바구니 담기 (LocalStorage 저장)
function addToCart(productId) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    let item = cart.find(item => item.productId === productId);

    if (item) {
        item.quantity += 1;
    } else {
        cart.push({productId, quantity: 1});
    }

    localStorage.setItem("cart", JSON.stringify(cart));
    alert("장바구니에 추가되었습니다! ✅");
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

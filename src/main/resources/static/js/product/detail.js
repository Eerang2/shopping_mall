function addToCart(productId) {

    const token = getCookie("JWT_TOKEN");
    let quantity = $("#quantity").val().trim()

    const formData = {
        productKey: productId,
        quantity: quantity
    };

    if (token) {
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
        alert("로그인 유저만 이용가능합니다. ");
    }
}

function buyNow(productId) {
    const token = getCookie("JWT_TOKEN");
    if (!token) {
        alert("로그인이 필요합니다! 🔒");
        window.location.href = "/login";
        return;
    }

    // ✅ 수량 가져오기 (value는 함수 아님!)
    const quantity = document.getElementById("quantity").value;

    // ✅ 단일 상품에 맞는 query string 생성
    const queryString = `products=${productId},${quantity}`;

    // ✅ 결제 페이지로 이동
    window.location.href = `/payment/direct/checkout?${queryString}`;
}

// 🍪 쿠키 가져오는 함수
function getCookie(name) {
    let cookies = document.cookie.split("; ");
    let cookie = cookies.find(row => row.startsWith(name + "="));
    return cookie ? cookie.split("=")[1] : null;
}

function increaseQuantity() {
    const quantityInput = document.getElementById('quantity');
    quantityInput.value = parseInt(quantityInput.value) + 1;
}

function decreaseQuantity() {
    const quantityInput = document.getElementById('quantity');
    if (parseInt(quantityInput.value) > 1) {
        quantityInput.value = parseInt(quantityInput.value) - 1;
    }
}
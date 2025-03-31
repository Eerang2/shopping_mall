document.addEventListener("DOMContentLoaded", function () {
    // 모든 상품 카드를 선택
    const productCards = document.querySelectorAll(".product-card");

    productCards.forEach(card => {
        card.addEventListener("click", function () {
            // 해당 상품의 ID 가져오기
            const productId = this.getAttribute("data-id");

            // 상세 페이지로 이동
            if (productId) {
                window.location.href = `/product/detail/${productId}`;
            }
        });
    });
});
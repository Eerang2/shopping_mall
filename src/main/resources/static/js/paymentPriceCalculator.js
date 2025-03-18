$(document).ready(function () {
    function updateFinalPrice() {
        let price = parseInt($("#productPrice").text());
        let couponDiscount = parseInt($("#couponSelect").val());
        let gradeDiscount = parseInt($("#gradeDiscount").text());
        let eventDiscount = parseInt($("#eventDiscount").text());

        let finalPrice = price - couponDiscount - gradeDiscount - eventDiscount;
        if (couponDiscount === 0) {
            $("#couponDiscount").text(couponDiscount);
        } else {
            $("#couponDiscount").text("- " +couponDiscount);
        }
        $("#finalPrice").text(finalPrice);
    }

    $("#couponSelect").on("change", function () {
        updateFinalPrice();
    });

    updateFinalPrice(); // 초기 로딩 시 계산
});
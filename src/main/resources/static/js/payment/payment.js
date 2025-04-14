// 📌 결제하기 버튼 클릭 시 AJAX 요청
$("#payButton").click(function () {
    let address = $("#address").text();
    let detailAddress = $("#detailAddress").text();
    const email = $("#email").val();
    let totalPrice = parseFloat($("#totalPrice").text().replace("원", "").replace(",", ""));
    console.log(totalPrice)

    if (!address || !detailAddress || address === "" || detailAddress === "") {
        alert("배송지를 입력해주세요!");
        return;
    }

    let products = [];
    $("#productList p").each(function () {
        products.push({
            productKey: $(this).find(".product-key").val(),
            quantity: $(this).find(".product-quantity").val()
        });
    });

    let orderData = {
        totalPrice: totalPrice,
        products: products
    };

    // 서버에 주문 데이터 전송
    $.ajax({
        type: "POST",
        url: "/api/payment/cart/checkout",
        contentType: "application/json",
        data: JSON.stringify(orderData),
        success: function (response) {
            // 결제 요청 실행
            requestPay({
                userName: response.userName,
                userEmail: email,
                merchantUid: response.merchantUid,
                productName: response.productName,
                amount: response.amount
            });
        },
        error: function () {
            alert("주문 처리 중 오류가 발생했습니다.");
        }
    });
});

function requestPay(res) {
    const {IMP} = window;
    IMP.init('imp67844376');

    IMP.request_pay({
        pg: 'html5_inicis.INIpayTest',
        pay_method: 'card',
        merchant_uid: res.merchantUid,
        name: res.productName,
        amount: res.amount,
        buyer_name: res.userName,
        buyer_email: res.userEmail
    }, function (rsp) {
        if (rsp.success) {
            // 결제 성공 시 검증 요청
            $.ajax({
                url: '/api/payment/verifyImport/' + rsp.imp_uid,
                method: 'POST',
                success: function (amount) {
                    if (res.amount === amount) {
                        alert('결제가 완료되었습니다!');
                        window.location.href = "/payment/success";
                    } else {
                        alert('결제 금액 검증 실패');
                        cancelPayment(res.merchantUid);
                    }
                },
                error: function (error) {
                    console.error('결제 검증 오류:', error);
                    cancelPayment(res.merchantUid);
                }
            });
        } else {
            console.error('결제 실패:', rsp.error_msg); // 🛠 실패 원인 출력
            cancelPayment(res.merchantUid);
            alert('결제가 취소되었습니다.');
        }
    });
}

function cancelPayment(merchantUid) {
    $.ajax({
        url: "/api/payment/cancel/" + merchantUid,
        method: "DELETE",
        error: function () {
            console.error('결제 취소 처리 실패');
        }
    });
}
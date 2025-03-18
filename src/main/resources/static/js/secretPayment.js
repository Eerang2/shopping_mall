$(document).ready(function () {
    const merchantUid = generateMerchantUID();
    const productName = $("#productName").text();
    const grade =  $("#grade").text();
    const productPrice = $("#productPrice").text();
    let finalPrice = 0;

    // 등급 할인과 이벤트 할인 (고정값)
    const gradeDiscount = $("#gradeDiscount").text();
    const eventDiscount = $("#eventDiscount").text();

    // 최종 결제 금액을 업데이트하는 함수
    function updateFinalPrice() {
        const couponDiscount = $("#couponSelect").val() || 0; // 쿠폰 값이 없으면 0으로 설정
        finalPrice = productPrice - couponDiscount - gradeDiscount - eventDiscount;

        // 최종 결제 금액 업데이트
        $("#couponDiscount").text(couponDiscount);
        $("#finalPrice").text(finalPrice);
    }

    updateFinalPrice();

    // 쿠폰 선택 변경 시 최종 금액 업데이트
    $("#couponSelect").change(function () {
        updateFinalPrice();
    });

    // 결제 요청 함수
    function requestPay(res) {
        const { IMP } = window;  // 아임포트 객체

        // 가맹점 식별 코드 초기화
        IMP.init('imp67844376');  // 여기에 실제 가맹점 식별 코드 입력

        // 결제 요청
        IMP.request_pay(
            {
                pg: 'html5_inicis.INIpayTest',  // PG사 코드 및 상점 ID (테스트 환경)
                pay_method: 'card',  // 결제 수단 (카드 결제)
                merchant_uid: merchantUid,
                name: productName,  // 상품명
                amount: res.finalPrice,  // 결제 금액
                buyer_name: res.userName,  // 구매자 이름
            },
            function (rsp) {  // 결제 결과 처리 콜백 함수
                if (rsp.success) {
                    // 결제 성공 시 서버로 결제 검증 요청
                    $.ajax({
                        url: 'http://localhost:8080/verifyIamport/' + rsp.imp_uid,  // 검증 URL
                        method: 'POST',
                        success: function (data) {
                            // 결제 금액 검증
                            if (rsp.paid_amount === data.response.amount) {
                                alert('결제 성공');
                            } else {
                                alert('결제 실패');
                            }
                        },
                        error: function (error) {
                            console.error('Error while verifying payment:', error);
                            alert('결제 실패');
                        }
                    });
                } else {
                    // 결제 실패 시
                    alert('결제 실패');
                }
            }
        );
    }

    // 🛠 결제 버튼 클릭 시, 백엔드에서 검증 후 결제창 띄우기
    $('#payButton').on('click', function () {
        $.ajax({
            url: '/api/payment/check', // 백엔드 검증 API
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                productName: productName,
                userGrade: grade,
                productPrice: productPrice,
                couponDiscount: $("#couponSelect").val() || 0,
                gradeDiscount: gradeDiscount,
                eventDiscount: eventDiscount,
                finalPrice: finalPrice

            }),
            success: function (response) {
                if (response) {
                    // 검증 성공 시 결제창 띄우기
                    requestPay(response);
                } else {
                    console.error("AJAX 요청 실패:", error);
                    alert("결제 검증 실패: " + response.message);
                }
            },
            error: function (error) {
                console.error('결제 검증 중 오류 발생:', error);
                alert("결제 검증 중 오류 발생");
            }
        });
    });
});



function generateMerchantUID() {
    const prefix = "GU_";
    const today = new Date();

    // 오늘 날짜를 "YYMMDD" 형식으로 만듭니다.
    const year = String(today.getFullYear()).slice(2); // '24'
    const month = String(today.getMonth() + 1).padStart(2, '0'); // '08'
    const day = String(today.getDate()).padStart(2, '0'); // '29'

    const datePart = year + month + day; // '240829'

    // 순차적인 숫자를 만들기 위한 기본 값 (예: '00001')
    let sequence = localStorage.getItem('sequence');

    if (sequence === null) {
        sequence = 1;
    } else {
        sequence = parseInt(sequence) + 1;
    }

    localStorage.setItem('sequence', sequence); // 현재 시퀀스를 로컬 스토리지에 저장

    const sequenceString = String(sequence).padStart(5, '0'); // '00001'

    // 무작위 4자리 숫자 생성
    const randomPart = String(Math.floor(Math.random() * 10000)).padStart(4, '0');

    // 완성된 주문번호를 반환합니다.
    return prefix + datePart + sequenceString + randomPart;

}


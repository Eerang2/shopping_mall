$(document).ready(function() {
    $("#loginForm").submit(function(event) {
        event.preventDefault(); // 기본 폼 제출 막기

        let sellerId = $("#sellerId").val().trim();
        let password = $("#password").val().trim();

        // 유효성 검사
        if (sellerId === "" || password === "") {
            alert("아이디와 비밀번호를 입력하세요.");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/api/seller/login",
            contentType: "application/json",
            data: JSON.stringify({ sellerId: sellerId, password: password }),
            success: function(response) {
                // 토큰 저장 (로컬스토리지 또는 쿠키 사용 가능)
                console.log(response);
                alert("로그인 성공!");
                window.location.href = "/"; // 메인 페이지로 이동
            },
            error: function(xhr) {
                console.log(xhr)
                console.log("응답 텍스트:", xhr.responseText);
                alert("로그인 중 알 수 없는 오류가 발생했습니다.");
            }
        });
    });
});

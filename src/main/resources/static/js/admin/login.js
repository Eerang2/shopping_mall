$(document).ready(function() {
    $("#loginForm").submit(function(event) {
        event.preventDefault(); // 기본 폼 제출 막기

        let adminId = $("#adminId").val().trim();
        let password = $("#password").val().trim();

        // 유효성 검사
        if (adminId === "" || password === "") {
            alert("아이디와 비밀번호를 입력하세요.");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/api/admin/login",
            contentType: "application/json",
            data: JSON.stringify({ adminId: adminId, password: password }),
            success: function(response) {
                // 토큰 저장 (로컬스토리지 또는 쿠키 사용 가능)

                alert("로그인 성공!");
                window.location.href = "/admin"; // 메인 페이지로 이동
            },
            error: function(xhr) {
                let errorMsg = xhr.responseJSON?.message || "로그인 실패. 다시 시도해주세요.";
                alert(errorMsg);
            }
        });
    });
});

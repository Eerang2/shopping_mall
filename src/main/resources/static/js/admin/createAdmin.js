$(document).ready(function() {
    $("#create-admin").submit(function(event) {
        event.preventDefault(); // 기본 폼 제출 막기

        let adminName = $("#adminId").val().trim();
        let role = $("#adminRole").val();


        $.ajax({
            type: "POST",
            url: "/api/admin/create",
            contentType: "application/json",
            data: JSON.stringify({ adminName: adminName, role: role }),
            success: function(response) {
                // 토큰 저장 (로컬스토리지 또는 쿠키 사용 가능)

                alert("생성 성공!");
                window.location.href = "/admin/index"; // 메인 페이지로 이동
            },
            error: function(xhr) {
                let errorMsg = xhr.responseJSON?.message || "로그인 실패. 다시 시도해주세요.";
                alert(errorMsg);
            }
        });
    });
});
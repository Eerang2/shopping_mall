$(document).ready(function () {
    let isUsernameAvailable = false;

    // ✅ 비밀번호 입력 실시간 검사
    $("#password").on("input", function () {
        let password = $(this).val().trim();

        if (password.length < 8) {
            $("#passwordCheckResult").text("비밀번호는 8자 이상이어야 합니다.").css("color", "red");
        } else if (!/.*\d.*/.test(password) || !/.*[A-Za-z].*/.test(password)) {
            $("#passwordCheckResult").text("비밀번호는 영문과 숫자를 포함해야 합니다.").css("color", "red");
        } else {
            $("#passwordCheckResult").text(""); // 정상 입력이면 메시지 제거
        }
    });

    // ✅ 비밀번호 확인 실시간 검사
    $("#confirmPassword").on("input", function () {
        let password = $("#password").val().trim();
        let confirmPassword = $(this).val().trim();

        if (password !== confirmPassword) {
            $("#confirmPasswordCheckResult").text("비밀번호가 일치하지 않습니다.").css("color", "red");
        } else {
            $("#confirmPasswordCheckResult").text(""); // 정상 입력이면 메시지 제거
        }
    });

    // ✅ 아이디 중복 확인 AJAX 요청
    $("#checkUsernameBtn").click(function () {
        let adminId = $("#adminId").val().trim();
        if (adminId === "") {
            $("#usernameCheckResult").text("아이디를 입력하세요.").css("color", "red");
            return;
        }

        $.ajax({
            type: "GET",
            url: "/api/admin/check-id",
            data: { adminId: adminId },
            success: function (response) {
                if (response.available) {
                    $("#usernameCheckResult").text("아이디 확인되었습니다.").css("color", "green");
                    isUsernameAvailable = true;
                } else {
                    $("#usernameCheckResult").text("지정된 아이디가 아닙니다").css("color", "red");
                    isUsernameAvailable = false;
                }
            },
            error: function () {
                $("#usernameCheckResult").text("서버 오류가 발생했습니다.").css("color", "red");
            }
        });
    });

    // ✅ 회원가입 AJAX 요청
    $("#registerForm").submit(function (event) {
        event.preventDefault(); // 기본 폼 제출 막기

        let adminId = $("#adminId").val().trim();
        let password = $("#password").val().trim();
        let confirmPassword = $("#confirmPassword").val().trim();

        // 아이디 중복 확인 여부 체크
        if (!isUsernameAvailable) {
            alert("관리자 아이디 체크 검사를 해주세요.");
            return;
        }
        if (password.length < 8) {
            alert("비밀번호는 8자 이상이어야 합니다.");
            return;
        }
        if (!/.*\d.*/.test(password) || !/.*[A-Za-z].*/.test(password)) {
            alert("비밀번호는 영문과 숫자를 포함해야 합니다.");
            return;
        }
        if (password !== confirmPassword) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        let formData = {
            adminId: adminId,
            password: password,
            confirmPassword: confirmPassword
        };

        $.ajax({
            type: "POST",
            url: "/api/admin/register",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (response) {
                alert("비밀번호 설정이 완료되었습니다!");
                window.location.href = "/login"; // 로그인 페이지로 이동
            },
            error: function (xhr) {
                let errorMsg = xhr.responseJSON?.message || "가입에 실패했습니다.";
                alert(errorMsg);
            }
        });
    });
});

$(document).ready(function () {
    let isUsernameAvailable = true; // 아이디 중복 확인 상태

    // ✅ 아이디 입력 실시간 검사
    $("#sellerId").on("input", function () {
        let sellerId = $(this).val().trim();
        isUsernameAvailable = false; // 아이디 변경 시 중복 확인 초기화

        if (!/^[a-zA-Z0-9]*$/.test(sellerId)) {
            $("#usernameCheckResult").text("아이디는 숫자와 영문자만 가능합니다.").css("color", "red");
        } else if (sellerId.length < 4 || sellerId.length > 12) {
            $("#usernameCheckResult").text("아이디는 4~12자여야 합니다.").css("color", "red");
        } else {
            $("#usernameCheckResult").text(""); // 정상 입력이면 메시지 제거
        }
    });

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
        let sellerId = $("#sellerId").val().trim();
        if (sellerId === "") {
            $("#usernameCheckResult").text("아이디를 입력하세요.").css("color", "red");
            return;
        }

        $.ajax({
            type: "GET",
            url: "/api/seller/check-id",
            data: { sellerId: sellerId },
            success: function (response) {
                console.log(response)
                console.log(response.available)
                if (!response.available) {
                    $("#usernameCheckResult").text("사용 가능한 아이디입니다.").css("color", "green");
                    isUsernameAvailable = true;
                } else {
                    $("#usernameCheckResult").text("이미 사용 중인 아이디입니다.").css("color", "red");
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

        let sellerId = $("#sellerId").val().trim();
        let storeName = $("#storeName").val().trim();
        let password = $("#password").val().trim();
        let confirmPassword = $("#confirmPassword").val().trim();
        let registrationNumber = $("#registrationNumber").val().trim();
        console.log(registrationNumber)

        // 아이디 중복 확인 여부 체크
        if (!isUsernameAvailable) {
            alert("아이디 중복 확인을 해주세요.");
            return;
        }

        // 최종 유효성 검사
        if (!/^[a-zA-Z0-9]*$/.test(sellerId)) {
            alert("아이디는 숫자와 영문자만 가능합니다.");
            return;
        }
        if (sellerId.length < 4 || sellerId.length > 12) {
            alert("아이디는 4~12자여야 합니다.");
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
            sellerId: sellerId,
            password: password,
            confirmPassword: confirmPassword,
            storeName: storeName,
            registrationNumber: registrationNumber
        };

        $.ajax({
            type: "POST",
            url: "/api/seller/register",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (response) {
                alert("회원가입이 완료되었습니다!" +
                    "관리자의 승인이 있을때까지 대기 부탁드립니다");
                window.location.href = "/"; // 메인페이지로 이동
            },
            error: function (xhr) {
                let errorMsg = xhr.responseJSON?.message || "회원가입에 실패했습니다.";
                alert(errorMsg);
            }
        });
    });
});

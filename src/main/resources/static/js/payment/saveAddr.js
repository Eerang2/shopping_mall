$(document).ready(function () {
    // 배송지 추가 팝업 표시
    $("#addAddressBtn").click(function () {
        $("#addressPopup, #popupOverlay").show();
    });

    // 팝업 닫기
    $("#popupOverlay").click(function () {
        $("#addressPopup, #popupOverlay").hide();
    });

    // 주소 저장
    $("#saveAddressBtn").click(function () {
        const addressData = {
            addressName: $("#newAddressName").val(),
            address: $("#newAddress").val(),
            addressDetail: $("#newDetailAddress").val()
        };

        if (!addressData.addressName || !addressData.address || !addressData.addressDetail) {
            alert("모든 필드를 입력해주세요.");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/api/user/address",
            contentType: "application/json",
            data: JSON.stringify(addressData),
            success: function (response) {
                if (response) {
                    // UI 업데이트
                    $("#addressExistMessage")
                        .html(`
                            <p><strong>주소:</strong> <span id="address">${addressData.address}</span></p>
                            <p><strong>상세주소:</strong> <span id="detailAddress">${addressData.addressDetail}</span></p>
                        `)
                        .show();
                    $("#addressNotExistMessage").hide();

                    // 팝업 초기화 및 닫기
                    $("#newAddressName, #newAddress, #newDetailAddress").val("");
                    $("#addressPopup, #popupOverlay").hide();

                    alert("배송지가 등록되었습니다.");
                    location.reload();
                }
            },
            error: function () {
                alert("배송지 등록에 실패했습니다.");
            }
        });
    });
});
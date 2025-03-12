$(document).ready(function() {
    // 이미지 미리보기
    $("#image").on("change", function(event) {
        const file = event.target.files[0];
        const reader = new FileReader();

        reader.onload = function(e) {
            $("#preview").attr("src", e.target.result).show();
        }

        if (file) {
            reader.readAsDataURL(file);
        } else {
            $("#preview").hide();
        }
    });

    // 폼 제출 처리
    $("#productForm").on("submit", function(e) {
        e.preventDefault();

        // 폼 데이터 가져오기
        const formData = new FormData();
        formData.append("image", $("#image")[0].files[0]);

        // 이미지 가공 요청 (이미지 처리 서버로 요청)
        $.ajax({
            url: "/api/seller/image-process", // 실제 이미지 가공을 처리할 URL
            type: "POST",
            data: formData,
            dataType: "json", // 응답을 JSON 형식으로 처리
            contentType: false,
            processData: false,
            success: function(response) {
                // 이미지 가공이 완료되면 DB 저장을 위한 요청
                const processedImage = response.processedImage; // 가공된 이미지 URL 또는 경로
                console.log(processedImage)
                const productData = {
                    productName: $("#productName").val().trim(),
                    price: $("#price").val().trim(),
                    image: processedImage,
                };

                // DB에 저장하기 위한 요청
                $.ajax({
                    url: "/api/seller/save-product", // 실제 상품 저장을 처리할 URL
                    type: "POST",
                    contentType: "application/json", // 요청 본문이 JSON 형식임을 명시
                    dataType: "json", // 응답을 JSON 형식으로 처리
                    data: JSON.stringify(productData),
                    success: function(saveResponse) {
                        if (saveResponse.success) {
                            alert("상품이 성공적으로 등록되었습니다.");
                            // 상품 등록 후 추가적인 처리 (예: 페이지 이동, 폼 초기화 등)
                        } else {
                            alert("상품 등록에 실패했습니다.");
                        }
                    },
                    error: function(err) {
                        alert("상품 등록 중 오류가 발생했습니다.");
                    }
                });
            },
            error: function(err) {
                alert("이미지 가공 중 오류가 발생했습니다.");
            }
        });
    });
});

function logout() {
    fetch('/api/admin/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify({})  // 필요하면 데이터 추가
    })
        .then(response => {
            if (response.ok) {
                // 로그아웃 성공 시 처리할 동작 (예: 로그인 페이지로 이동)
                window.location.href = '/admin/login'; // 예시로 로그인 페이지로 리다이렉션
            } else {
                // 로그아웃 실패 시 처리할 동작
                alert('로그아웃 실패');
            }
        })
        .catch(error => {
            console.error('로그아웃 오류:', error);
            alert('로그아웃 처리 중 오류 발생');
        });
}
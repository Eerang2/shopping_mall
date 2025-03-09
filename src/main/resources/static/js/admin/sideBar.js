// 드롭다운 메뉴 열기/닫기 이벤트 처리
const dropdownToggles = document.querySelectorAll('.dropdown-toggle');
const dropdowns = document.querySelectorAll('.dropdown');

// 드롭다운 메뉴 클릭 시 열기/닫기
dropdownToggles.forEach(toggle => {
    toggle.addEventListener('click', function(e) {
        e.preventDefault();

        // 클릭한 드롭다운 토글 메뉴만 열기
        const dropdown = this.nextElementSibling;
        const isVisible = dropdown.style.display === 'block';

        // 모든 드롭다운 메뉴를 닫기
        dropdowns.forEach(drop => {
            drop.style.display = 'none';
        });

        // 클릭한 메뉴가 닫혀 있으면 열기
        if (!isVisible) {
            dropdown.style.display = 'block';
        }
    });
});

// 외부 클릭 시 드롭다운 메뉴 닫기
document.addEventListener('click', function(e) {
    // 사이드바 외부 클릭 시 드롭다운 닫기
    if (!e.target.closest('.sidebar')) {
        dropdowns.forEach(drop => {
            drop.style.display = 'none';
        });
    }
});
function toggleLike(boardId, button) {
    const csrfToken = document.getElementById(`csrfToken`).value;

    fetch("/board/like?boardId=" + boardId, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrfToken
        }
    })
    .then(response => response.text())
    .then(data => {
        if (data === "unliked") {
            button.src = '/img/unlike.png'; // 좋아요 취소 상태로 변경
        } else if (data === "liked") {
            button.src = '/img/like.png'; // 좋아요 상태로 변경
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("좋아요 처리 중 오류가 발생했습니다.");
    });
}
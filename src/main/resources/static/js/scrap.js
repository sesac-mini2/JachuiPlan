//const csrfToken = $("#csrfToken").val();
axios.defaults.baseURL = 'http://localhost';
const path = window.location.pathname;
const bno = path.split("/").pop();
const scrapFragment = $("#scrapFragment");

$(document).ready(function(){
    getScrapList();
});

// 스크랩 등록, 취소
const handleScrapClick = (e) => {
    const regioncdId = e.target.dataset.regioncdId;
    if(!regioncdId) {
        return;
    }

    if(e.target.classList.contains("active")) { // 스크랩 삭제
        fetch(`/api/scrap/${regioncdId}`, {
          method: "DELETE",
          credentials: "include",
        })
        .then((response) => response.json())
        .then((data) => {
          e.target.classList.remove("active");
          e.target.setAttribute("src", "/img/bookmark.png")
        })
        .catch((err) => {
          console.log(err);
        });
    } else { // 스크랩 추가
        fetch(`/api/scrap/${regioncdId}`, {
          method: "POST",
          credentials: "include",
        })
        .then((response) => response.json())
        .then((data) => {
          e.target.classList.add("active");
          e.target.setAttribute("src", "/img/bookmark_active.png")
        })
        .catch((err) => {
          console.log(err);
        });
    }
};

// 스크랩 목록
async function getScrapList(type="building") {
    await axios.get("/scrap/list?type=" + type)
        .then(function(res) {
            scrapFragment.empty();
            scrapFragment.append(res.data);
            $(".scrap-btn").click(handleScrapClick);
        })
        .catch(function(err) {
            console.log(err);
            alert("스크랩 목록을 가져오는데 실패했습니다.");
        })
}

// 건물 타입 선택시 새로운 리스트 호출
$(".dropdown-item").click(function(e) {
    const type = e.target.dataset.type;
    $(".dropdown-type").text(e.target.textContent);
    getScrapList(type);
})

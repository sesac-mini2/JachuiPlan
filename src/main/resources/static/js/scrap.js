// 화경
//const csrfToken = $("#csrfToken").val();
axios.defaults.baseURL = 'http://localhost';
const path = window.location.pathname;
const bno = path.split("/").pop();
const scrapFragment = $("#scrapFragment");

let startYearMonth = "202301";
let endYearMonth = null;
let buildingType = "building";
let rentType = "월세";
let minFloor = -9999;
let maxFloor = 9999;
let minBuildYear = null;
let maxBuildYear = null;
let minArea = null;
let maxArea = null;

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
async function getScrapList() {
    await axios.get('/scrap/list?', {
        params: {
            type: buildingType,
            rentType: rentType,
            minFloor: minFloor,
            maxFloor: maxFloor,
            startYearMonth: startYearMonth,
            endYearMonth: endYearMonth,
            minBuildYear: minBuildYear,
            maxBuildYear: maxBuildYear,
            minArea: minArea,
            maxArea: maxArea,
        }
    })
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
    const dropdown = $(this).parent().prev();
    dropdown.text(e.target.textContent);

    if (type === "building") {
        buildingType = type;
    }
    else if(type === "officeHotel") {
        buildingType = type;
    }
    else if(type === "월세") {
        rentType = type;
    }
    else if(type === "반전세") {
        rentType = type;
    }
    else if(type === "전세") {
        rentType = type;
    }
    else if(type === "전체") {
        minFloor = -9999;
        maxFloor = 9999;
    }
    else if(type === "지하") {
        minFloor = -9999;
        maxFloor = 0;
    }
    else if(type === "1층") {
        minFloor = 1;
        maxFloor = 1;
    }
    else if(type === "2층") {
        minFloor = 2;
        maxFloor = 2;
    }
    else if(type === "3층 이상") {
        minFloor = 3;
        maxFloor = 9999;
    }
    else {

    }

    getScrapList();
})

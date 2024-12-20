const replyListContainer = $("#replyListContainer");
const paginationContainer = $("#paginationContainer");

axios.defaults.baseURL = 'http://localhost/api';
const path = window.location.pathname;
const bno = path.split("/").pop();

// 댓글 목록
async function getReplyList(page = 0) {
    await axios.get("/reply/list/" + bno + "?page=" + page)
        .then(function(res) {
            const paging = res.data;
            const replyList = paging.content;

            renderReplyList(replyList, paging);
        })
        .catch(function(err) {
            console.log(err);
            alert("댓글 목록을 가져오는데 실패했습니다.");
        })
}
// 특정 댓글 목록
async function getTargetReplyList(rno) {
    await axios.get("/reply/list/" + bno + "/" + rno)
        .then(function(res) {
            const paging = res.data;
            const replyList = paging.content;
            renderReplyList(replyList, paging);

            // 특정 댓글로 이동
            $('html, body').animate({
                scrollTop: $(`.card[data-reply-id='${rno}']`).offset().top
            }, 500);
        })
        .catch(function(err) {
            console.log(err);
            alert("댓글 목록을 가져오는데 실패했습니다.");
        })
}
// 댓글 등록
async function createReply(){
    const csrfToken = $("#csrfToken").val();
    await axios.post("/reply/" + bno, {
        reply: $("#replyContent").val()
    }, {
        headers: {
            "X-CSRF-TOKEN": csrfToken
        }
    })
    .then(function(res) {
        let reply = res.data;
        getTargetReplyList(reply.rno);
    })
    .catch(function(err) {
        console.log(err);
    })
}
// 댓글 수정
async function modifyReply(e){
    const replyId = e.dataset.replyId;
    await axios.put("/reply/" + replyId)
        .then(function(res) {
            console.log(res);
            $(`.card[data-reply-id='${rno}']`)
        })
        .catch(function(err) {
            console.log(err);
        })
        .finally(function() {
        })
}
// 댓글 삭제
async function deleteReply(e){
    const csrfToken = $("#csrfToken").val();
    const replyId = e.dataset.replyId;
    const page = $(".page-item.active").find(".page-link").data("page");
    await axios.delete("/reply/" + replyId, {
            headers: {
                "X-CSRF-TOKEN": csrfToken
            }
        })
        .then(function(res) {
            getReplyList(page);
        })
        .catch(function(err) {
            console.log(err);
        })
        .finally(function() {
        })
}

function renderReplyList(replyList, paging){
    replyListContainer.empty();
    paginationContainer.empty();
    console.log(paging);
    $("#replyCount").val(paging.totalElements);
    // 댓글 목록 렌더링
    replyList.forEach(reply => {
        let item =
            `<div class="card mb-3" data-reply-id="${reply.rno}">
                <div class="card-body">
                    <p>${reply.nickname}</p>
                    <p>${reply.reply}</p>
                    <p class="text-muted small">${reply.replydate}</p>
                    <div class="text-end">
                        <button class="btn btn-primary btn-sm" data-reply-id="${reply.rno}">수정</button>
                        <button class="btn btn-secondary btn-sm" data-reply-id="${reply.rno}" onclick="deleteReply(this)">삭제</button>
                    </div>
                </div>
            </div>`
        replyListContainer.append(item);
    })

    // 페이지 번호 렌더링
    // 이전 버튼
    let pageItems =
        `<ul class="pagination justify-content-center">
            <li class="page-item ${paging.first ? 'invisible' : ''}">
                <a class="page-link" data-page="${paging.number - 1}">
                    <span>이전</span>
                </a>
            </li>`;
    // 페이지 번호
    for (let i = Math.max(0, paging.number - 5); i <= Math.min(paging.totalPages - 1, paging.number + 5); i++) {
        pageItems +=
            `<li class="page-item ${i === paging.number ? 'active' : ''}">
                <a class="page-link" data-page="${i}">${i + 1}</a>
            </li>`;
    }
    // 다음 버튼
    pageItems +=
        `<li class="page-item ${paging.last ? 'invisible' : ''}">
            <a class="page-link" data-page="${paging.number + 1}">
                <span>다음</span>
            </a>
        </li></ul>`;

    paginationContainer.append(pageItems);

    $(".page-item:not(.active)").click(function(e){
        getReplyList(parseInt(e.target.dataset.page));
    });
}


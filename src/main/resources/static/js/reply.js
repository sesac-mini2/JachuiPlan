// 화경
const replyFragment = $("#replyFragment");

//const csrfToken = $("#csrfToken").val();
axios.defaults.baseURL = 'http://localhost';
axios.defaults.withCredentials = true;
const path = window.location.pathname;
const bno = path.split("/").pop();

// 댓글 목록
async function getReplyList(page = 0, targetRno = null) {
    await axios.get("/reply/list/" + bno + "?page=" + page)
        .then(function(res) {
            renderReplyList(res.data, targetRno);
        })
        .catch(function(err) {
            console.log(err);
            alert("댓글 목록을 가져오는데 실패했습니다.");
        })
}
// 댓글 등록
async function createReply(replyContent){
    await axios.post("/reply/" + bno, {
        reply: replyContent
    }, {
        headers: {
            "X-CSRF-TOKEN": csrfToken
        }
    })
    .then(function(res) {
        const reply = res.data;
        getReplyList(reply.page, reply.rno);
    })
    .catch(function(err) {
        const err_msgs = err.response.data;
        $("#replyErrorContainer").empty();
        err_msgs.forEach(msg => {
            $("#replyErrorContainer").append(
                `<p>${msg}</p>`
            );
        })
        alert("댓글을 등록하지 못했습니다. 다시 시도해주세요.");
    })
}
// 댓글 수정
async function modifyReply(replyId, replyContent){
    await axios.put("/reply/" + replyId, {
        reply: replyContent
    }, {
       headers: {
           "X-CSRF-TOKEN": csrfToken
       }
    })
        .then(function(res) {
            const reply = res.data;
            $(`.reply-card[data-reply-id='${reply.rno}']`).find('.reply-content').text(reply.reply);
            alert("댓글을 수정했습니다.");
        })
        .catch(function(err) {
            console.log(err);
            alert("댓글을 수정하지 못했습니다. 다시 시도해주세요.");
        })
        .finally(function() {
        })
}
// 댓글 삭제
async function deleteReply(replyId){
    if(confirm("댓글을 삭제하시겠습니까?")){
        const page = $(".page-item.active").data('page');
        await axios.delete("/reply/" + replyId, {
                headers: {
                    "X-CSRF-TOKEN": csrfToken
                }
            })
            .then(function(res) {
                getReplyList(page);
                alert("댓글을 삭제했습니다.")
            })
            .catch(function(err) {
                console.log(err);
                alert("댓글을 삭제하지 못했습니다. 다시 시도해주세요.")
            })
            .finally(function() {
            })
    }
}

// 댓글 등록 버튼 클릭
function clickRegReplyBtn(e){
    const replyContent = $('#replyContent').val().trim();
    if(!validateReply(replyContent)){
        $("#replyErrorContainer").empty();
        $("#replyErrorContainer").append(`<p class="p-2 text-danger">! 댓글을 입력해주세요.</p>`)
        return;
    }
    createReply(replyContent);

}

// 댓글 수정 버튼 클릭
function clickModifyReplyBtn(e) {
    const card = $(e).closest('.reply-card');
    const replyId = card.data("reply-id");
    const replyContent = card.find('.reply-content');
    const replyErr = card.find('.reply-err');
    const cancelBtn = $(e).next();

    replyErr.empty();

    if ($(e).text() === '수정') {
        // p 태그 내용을 textarea로 복사
        const currentContent = replyContent.text();

        // replyContent 숨기고 textarea 표시
        replyContent.addClass('d-none');
        replyContent.after(`<textarea class="reply-textarea form-control mb-3 p-3" placeholder="댓글을 입력하세요">${currentContent}</textarea>`);

        // 버튼 텍스트를 '저장'으로 변경
        $(e).text('저장');

        // 삭제 버튼을 '취소'로 변경
        cancelBtn.text('취소');
    }
    else {
        // 변경 내용 가져오기
        const updatedContent = card.find('.reply-textarea').val().trim();
        if(!validateReply(updatedContent)){
            replyErr.append(`<p class="p-2 text-danger">! 댓글을 입력해주세요.</p>`)
            return;
        }

        modifyReply(replyId, updatedContent);

        // textarea 삭제하고 replyContent 태그 표시
        replyContent.next().remove();
        replyContent.text(updatedContent);
        replyContent.removeClass('d-none');

        // 버튼 텍스트를 '수정'으로 변경
        $(e).text('수정');

        // 취소 버튼을 '삭제'로 변경
        cancelBtn.text('삭제');
    }
}

// 댓글 삭제/취소 버튼 클릭
function clickDeleteReplyBtn(e){
    if ($(e).text() === '삭제') {
        const card = $(e).closest('.reply-card');
        const replyId = card.data("reply-id");
        deleteReply(replyId);
    } else {
        // 취소 버튼일 때

        // 취소 버튼을 '삭제'로 변경
        $(e).text('삭제');

        // 저장 버튼을 '수정'으로 변경
        $(e).prev().text('수정');

        const card = $(e).closest('.reply-card');
        const replyContent = card.find('.reply-content');
        const replyTextarea = card.find('.reply-textarea');
        const replyErr = card.find('.reply-err');

        // 댓글 내용, 수정란 변경
        replyContent.removeClass('d-none');
        replyTextarea.remove();
        replyErr.empty();
    }
}

// 렌더링
function renderReplyList(replyList, targetRno){
    // 댓글 목록, 페이지네이션 렌더링
    replyFragment.empty();
    replyFragment.append(replyList);

    // 페이지 이벤트
    $(".page-item:not(.active):not(.none)").click(function(){
        getReplyList(parseInt(this.dataset.page));
        $('html, body').animate({
            scrollTop: replyFragment.offset().top
        }, 500);
    });

    // 댓글 등록 이벤트
    $(".reply-reg").click(function(){
        clickRegReplyBtn(this);
    })

    // 댓글 수정 이벤트
    $(".reply-modify").click(function(){
        clickModifyReplyBtn(this);
    })

    // 댓글 삭제 이벤트
    $(".reply-delete").click(function(){
        clickDeleteReplyBtn(this);
    })

    // 특정 댓글로 이동
    if(targetRno){
        $('html, body').animate({
            scrollTop: $(`.reply-card[data-reply-id='${targetRno}']`).offset().top
        }, 500);
    }
}

function validateReply(reply){
    // 빈 댓글일 때
    if(!reply){
        return false;
    }
    // 300자 넘어갈 때
    if(reply.length > 300){
        return false;
    }
    return true;
}
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script>
    axios.defaults.baseURL = 'http://localhost/api';
    const path = window.location.pathname;
    const bno = path.split("/").pop();
    const replyListContainer = $("#replyListContainer");
    const paginationContainer = $("#paginationContainer");

    // 댓글 목록
    async function getReplyList() {
        await axios.get("/reply/list/" + bno)
            .then(function(res) {
                const paging = res.data;
                const replyList = paging.content;
                replyList.forEach(reply => {
                    let item =
                        `<div class="card mb-3">
                            <div class="card-body">
                                <p>${reply.author}</p>
                                <p>${reply.reply}</p>
                                <p class="text-muted small">${reply.replydate}</p>
                                <div class="text-end">
                                    <button class="btn btn-primary btn-sm" data-reply-id=${reply.id}>수정</button>
                                    <button class="btn btn-secondary btn-sm" data-reply-id=${reply.id} disabled>삭제</button>
                                </div>
                            </div>
                        </div>`
                    replyListContainer.append(item);
                })

                let preBtn =
                    `<ul class="pagination justify-content-center">
                        <a class="page-link" href="#" data-page="${paging.number - 1}">
                            <li class="page-item ${!paging.hasPrevious ? 'disabled' : ''}">
                                <span>이전</span>
                            </li>
                        </a>`;

                let pagingNumber = ``;
                for (let i = Math.max(0, paging.number - 5); i <= Math.min(paging.totalPages - 1, paging.number + 5); i++) {
                    pagingNumber +=
                        `<li class="page-item ${i === paging.number ? 'active' : ''}">
                            <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
                        </li>`;
                }

                let nxtBtn =
                    `<li class="page-item ${!paging.last ? 'disabled'}">
                        <a class="page-link" href="#" data-page="${paging.number + 1}">
                            <span>다음</span>
                        </a>
                    </li></ul>`;

                paginationContainer.append(preBtn);
                paginationContainer.append(pagingNumber);
                paginationContainer.append(nxtBtn);
            })
            .catch(function(err) {
                console.log(err);
                alert("댓글 목록을 가져오는데 실패했습니다.");
            })
    }

    // 댓글 등록
    async function createReply(){
        await axios.post("/reply/" + bno, {
            reply: $("#replyContent").val()
        })
        .then(function(res) {

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
            })
            .catch(function(err) {
                console.log(err);
            })
            .finally(function() {
            })
    }

    // 댓글 삭제
    async function deleteReply(e){
        const replyId = e.dataset.replyId;
        await axios.delete("/reply/" + replyId)
            .then(function(res) {
                console.log(res);
            })
            .catch(function(err) {
                console.log(err);
            })
            .finally(function() {
            })
    }
</script>
$(document).ready(function () {

    const csrfToken = $('#csrfToken').val();

    // 비밀번호 확인 버튼 클릭 이벤트
    $('#verifyPasswordBtn').click(function () {
        const password = $('#password').val();
        const message = $('#verifyMessage');

        if (!password) {
            message.text("비밀번호를 입력해주세요.")
                .removeClass("success-message")
                .addClass("error-message");
            return;
        }

        $.ajax({
            url: "/users/myPage/verify-password", // 서버 API
            type: "POST",
            data: { password: password },
            success: function () {
                message.text("비밀번호가 일치합니다.")
                    .removeClass("error-message")
                    .addClass("success-message");
                $('#passwordVerifySection').hide();
                $('#myPageContent').fadeIn();
            },
            error: function () {
                message.text("비밀번호가 일치하지 않습니다.")
                    .removeClass("success-message")
                    .addClass("error-message");
            }
        });
    });

    // 탭 클릭 이벤트
    $('.tab-link').click(function () {
        const targetTab = $(this).data('tab');

        $('.tab-content').removeClass('active');
        $('.tab-link').removeClass('active');
        $(`#${targetTab}`).addClass('active');
        $(this).addClass('active');
    });

    // 닉네임 중복 확인
    $('#checkNicknameBtn').click(function () {
        const nickname = $('#nickname').val();
        const message = $('#nicknameMessage');

        if (!nickname) {
            message.text("닉네임을 입력해주세요.").addClass("error-message");
            return;
        }

        $.ajax({
            url: "/users/check-nickname",
            type: "GET",
            data: { nickname: nickname },
            success: function () {
                message.text("사용 가능한 닉네임입니다.").removeClass("error-message").addClass("success-message");
            },
            error: function () {
                message.text("이미 사용 중인 닉네임입니다.").removeClass("success-message").addClass("error-message");
            }
        });
    });

    // 수정 완료 버튼 클릭 이벤트
    $('#updateInfoBtn').click(function () {
        const nickname = $('#nickname').val();
        const newPassword = $('#newPassword').val();
        const confirmPassword = $('#confirmPassword').val();
        const passwordMessage = $('#passwordMessage');

        if (!newPassword || !confirmPassword) {
            passwordMessage.text("모든 비밀번호 입력 필드를 채워주세요.").addClass("error-message");
            return;
        }

        if (newPassword !== confirmPassword) {
            passwordMessage.text("비밀번호가 일치하지 않습니다.").addClass("error-message");
            return;
        }

        $.ajax({
            url: "/users/change-nickname",
            type: "PUT",
            data: { nickname: nickname },
            success: function () {
                $('#nicknameMessage').text("닉네임이 변경되었습니다.").addClass("success-message");

                $.ajax({
                    url: "/users/change-password",
                    type: "PUT",
                    data: { newPassword: newPassword, confirmPassword: confirmPassword },
                    success: function () {
                        passwordMessage.text("비밀번호가 변경되었습니다.").removeClass("error-message").addClass("success-message");
                        location.reload();
                    },
                    error: function () {
                        passwordMessage.text("비밀번호 변경에 실패했습니다.").addClass("error-message");
                    }
                });


            },
            error: function () {
                $('#nicknameMessage').text("닉네임 변경에 실패했습니다.").addClass("error-message");
                location.reload();
            }
        });
    });
    $('#deleteAccountBtn').click(function() {
        if(confirm("정말로 회원 탈퇴를 진행하시겠습니까?")) {
            $.ajax({
                url: "/users/delete",
                type: "DELETE",
                headers: {
                    'X-CSRF-TOKEN': csrfToken // 기본 헤더 이름 사용
                },
                success: function(){
                    alert("회원 탈퇴가 완료되었습니다.");
                    window.location.href = "/map/";
                },
                error: function () {
                    alert("회원 탈퇴 중 문제가 발생했습니다. 다시 시도해 주세요.");
                }
            });
        }
    });
});
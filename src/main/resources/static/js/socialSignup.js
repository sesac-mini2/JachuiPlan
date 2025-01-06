// 정은
document.addEventListener('DOMContentLoaded', () => {
            const form = document.getElementById('socialSignupForm');
            const passwordField = document.getElementById('password');
            const confirmPasswordField = document.getElementById('confirmPassword');
            const passwordMatchMessage = document.getElementById('password-match-message');
            const nicknameField = document.getElementById('nickname');
            const nicknameMessage = document.getElementById('nickname-message');
            const checkNicknameBtn = document.getElementById('check-nickname');
            const signupBtn = document.getElementById('signupBtn');

            // 비밀번호 일치 여부 실시간 확인
            function checkPasswordMatch() {
                if (passwordField.value && confirmPasswordField.value &&
                    passwordField.value !== confirmPasswordField.value) {
                    passwordMatchMessage.textContent = "비밀번호가 일치하지 않습니다.";
                    return false;
                } else {
                    passwordMatchMessage.textContent = "";
                    return true;
                }
            }
            passwordField.addEventListener('input', checkPasswordMatch);
            confirmPasswordField.addEventListener('input', checkPasswordMatch);

            // 닉네임 중복 확인
            checkNicknameBtn.addEventListener('click', () => {
                const nicknameValue = nicknameField.value.trim();
                if (!nicknameValue) {
                    alert("닉네임을 입력해주세요.");
                    return;
                }

                fetch(`/users/check-nickname?nickname=${nicknameValue}`)
                    .then(response => {
                        if (response.ok) {
                            return response.text(); // 서버에서 보낸 메시지("사용 가능한 닉네임입니다." 등)
                        } else {
                            throw new Error("이미 있는 닉네임입니다.");
                        }
                    })
                    .then(msg => {
                        nicknameMessage.textContent = msg;
                        nicknameMessage.className = "success-message";
                    })
                    .catch(err => {
                        nicknameMessage.textContent = err.message;
                        nicknameMessage.className = "error-message";
                    });
            });

            // 폼 전송(완료 버튼)
            form.addEventListener('submit', (event) => {
                event.preventDefault(); // 폼의 기본 submit 동작을 막음

                // 비밀번호 최종 확인
                if (!checkPasswordMatch()) {
                    alert("비밀번호가 일치하지 않습니다.");
                    return;
                }

                // 닉네임 공백 체크
                const nicknameValue = nicknameField.value.trim();
                if (!nicknameValue) {
                    alert("닉네임을 입력해주세요.");
                    return;
                }

                // 서버로 AJAX POST
                fetch("/users/social-signup", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                    body: new URLSearchParams({
                        nickname: nicknameValue,
                        newPassword: passwordField.value,
                        confirmPassword: confirmPasswordField.value
                    })
                })
                .then(response => {
                    if (response.ok) {
                        return response.text();
                    } else {
                        // 에러 메시지(비밀번호 불일치, 닉네임 중복 등)
                        return response.text().then(text => { throw new Error(text); });
                    }
                })
                .then(resultMsg => {
                    alert(resultMsg);
                    // 성공 시 메인 페이지로
                    window.location.href = "/map";
                })
                .catch(err => {
                    alert(err.message);
                });
            });
        });
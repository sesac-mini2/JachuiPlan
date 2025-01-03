// 비밀번호 일치 확인
const password = document.getElementById("password");
const confirmPassword = document.getElementById("confirmPassword");
const passwordMatchMessage = document.getElementById("password-match-message");

function checkPasswordMatch() {
  if (password.value !== confirmPassword.value) {
    passwordMatchMessage.textContent = "비밀번호가 일치하지 않습니다.";
  } else {
    passwordMatchMessage.textContent = "";
  }
}

password.addEventListener("input", checkPasswordMatch);
confirmPassword.addEventListener("input", checkPasswordMatch);

// 아이디 중복 확인
document.getElementById("check-username").addEventListener("click", function () {
  const username = document.getElementById("username").value;
  const usernameMessage = document.getElementById("username-message");

  if (!username) {
    alert("아이디를 입력해주세요.");
    return;
  }

  fetch(`/users/check-username?username=${username}`)
    .then(response => {
      if (response.ok) {
        return response.text();
      } else {
        throw new Error("이미 있는 아이디입니다.");
      }
    })
    .then(data => {
      usernameMessage.textContent = data;
      usernameMessage.className = "success-message";
    })
    .catch(error => {
      usernameMessage.textContent = error.message;
      usernameMessage.className = "error-message";
    });
});

// 닉네임 중복 확인
document.getElementById("check-nickname").addEventListener("click", function () {
  const nickname = document.getElementById("nickname").value;
  const nicknameMessage = document.getElementById("nickname-message");

  if (!nickname) {
    alert("닉네임을 입력해주세요.");
    return;
  }

  fetch(`/users/check-nickname?nickname=${nickname}`)
    .then(response => {
      if (response.ok) {
        return response.text();
      } else {
        throw new Error("이미 있는 닉네임입니다.");
      }
    })
    .then(data => {
      nicknameMessage.textContent = data;
      nicknameMessage.className = "success-message";
    })
    .catch(error => {
      nicknameMessage.textContent = error.message;
      nicknameMessage.className = "error-message";
    });
});
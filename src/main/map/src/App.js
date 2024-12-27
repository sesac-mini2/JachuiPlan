import React, { useState, useEffect } from 'react';

function App() {
  const [msg, setMsg] = useState(""); // 초기 상태를 문자열로 설정

  useEffect(() => {
    fetch("/api/regioncd")
        .then((response) => {
          if (!response.ok) {
            throw new Error("네트워크 응답이 잘못되었습니다.");
          }
          return response.json(); // JSON 응답 처리
        })
        .then((data) => {
          setMsg(JSON.stringify(data[0])); // JSON 데이터 확인
        })
        .catch((error) => {
          console.error("Fetch 작업 중 문제가 발생했습니다:", error);
        });
  }, []);

  return (
      <div>
        <h1>say:</h1>
        <p>{msg}</p>
      </div>
  );
}

export default App;

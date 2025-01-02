import React, { useState, useEffect } from "react";

const ScrapButton = ({ isAuthenticated, targetUmd }) => {
  const [isActive, setIsActive] = useState(false);

  useEffect(() => {
    if(!targetUmd){
      return;
    }
    fetch(`/api/scrap/${targetUmd}`, {
      method: "GET",
      credentials: "include",
    })
    .then((response) => response.json())
    .then((data) => {
      setIsActive(data);
    })
    .catch((err) => {
      console.log(err);
    });
  }, [targetUmd])

  const handleMarkClick = (regioncdId) => {
    if(!isAuthenticated){
      if(window.confirm("로그인 하시겠습니까?")){
        window.location.href = `http://localhost/users/login`;
      }
      return;
    }
    if(isActive) { // 스크랩 삭제
      fetch(`/api/scrap/${regioncdId}`, {
        method: "DELETE",
        credentials: "include",
      })
      .then((response) => response.json())
      .then((data) => {
        setIsActive(false);
      })
      .catch((err) => {
        console.log(err);
      });
    } else { // 스크랩 추가가
      fetch(`/api/scrap/${regioncdId}`, {
        method: "POST",
        credentials: "include",
      })
      .then((response) => response.json())
      .then((data) => {
        setIsActive(true);
      })
      .catch((err) => {
        console.log(err);
      });
    }
  };

  return (
      <span className="align-middle" onClick={() => handleMarkClick(targetUmd)}>
        <img src={isActive ? "/img/bookmark_active.png" : "/img/bookmark.png"} alt="bookmark" style={{width:"20px", height:"20px"}}/>
      </span>
  );
}

export default ScrapButton;
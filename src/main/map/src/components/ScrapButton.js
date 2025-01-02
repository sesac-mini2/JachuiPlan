import React, { useState, useEffect } from "react";

const ScrapButton = ({ regioncdId }) => {
  const [isActive, setIsActive] = useState(false);

  useEffect(() => {
    fetch(`/api/scrap/${regioncdId}`, {
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
  }, [regioncdId])

  const handleMarkClick = (regioncdId) => {
      console.log("Clicked regioncdId:", regioncdId, "isActive:", isActive);
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
      <div onClick={() => handleMarkClick(regioncdId)}>
        <img src={isActive ? "/img/bookmark_active.png" : "/img/bookmark.png"} alt="bookmark" style={{width:"20px", height:"20px"}}/>
      </div>
  );
}

export default ScrapButton;
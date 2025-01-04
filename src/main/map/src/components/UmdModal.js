import React from "react";
import ScrapButton from "./ScrapButton";
import Scrap from './Graph/Scrap';
import UmdGraph from './Graph/UmdGraph';

const UmdModal = ({ isAuthenticated, targetUmd, isShowUmdModal, closeUmdModal }) => {
  const modalStyle = {
    position: "absolute",
    top: "50%", // 세로 중앙 정렬
    left: "0.8rem", // 항상 왼쪽 정렬
    transform: "translateY(-50%)", // 정확한 세로 중앙 배치
    zIndex: 1000, // 맵 위에 표시
    width: "46%",
    height: "96%",
    boxShadow: "0 4px 8px rgba(0, 0, 0, 0.2)", // 그림자 효과 (선택)
    overflowY: "auto",
    overflowX: "auto",
    border: "none",
    backgroundColor: "#ffffff",
    borderRadius: "0.5rem",
    padding: "1.2rem 1.5rem",
  };
  
  const closeButtonStyle = {
    position: "absolute",
    top: "1.2rem",
    right: "1.2rem",
  };

  return (
    <>
      <div id="umdModal" className={isShowUmdModal ? "card show" : "card d-none"} style={modalStyle} tabIndex="-1" aria-hidden="false">
        <div>
          <div>
            <span className="umdnm me-3 h4 align-middle">{targetUmd}</span>
            <ScrapButton isAuthenticated={isAuthenticated} targetUmd={targetUmd}></ScrapButton>
          </div>
          <button type="button" className="btn-close" aria-label="Close" style={closeButtonStyle} onClick={closeUmdModal}></button>
        </div>
        <Scrap />
        <UmdGraph />
      </div>
    </>
  )
}

export default UmdModal;
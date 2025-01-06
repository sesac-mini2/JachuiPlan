// 성현
import React, { useState, useRef, useEffect } from 'react';
import './App.css';

function AreaSelect({ setIsModalOpen, isModalOpen, onDateChange }) {
  const [minArea, setMinArea] = useState(0);  // 최소 면적
  const [maxArea, setMaxArea] = useState(30);  // 최대 면적 (30평)

  const sliderRef = useRef(null);
  const leftThumbRef = useRef(null);
  const rightThumbRef = useRef(null);

  // 슬라이더 드래그 핸들러
  const handleMouseDown = (e, type) => {
    const slider = sliderRef.current;
    const startX = e.clientX;

    const handleMouseMove = (moveEvent) => {
      const sliderRect = slider.getBoundingClientRect();
      const newPos = moveEvent.clientX - sliderRect.left;
      const newValue = Math.min(
        Math.max(Math.round((newPos / sliderRect.width) * 30), 0),  // 0 <= value <= 30
        30
      );

      if (type === 'min' && newValue < maxArea) {
        setMinArea(newValue);
        onDateChange(newValue, maxArea);  // 면적 값 변경 시 상위 컴포넌트에 전달
      } else if (type === 'max' && newValue > minArea) {
        setMaxArea(newValue);
        onDateChange(minArea, newValue);  // 면적 값 변경 시 상위 컴포넌트에 전달
      }
    };

    const handleMouseUp = () => {
      document.removeEventListener('mousemove', handleMouseMove);
      document.removeEventListener('mouseup', handleMouseUp);
    };

    document.addEventListener('mousemove', handleMouseMove);
    document.addEventListener('mouseup', handleMouseUp);
  };

  // 초기값 리셋
  const handleRangeReset = () => {
    setMinArea(0);
    setMaxArea(30);
    onDateChange(0, 30);  // 초기화 시 상위 컴포넌트에 전달
  };

  // 버튼 클릭 시 슬라이더 범위 설정
  const handleButtonClick = (min, max) => {
    setMinArea(min);
    setMaxArea(max);
    onDateChange(min, max);  // 버튼 클릭 시 상위 컴포넌트에 전달
  };

  // 모달 외부 클릭 시 닫기
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (isModalOpen && sliderRef.current && !sliderRef.current.contains(event.target)) {
        setIsModalOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [isModalOpen, setIsModalOpen]);

  return (
    <div className="area-select-container">
      <button className="area-select-button" onClick={() => setIsModalOpen(true)}>
        {/* 사용자가 면적을 선택하면 해당 면적을 버튼에 표시 */}
        <span>{minArea === 0 && maxArea === 30 ? '면적선택' : `${minArea}~${maxArea}평`}</span>
      </button>

      {isModalOpen && (
        <div className="area-select-modal" ref={sliderRef}>
          <div className="area-select">
            <div className="area-header">
              <span>면적선택</span>
              <button className="close-button" onClick={() => setIsModalOpen(false)}>×</button>
            </div>

            <div className="slider-container">
              <div className="slider-track" />
              <div
                ref={leftThumbRef}
                className="slider-thumb left"
                style={{ left: `${(minArea / 30) * 100}%` }}
                onMouseDown={(e) => handleMouseDown(e, 'min')}
              />
              <div
                ref={rightThumbRef}
                className="slider-thumb right"
                style={{ left: `${(maxArea / 30) * 100}%` }}
                onMouseDown={(e) => handleMouseDown(e, 'max')}
              />
            </div>
            <div className="area-range">
              <span>{minArea !== maxArea ? `${minArea}~${maxArea}평` : '30평 이상'}</span>
            </div>

            <div className="area-buttons">
              <button className="area-button" onClick={() => handleButtonClick(0, 5)}>5평 이하</button>
              <button className="area-button" onClick={() => handleButtonClick(5, 10)}>5~10평</button>
              <button className="area-button" onClick={() => handleButtonClick(10, 15)}>10~15평</button>
              <button className="area-button" onClick={() => handleButtonClick(15, 20)}>15~20평</button>
              <button className="area-button" onClick={() => handleButtonClick(20, 30)}>20~30평</button>
              <button className="area-button" onClick={() => handleButtonClick(30, 30)}>30평 이상</button>
            </div>
            <button className="reset-button" onClick={handleRangeReset}>초기화</button>
          </div>
        </div>
      )
      }
    </div >
  );
}

export default AreaSelect;

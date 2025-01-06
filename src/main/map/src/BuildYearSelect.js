// 성현
import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const BuildYearSelect = ({ onDateChange }) => {
  const [startYear, setStartYear] = useState(null);
  const [endYear, setEndYear] = useState(null);

  const currentYear = new Date();

  // 날짜가 변경되면 부모 컴포넌트로 선택된 날짜를 전달
  const handleChange = (dates) => {
    const [start, end] = dates;
    setStartYear(start); // 시작 날짜 설정
    setEndYear(end); // 종료 날짜 설정

    if (start && end) {
      const formattedStart = start.getFullYear().toString(); // YYYY 형식
      const formattedEnd = end.getFullYear().toString(); // YYYY 형식
      onDateChange(formattedStart, formattedEnd); // 부모 컴포넌트로 날짜 범위 전달
    }
  };
  return (
    <div className="year-picker-container">
      <DatePicker
        selected={startYear}
        onChange={handleChange}
        selectsRange
        showYearPicker
        dateFormat="yyyy년"
        startDate={startYear}
        endDate={endYear}
        maxDate={currentYear}
        placeholderText="연식"
        className="year-picker"
        popperClassName="year-picker-popup"
      />
    </div>
  );
};

export default BuildYearSelect;

// 성현
import React, { useState } from "react";
import DatePicker from "react-datepicker";
import ko from "date-fns/locale/ko"; // 한국어 로케일 설정
import "react-datepicker/dist/react-datepicker.css"; // 스타일 import

const MonthRangePicker = ({ onDateChange }) => {
  const [startYearMonth, setstartYearMonth] = useState(new Date("2023/01"));
  const [endYearMonth, setendYearMonth] = useState(null);

  const currentYear = new Date();

  // 날짜가 변경되면 부모 컴포넌트로 선택된 날짜를 전달
  const handleChange = (dates) => {
    const [start, end] = dates;
    setstartYearMonth(start); // 시작 날짜 설정
    setendYearMonth(end); // 종료 날짜 설정

    if (start && end) {
      const formattedStart = start.toISOString().slice(0, 7).replace(/-/g, '');  // YYYY-MM 형식에서 '-'를 제거
      const formattedEnd = end.toISOString().slice(0, 7).replace(/-/g, '');      // YYYY-MM 형식에서 '-'를 제거
      onDateChange(formattedStart, formattedEnd); // 부모 컴포넌트로 날짜 범위 전달
    }
  };

  return (
    <div className="month-range-picker">
      <DatePicker
        className="datepicker"
        selected={startYearMonth}
        onChange={handleChange}
        selectsRange
        startDate={startYearMonth}
        endDate={endYearMonth}
        dateFormat="yyyy-MM"
        showMonthYearPicker
        maxDate={currentYear}
        locale={ko} // 한국어 로케일 설정
        placeholderText="조회 기간 선택" // 입력란의 기본 텍스트
      />
    </div>
  );
};

export default MonthRangePicker;

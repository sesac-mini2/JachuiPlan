import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";

const DateRangePicker = () => {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);

  // 기간 설정 버튼 클릭 시 처리
  const handleSetRange = () => {
    if (startDate && endDate) {
      alert(`기간 설정: ${startDate.toLocaleDateString()} - ${endDate.toLocaleDateString()}`);
    } else {
      alert('시작일과 종료일을 모두 선택해주세요.');
    }
  };

  return (
    <div className="date-range-picker">
      <div>
        <DatePicker
          selected={startDate}
          onChange={(date) => setStartDate(date)}
          selectsStart
          startDate={startDate}
          endDate={endDate}
          dateFormat="yyyy-MM-dd"
          placeholderText="시작일 선택"
        />
      </div>

      <div>
        <DatePicker
          selected={endDate}
          onChange={(date) => setEndDate(date)}
          selectsEnd
          startDate={startDate}
          endDate={endDate}
          minDate={startDate}
          dateFormat="yyyy-MM-dd"
          placeholderText="종료일 선택"
        />
      </div>

      <div>
        <button onClick={handleSetRange} className="btn btn-primary">기간 설정</button>
      </div>
    </div>
  );
};

export default DateRangePicker;

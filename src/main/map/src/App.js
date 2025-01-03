import React, { useState, useEffect } from 'react';
import MonthRangePicker from './MonthRangePicker';
import MapContainer from "./MapContainer";
import DistrictSelector from './DistrictSelector';
import BuildYearSelect from './BuildYearSelect';
import AreaSelect from './AreaSelect';
import Scrap from './components/Graph/Scrap';
import UmdGraph from './components/Graph/UmdGraph';
import Header from "./components/Header";
import ScrapButton from "./components/ScrapButton";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import './App.css';

function App() {
  const [selectedSidoCd, setSelectedSidoCd] = useState('11'); // 서울특별시 기본값 (서울특별시 코드)
  const [selectedSggCd, setSelectedSggCd] = useState(''); // 시군구 선택
  const [center, setCenter] = useState({ latitude: 37.5665, longitude: 126.978 }); // 서울시 기본 좌표
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [nickname, setNickname] = useState('');
  const [isAreaModalOpen, setIsAreaModalOpen] = useState(false);
  const [startYearMonth, setStartYearMonth] = useState('202101');
  const [endYearMonth, setEndYearMonth] = useState('202412');
  const [selectedType, setSelectedType] = useState('building'); // 추가: 빌딩 또는 오피스텔 선택
  const [rentType, setRentType] = useState('월세');
  const [minArea, setMinArea] = useState(0);
  const [maxArea, setMaxArea] = useState(30);
  const [startYear, setStartYear] = useState(null);
  const [endYear, setEndYear] = useState(null);
  const [selectedFloor, setSelectedFloor] = useState(null);

  // 날짜 범위가 변경되면 호출될 함수
  const handleDateChange = (start, end) => {
    setStartYearMonth(start);
    setEndYearMonth(end);
  };

  const handleYearChange = (startyear, endyear) => {
    setStartYear(startyear === "null" ? null : startyear);
    setEndYear(endyear === "null" ? null : endyear);
  };

  // 면적 변경 함수
  const handleAreaChange = (minArea, maxArea) => {
    setMinArea(minArea);
    setMaxArea(maxArea);
  };

  useEffect(() => {
    // 로그인 여부 확인
    fetch("/users/check-auth", {
      method: "GET",
      credentials: "include", // 세션 쿠키 포함
    })
      .then((response) => response.json())
      .then((data) => {
        setIsAuthenticated(data.authenticated);
        setNickname(data.nickname);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  // 다른 버튼 클릭 시 면적 선택 모달 닫기
  const handleOtherButtonClick = () => {
    setIsAreaModalOpen(false); // 다른 버튼 클릭 시 모달 닫기
  };

  return (
    <div>
      <Header menu={'home'} isAuthenticated={isAuthenticated} nickname={nickname} />

      <div className='container-fluid'>
        <div className='row'>
          <main className='col-md-12 p-4'>
            <div className="select-container">
              <div className="select-day">
                <select
                  id="sido"
                  value={selectedSidoCd}
                  onChange={(e) => setSelectedSidoCd(e.target.value)}
                  style={{ display: 'none' }} // 서울특별시를 기본값으로 하고 옵션 숨김
                >
                  <option value="11">서울특별시</option>
                  <option value="26">부산광역시</option>
                </select>

                <DistrictSelector
                  selectedSidoCd={selectedSidoCd}
                  selectedSggCd={selectedSggCd}
                  setSelectedSggCd={setSelectedSggCd}
                />
                <MonthRangePicker onDateChange={handleDateChange} />
              </div>
              <div className="filter-container">
                <select
                  className="filter"
                  onChange={(e) => setSelectedType(e.target.value)}
                  value={selectedType}
                >
                  <option value="building">빌딩</option>
                  <option value="officeHotel">오피스텔</option>
                </select>
                <select className="filter" onClick={handleOtherButtonClick}
                  onChange={(e) => setRentType(e.target.value)}
                  value={rentType}>
                  <option value="">월세/전세</option>
                  <option value="월세">월세</option>
                  <option value="반전세">반전세</option>
                  <option value="전세">전세</option>
                </select>
                <AreaSelect
                  setIsModalOpen={setIsAreaModalOpen}
                  isModalOpen={isAreaModalOpen}
                  onDateChange={handleAreaChange}  // 면적 변경 함수 전달
                />
                <BuildYearSelect onClick={handleOtherButtonClick} onDateChange={handleYearChange} />
                <select
                  className="filter"
                  onChange={(e) => setSelectedFloor(e.target.value)}
                  value={selectedFloor || ''}>
                  <option value="">층</option>
                  <option value="지하">지하</option>
                  <option value="1층">1층</option>
                  <option value="2층">2층</option>
                  <option value="3층이상">3층이상</option>
                </select>
              </div>
            </div>
            <MapContainer
              center={center}
              startYearMonth={startYearMonth}
              endYearMonth={endYearMonth}
              selectedType={selectedType}
              rentType={rentType}
              startYear={startYear}
              endYear={endYear}
              selectedFloor={selectedFloor}
              minArea={minArea}
              maxArea={maxArea}
              selectedSidoCd={selectedSidoCd}
              selectedSggCd={selectedSggCd}
            />
            <Scrap />
            <UmdGraph />
          </main>
        </div>
      </div>
    </div>
  );
}

export default App;

import React, { useState, useEffect } from 'react';
import DateRangePicker from './DateRangePicker';
import MapContainer from "./MapContainer";
import DistrictSelector from './DistrictSelector'; // DistrictSelector 컴포넌트 가져오기
import Scrap from './components/Graph/Scrap';
import UmdGraph from './components/Graph/UmdGraph';
import Header from "./components/Header";
import ScrapButton from "./components/ScrapButton"
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import './App.css';

function App() {
  const [selectedSidoCd, setSelectedSidoCd] = useState('11'); // 서울특별시 기본값 (서울특별시 코드)
  const [selectedSggCd, setSelectedSggCd] = useState(''); // 시군구 선택
  const [center, setCenter] = useState({ latitude: 37.5665, longitude: 126.978 }); // 서울시 기본 좌표
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [nickname, setNickname] = useState('');

  const handleClick = () => {
    if(!isAuthenticated){
      if(window.confirm("로그인 하시겠습니까?")){
        window.location.href = `http://localhost/users/login`;
      }
    };
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

    // 구를 선택할 때마다 해당 구의 좌표를 가져오는 API 호출
    if (selectedSggCd) {
      fetch(`/api/regioncd/${selectedSidoCd}/${selectedSggCd}`) //RegioncdApiController에 요청
        .then((response) => response.json())
        .then((data) => {
          if (data.length > 0) {
            // umdCd가 '000'인 데이터를 찾아서 좌표 설정
            const region = data.find((region) => region.umdCd === '000');
            setCenter({
              latitude: region.latitude,
              longitude: region.longitude,
            });
          }
        })
        .catch((error) => {
          console.error("구 정보 가져오기 실패:", error);
        });
    }
  }, [selectedSidoCd, selectedSggCd]);

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
                  style={{ display: 'none' }} //서울특별시를 기본값으로 하고 옵션 숨김김
                >
                  <option value="11">서울특별시</option>
                  <option value="26">부산광역시</option>
                </select>

                <DistrictSelector
                  selectedSidoCd={selectedSidoCd}
                  selectedSggCd={selectedSggCd}
                  setSelectedSggCd={setSelectedSggCd}
                />
              </div>
              <div className="filter-container">
                <select className="filter">
                  <option value="building">빌딩</option>
                  <option value="officeHotel">오피스텔</option>
                </select>
                <select className="filter">
                  <option value="월세">월세</option>
                  <option value="반전세">반전세</option>
                  <option value="전세">전세</option>
                </select>
                <select className="filter">
                  <option value="">면적</option>
                </select>
                <select className="filter">
                  <option value="">연식</option>
                </select>
                <select className="filter">
                  <option value="">층</option>
                </select>
              </div>
            </div>
            <MapContainer center={center} />
            <Scrap />
            <UmdGraph />
          </main>
        </div>
      </div>
    </div>
  );
}

export default App;

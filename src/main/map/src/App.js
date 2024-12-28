import React, { useState, useEffect } from 'react';
import MapContainer from "./MapContainer";
import DistrictSelector from './DistrictSelector'; // DistrictSelector 컴포넌트 가져오기
import './App.css';

function App() {
  const [selectedSidoCd, setSelectedSidoCd] = useState('11'); // 서울특별시 기본값 (서울특별시 코드)
  const [selectedSggCd, setSelectedSggCd] = useState(''); // 시군구 선택
  const [center, setCenter] = useState({ latitude: 37.5665, longitude: 126.978 }); // 서울시 기본 좌표

  useEffect(() => {
    // 구를 선택할 때마다 해당 구의 좌표를 가져오는 API 호출
    if (selectedSggCd) {
      fetch(`/api/sgg/${selectedSggCd}`) //RegioncdApiController에 요청
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
  }, [selectedSggCd]);

  return (
    <div>
      <h4>지역 선택</h4>
      <div className="select-container">
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
      <MapContainer center={center} />
    </div>
  );
}

export default App;

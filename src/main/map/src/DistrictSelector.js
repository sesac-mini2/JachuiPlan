import React, { useEffect, useState } from 'react';

const DistrictSelector = ({ selectedSidoCd, selectedSggCd, setSelectedSggCd }) => {
  const [districts, setDistricts] = useState([]); // 시군구 목록 상태
  const [loading, setLoading] = useState(false); // 로딩 상태
  const [error, setError] = useState(null); // 에러 상태

  // selectedSidoCd가 변경될 때마다 실행되는 useEffect
  useEffect(() => {
    if (!selectedSidoCd) return; // 시도가 선택되지 않은 경우 아무것도 하지 않음
    setLoading(true); // 로딩 시작
    setError(null); // 에러 초기화

    // API 호출하여 해당 시도의 구 데이터를 가져옴
    fetch(`http://localhost/api/regioncd/${selectedSidoCd}`)
      .then(response => response.json()) // 응답을 JSON으로 파싱
      .then(data => {
        setDistricts(data); // 가져온 구 데이터를 districts 상태에 저장
        setLoading(false); // 로딩 종료
      })
      .catch(err => {
        setError(err.message); // 에러 발생 시 에러 메시지 설정
        setLoading(false); // 로딩 종료
      });
  }, [selectedSidoCd]); // selectedSidoCd가 변경될 때마다 실행

  // 로딩 중일 때 보여줄 내용
  if (loading) return <div>로딩 중...</div>;

  // 에러 발생 시 보여줄 내용
  if (error) return <div>오류 발생: {error}</div>;

  // 시군구 선택 변경 처리 함수
  const handleDistrictChange = (event) => {
    const selectedSggCd = event.target.value; // 선택된 시군구 코드
    setSelectedSggCd(selectedSggCd); // 부모 컴포넌트에서 전달된 setSelectedSggCd 함수 호출하여 상태 변경
  };

  return (
    <select
      value={selectedSggCd} // 선택된 시군구 값을 표시
      onChange={handleDistrictChange} // 시군구 선택이 변경되면 handleDistrictChange 함수 호출
    >
      <option value="">시군구를 선택하세요</option> {/* 기본 선택 옵션 */}
      {districts.map(region => (
        <option key={region.sggCd} value={region.sggCd}>
          {region.locataddNm} {/* 구 이름 */}
        </option>
      ))}
    </select>
  );
};

export default DistrictSelector;
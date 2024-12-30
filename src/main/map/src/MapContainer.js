import React, { useEffect, useRef, useState } from 'react';

const MapContainer = ({ center }) => {
  const mapContainerRef = useRef(null); // 지도 컨테이너를 참조하기 위한 ref
  const [map, setMap] = useState(null); // 지도 객체 상태

  // 지도 초기화 및 지도 영역 내 동들 표시
  useEffect(() => {
    // 지도 초기화: 지도 컨테이너가 존재하고, 지도 객체가 아직 생성되지 않은 경우
    if (mapContainerRef.current && !map) {
      const container = mapContainerRef.current; // 지도 컨테이너 DOM 참조
      const options = {
        center: new window.kakao.maps.LatLng(center.latitude, center.longitude), // 지도 중심 좌표
        level: 6, // 지도 확대 수준
      };
      const newMap = new window.kakao.maps.Map(container, options); // 새 지도 객체 생성
      setMap(newMap); // 상태에 지도 객체 저장

      // 지도 영역 내 동들을 표시
      updateMarkersInView(newMap); // 초기 마커 표시

      // 지도 범위가 바뀔 때마다 동들을 표시
      window.kakao.maps.event.addListener(newMap, 'idle', () => {
        updateMarkersInView(newMap); // 범위 변경 시 마커 갱신
      });
    } else if (map) {
      // 지도 객체가 이미 생성된 경우 (새로운 center로 지도 이동)
      const latLng = new window.kakao.maps.LatLng(center.latitude, center.longitude); // 새로운 중심 좌표
      map.setCenter(latLng); // 지도 중심을 새로운 좌표로 이동
    }
  }, [center, map]); // center나 map 상태가 변경될 때마다 실행

  // 지도 영역 내 동들을 가져와 마커로 표시하는 함수
  const updateMarkersInView = (map) => {
    // 지도 영역의 경계를 가져옴
    const bounds = map.getBounds();
    const north = bounds.getNorthEast().getLat(); // 북쪽 경계
    const east = bounds.getNorthEast().getLng(); // 동쪽 경계
    const south = bounds.getSouthWest().getLat(); // 남쪽 경계
    const west = bounds.getSouthWest().getLng(); // 서쪽 경계

    // 서버에서 현재 지도 영역 내 동들을 가져옴
    fetch(`http://localhost/api/regioncd/regionsInBounds?north=${north}&east=${east}&south=${south}&west=${west}`)
      .then(response => response.json()) // JSON 응답 처리
      .then(data => {
        // 각 동에 대해 마커 생성
        data.forEach(region => {
          const position = new window.kakao.maps.LatLng(region.latitude, region.longitude); // 마커 위치
          const marker = new window.kakao.maps.Marker({
            position, // 마커 위치
            title: region.locataddNm, // 마커 툴팁 (동 이름)
          });
          marker.setMap(map); // 마커를 지도에 표시
        });
      })
      .catch(err => {
        console.error('Error fetching markers: ', err); // 에러 처리
      });
  };

  return (
    
      <div ref={mapContainerRef} style={{ width: '100%', height: 'calc(100vh - 174px)' }} />
    
  )
};

export default MapContainer;
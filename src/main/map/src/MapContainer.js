import React, { useEffect, useRef, useState } from 'react';

const MapContainer = ({ center }) => {
  const mapContainerRef = useRef(null); // 지도 컨테이너를 참조하기 위한 ref
  const [map, setMap] = useState(null); // 지도 객체 상태
  const guOverlaysRef = useRef([]); // 구 마커 오버레이 상태를 담을 ref
  const dongOverlaysRef = useRef([]); // 동 마커 오버레이 상태를 담을 ref

  // 지도 초기화 및 지도 영역 내 동들 표시
  useEffect(() => {
    if (mapContainerRef.current && !map) {
      const container = mapContainerRef.current; // 지도 컨테이너 DOM 참조
      const options = {
        center: new window.kakao.maps.LatLng(center.latitude, center.longitude), // 지도 중심 좌표
        level: 5, // 초기 지도 확대 레벨
      };
      const newMap = new window.kakao.maps.Map(container, options); // 새 지도 객체 생성

      setMap(newMap); // 상태에 지도 객체 저장

      updateMarkersInView(newMap); // 지도 영역 내 마커 업데이트

      // 지도 범위가 바뀔 때마다 동들을 표시
      window.kakao.maps.event.addListener(newMap, 'idle', () => {
        updateMarkersInView(newMap); // 범위 변경 시 마커 갱신
      });
    } else if (map) {
      // 지도 객체가 이미 생성된 경우 (새로운 center로 지도 이동)
      const latLng = new window.kakao.maps.LatLng(center.latitude, center.longitude); // 새로운 중심 좌표
      map.setLevel(5);
      map.setCenter(latLng); // 지도 중심을 새로운 좌표로 이동
    }
  }, [center, map]); // center 상태가 변경될 때마다 실행

  // 오버레이 배열을 초기화하는 함수
  const clearOverlays = (overlays) => {
    overlays.forEach((overlay) => overlay.setMap(null)); // 각 오버레이를 지도에서 제거
    overlays.length = 0; // 배열 비우기
  };

  // 지도 영역 내 동들을 가져와 커스텀 오버레이를 표시하는 함수
  const updateMarkersInView = (map) => {
    // 지도 영역의 경계를 가져옴
    const bounds = map.getBounds();
    const north = bounds.getNorthEast().getLat();
    const east = bounds.getNorthEast().getLng();
    const south = bounds.getSouthWest().getLat();
    const west = bounds.getSouthWest().getLng();

    // 현재 지도 레벨을 가져옴
    const level = map.getLevel();

    // 서버에 현재 지도 영역 및 레벨 값 전달하여 동 데이터를 가져옴
    fetch(`http://localhost/api/regioncd/regionsInBounds?north=${north}&east=${east}&south=${south}&west=${west}&level=${level}`)
      .then(response => response.json())
      .then(data => {
        clearOverlays(guOverlaysRef.current); // 구 오버레이 초기화
        clearOverlays(dongOverlaysRef.current); // 동 오버레이 초기화

        const newGuOverlays = []; // 새 구 오버레이 배열
        const newDongOverlays = []; // 새 동 오버레이 배열

        // 응답 데이터 처리하여 마커 생성
        data.forEach(region => {
          const position = new window.kakao.maps.LatLng(region.latitude, region.longitude);
          const lastName = region.locataddNm.split(' ').pop();

          // 지도 레벨이 6 미만일 때 동 마커를 표시
          if (level < 6 && region.umdCd !== '000') {
            const content = `<div class="customoverlay">
                              <div class="info">
                                <span class="title">${lastName}</span>
                                <span class="price">2.5억</span>
                              </div>
                            </div>`;
            const customOverlay = new window.kakao.maps.CustomOverlay({
              map: map,
              position: position,
              content: content,
              yAnchor: 1,
            });
            newDongOverlays.push(customOverlay); // 동 오버레이 배열에 추가
          }
          // 지도 레벨이 6 이상일 때 구 마커를 표시
          else if (level >= 6 && region.umdCd === '000' && region.sggCd !== '000') {
            const content = `<div class="customoverlay">
                              <div class="info">
                                <span class="title">${lastName}</span>
                                <span class="price">2.5억</span>
                              </div>
                            </div>`;
            const customOverlay = new window.kakao.maps.CustomOverlay({
              map: map,
              position: position,
              content: content,
              yAnchor: 1,
            });
            newGuOverlays.push(customOverlay); // 구 오버레이 배열에 추가
          }
        });

        guOverlaysRef.current = newGuOverlays; // 구 오버레이 상태 업데이트
        dongOverlaysRef.current = newDongOverlays; // 동 오버레이 상태 업데이트
      })
      .catch(err => {
        console.error('Error fetching markers: ', err);
      });
  };

  return (
    <div ref={mapContainerRef} style={{ width: '100%', height: 'calc(100vh - 174px)' }} />
  );
};

export default MapContainer;

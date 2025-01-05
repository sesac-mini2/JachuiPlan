import React, { useEffect, useRef, useState } from 'react';

const MapContainer = ({
  center,
  startYearMonth,
  endYearMonth,
  selectedType,
  rentType,
  minBuildYear,
  maxBuildYear,
  minFloor,
  maxFloor,
  minArea,
  maxArea,
  selectedSggCd,
  selectedSidoCd,
  onClickOverlay,
}) => {
  const mapContainerRef = useRef(null);
  const [map, setMap] = useState(null);
  const guOverlaysRef = useRef([]);
  const dongOverlaysRef = useRef([]);
  const regionDataRef = useRef([]);

  const propsRef = useRef({
    startYearMonth,
    endYearMonth,
    selectedType,
    rentType,
    minBuildYear,
    maxBuildYear,
    minFloor,
    maxFloor,
    minArea,
    maxArea,
  });

  const isMouseDownRef = useRef(false);  // 마우스 버튼 눌림 여부
  const isDraggingRef = useRef(false);  // 드래깅 상태 여부

  const handleMouseDown = () => {
    isMouseDownRef.current = true;
    isDraggingRef.current = false;
  };

  const handleMouseMove = () => {
    if (isMouseDownRef.current) {
      isDraggingRef.current = true;
    }
  };

  const handleMouseUp = (regionId) => {
    if (!isDraggingRef.current) {
      onClickOverlay(regionId);  // 드래깅 상태가 아니면 클릭 이벤트 실행
    }
    isMouseDownRef.current = false;
    isDraggingRef.current = false;
  };

  // 최신 props 업데이트
  useEffect(() => {
    propsRef.current = {
      startYearMonth,
      endYearMonth,
      selectedType,
      rentType,
      minBuildYear,
      maxBuildYear,
      minFloor,
      maxFloor,
      minArea,
      maxArea,
    };
  }, [startYearMonth, endYearMonth, selectedType, rentType, minBuildYear, maxBuildYear, minFloor, maxFloor, minArea, maxArea]);

  const clearOverlays = (overlays) => {
    overlays.forEach((overlay) => overlay.setMap(null));
    overlays.length = 0;
  };

  const updateMarkersInView = (map) => {
    if (!map) return;

    const currentProps = propsRef.current;

    const bounds = map.getBounds();
    const north = bounds.getNorthEast().getLat();
    const east = bounds.getNorthEast().getLng();
    const south = bounds.getSouthWest().getLat();
    const west = bounds.getSouthWest().getLng();
    const level = map.getLevel();

    fetch(`http://localhost/api/regioncd/regionsInBounds?north=${north}&east=${east}&south=${south}&west=${west}&level=${level}`)
      .then((response) => response.json())
      .then((regionData) => {
        regionDataRef.current = regionData;
        const sggcdsSet = new Set();

        regionData.forEach((region) => {
          if (region.umdCd !== '000') {
            sggcdsSet.add(region.sidoCd + region.sggCd);
          }
        });

        const minAreaInSquareMeters = currentProps.minArea * 3.3058;
        const maxAreaInSquareMeters = currentProps.maxArea * 3.3058;

        const apiUrl =
          currentProps.selectedType === 'building'
            ? `http://localhost/api/building/average?sggcds=${Array.from(sggcdsSet).join(',')}&startYearMonth=${currentProps.startYearMonth}&endYearMonth=${currentProps.endYearMonth}&rentType=${currentProps.rentType}&minBuildYear=${currentProps.minBuildYear || ''}&maxBuildYear=${currentProps.maxBuildYear || ''}&minFloor=${currentProps.minFloor || ''}&maxFloor=${currentProps.maxFloor || ''}&minArea=${minAreaInSquareMeters || ''}&maxArea=${maxAreaInSquareMeters || ''}`
            : `http://localhost/api/officeHotel/average?sggcds=${Array.from(sggcdsSet).join(',')}&startYearMonth=${currentProps.startYearMonth}&endYearMonth=${currentProps.endYearMonth}&rentType=${currentProps.rentType}&minBuildYear=${currentProps.minBuildYear || ''}&maxBuildYear=${currentProps.maxBuildYear || ''}&minFloor=${currentProps.minFloor || ''}&maxFloor=${currentProps.maxFloor || ''}&minArea=${minAreaInSquareMeters || ''}&maxArea=${maxAreaInSquareMeters || ''}`;

        return fetch(apiUrl)
          .then((response) => response.json())
          .then((avgPriceData) => ({ regionData, avgPriceData }));
      })
      .then(({ regionData, avgPriceData }) => {
        clearOverlays(guOverlaysRef.current);
        clearOverlays(dongOverlaysRef.current);

        const newGuOverlays = [];
        const newDongOverlays = [];

        const avgPriceMap = new Map(
          avgPriceData.map((item) => {
            const price =
              currentProps.rentType === '전세' || currentProps.rentType === '반전세'
                ? item.avgDeposit
                : item.avgMonthlyRent;
            return [item.umdnm, price];
          })
        );

        regionData.forEach((region) => {
          const position = new window.kakao.maps.LatLng(region.latitude, region.longitude);
          const lastName = region.locataddNm.split(' ').pop();
          const averagePrice = Math.floor(avgPriceMap.get(lastName) || 0);
          const priceLabel = averagePrice === 0 ? '원' : '만원';

          if (level < 6 && region.umdCd !== '000') {
            const content = document.createElement('div'); // DOM 요소 생성
            content.className = 'customoverlay';
            content.innerHTML = `<div class="info">
                                   <span class="title">${lastName}</span>
                                   <span class="price">${averagePrice}${priceLabel}</span>
                                 </div>`;

            content.onmousedown = handleMouseDown;
            content.onmousemove = handleMouseMove;
            content.onmouseup = () => handleMouseUp(region.id);

            const customOverlay = new window.kakao.maps.CustomOverlay({
              map: map,
              position: position,
              content: content,
              yAnchor: 1,
            });
            newDongOverlays.push(customOverlay);
          } else if (level >= 6 && region.umdCd === '000' && region.sggCd !== '000') {
            const content = `<div class="customoverlay">
                              <div class="info">
                                <span class="title-Guname">${lastName}</span>
                              </div>
                            </div>`;
            const customOverlay = new window.kakao.maps.CustomOverlay({
              map: map,
              position: position,
              content: content,
              yAnchor: 1,
            });
            newGuOverlays.push(customOverlay);
          }
        });

        guOverlaysRef.current = newGuOverlays;
        dongOverlaysRef.current = newDongOverlays;
      })
      .catch((err) => {
        console.error('Error updating markers: ', err);
      });
  };

  useEffect(() => {
    console.log('map:', map);  // map 객체 확인
    console.log('selectedSggCd:', selectedSggCd);  // selectedSggCd 값 확인
    if (map && selectedSggCd) {
      fetch(`/api/regioncd/${selectedSidoCd}/${selectedSggCd}`) // RegioncdApiController에 요청
        .then((response) => response.json())
        .then((data) => {
          if (data.length > 0) {
            const region = data.find((region) => region.umdCd === '000');
            if (region) {
              const latLng = new window.kakao.maps.LatLng(region.latitude, region.longitude);
              map.setLevel(5);
              map.setCenter(latLng);  // 지도 중앙 위치 업데이트
            }
          }
        })
        .catch((error) => {
          console.error("Error fetching region data:", error);
        });
    }
  }, [selectedSggCd, map]);  // `selectedSggCd` 변경 시, 지도 중앙 업데이트

  useEffect(() => {
    if (mapContainerRef.current && !map) {
      const container = mapContainerRef.current;
      const options = {
        center: new window.kakao.maps.LatLng(center.latitude, center.longitude),
        level: 5,
      };
      const newMap = new window.kakao.maps.Map(container, options);
      setMap(newMap);

      window.kakao.maps.event.addListener(newMap, 'idle', () => updateMarkersInView(newMap));
    }
  }, []);

  useEffect(() => {
    if (map) {
      updateMarkersInView(map);
    }
  }, [map, startYearMonth, endYearMonth, selectedType, rentType, minBuildYear, maxBuildYear, minFloor, maxFloor, minArea, maxArea]);

  return <div ref={mapContainerRef} style={{ width: '100%', height: 'calc(100vh - 174px)' }} />;

};

export default MapContainer;

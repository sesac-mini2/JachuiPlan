import axios from 'axios';
import React, { useState, useEffect, useRef } from 'react';
import { select, scaleBand, axisBottom, scaleLinear, axisLeft, min, max } from 'd3';

function Scarp({targetUmdSggcd, startYearMonth, endYearMonth,
  selectedType,
  rentType,
  minBuildYear,
  maxBuildYear,
  minFloor,
  maxFloor,
  minArea,
  maxArea
}) {
    const [data, setData] = useState([]);

    const ref = useRef();

    const containerWidth = 650;
    const containerHeight = 500;

    const transitionDuration = 500;

    const countColor = '#69b3a2';

    function getData(sggcd, startYearMonth, endYearMonth) {
      axios.get(`/api/${selectedType}/average`, {
        params: {
          sggcds: sggcd,
          startYearMonth: startYearMonth,
          endYearMonth: endYearMonth,
          rentType: rentType,
          minBuildYear: minBuildYear,
          maxBuildYear: maxBuildYear,
          minFloor: minFloor,
          maxFloor: maxFloor,
          minArea: minArea,
          maxArea: maxArea
        },
        // paramsSerializer: function(paramObj) {
        //   const params = new URLSearchParams();
        //   for (const key in paramObj) {
        //       params.append(key, paramObj[key]);
        //   }
        //   return params.toString();
        // }
      })
      .then((res) => {
        setData(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
    }

    function updateGraph(data) {
      const margin = { top: 30, right: 30, bottom: 30, left: 30 };
      const width = containerWidth - margin.left - margin.right;
      const height = containerHeight - margin.top - margin.bottom;

      const svg = select(ref.current);

      // x축
      const xScale = scaleBand()
      .range([margin.left, width])
      .domain(data.map(d => d.umdnm))
      .padding(0.4);
      svg.selectAll('.x-axis')
        .join(
            enter => enter.append("g"),
            update => update.transition()
                .duration(transitionDuration)
                .call(axisBottom(xScale))
        )
        .attr("class", "x-axis")
        .attr("transform", `translate(0,${height - margin.bottom})`)
        .selectAll("text") // x축 텍스트 선택
        .attr("transform", "rotate(-45)")
        .style("text-anchor", "end") // 텍스트 정렬 (끝쪽 정렬)
        .attr("dx", "-0.5em") // x축 텍스트의 위치를 약간 조정

      // y축
      const yScale = scaleLinear()
        .domain([min(data.map(d => d.avgMonthlyRent)) * 0.7, max(data.map(d => d.avgMonthlyRent))])
        .range([height - margin.bottom, margin.top]);
      svg.selectAll(".y-axis")
        .join(
            enter => enter.append("g"),
            update => update.transition()
                .duration(transitionDuration)
                .call(axisLeft(yScale))
        )
        .attr("class", "y-axis")
        .attr("transform", `translate(${margin.left},0)`);

      // 바형 그래프
      const bars = svg.select(".mybar")
      .selectAll(".avgRent")
      .data(data, d => d.umdnm);

      bars.join(
        enter => enter
            .append("rect")
            .attr("class", "avgRent")
            .attr("x", d => xScale(d.umdnm))
            .attr("y", d => yScale(d.avgMonthlyRent))
            .attr("width", xScale.bandwidth())
            .attr("height", d => height - margin.top - yScale(d.avgMonthlyRent))
            .attr("opacity", 0)
            .transition()
            .delay(transitionDuration)
            .attr("opacity", 1),
        update => update
            .transition()
            .duration(transitionDuration)
            .attr("x", d => xScale(d.umdnm))
            .attr("y", d => yScale(d.avgMonthlyRent))
            .attr("width", xScale.bandwidth())
            .attr("height", d => height - margin.top - yScale(d.avgMonthlyRent))
            .attr("opacity", 1),
        exit => exit.remove()
      )
      .attr("fill", countColor);
    }

    useEffect(() => {
      updateGraph(data);
    }, [data]);

    // 초기 데이터 로드
    useEffect(() => {
      if(!targetUmdSggcd) {
        return;
      }
      getData(targetUmdSggcd, startYearMonth, endYearMonth);
    }, [targetUmdSggcd, startYearMonth, endYearMonth, 
      selectedType,
      rentType,
      minBuildYear,
      maxBuildYear,
      minFloor,
      maxFloor,
      minArea,
      maxArea]);

    return (
        <div>
            <h3>최근 1년간 평균 월세</h3>

            <svg className="svg-top" ref={ref} style={{ width: containerWidth, height: containerHeight }}>
                <g className="x-axis" />
                <g className="y-axis" />
                <g className="mybar" />
            </svg><br />
        </div>
    );
}

export default Scarp;

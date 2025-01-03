import axios from 'axios';
import React, { useState, useEffect, useRef } from 'react';
import { select, scaleBand, axisBottom, scaleLinear, axisLeft, min, max } from 'd3';

function Scarp() {
    const [data, setData] = useState([]);

    const [sggcds, setSggcds] = useState(['11590', '11305']);
    const [startYearMonth, setStartYearMonth] = useState('202301');
    const [endYearMonth, setEndYearMonth] = useState('202307');

    const ref = useRef();

    const containerWidth = 500;
    const containerHeight = 500;

    const transitionDuration = 500;

    const countColor = '#69b3a2';

    function getData(sggcds, startYearMonth, endYearMonth) {
      axios.get(`/api/building/average`, {
        params: {
          sggcds: sggcds,
          startYearMonth: startYearMonth,
          endYearMonth: endYearMonth,
          rentType: '월세'
        },
        paramsSerializer: function(paramObj) {
          const params = new URLSearchParams();
          for (const key in paramObj) {
              params.append(key, paramObj[key]);
          }
          return params.toString();
        }
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
      getData(['11590', '11305'], '202307', '202312');
    }, []);

    return (
        <div>
            <svg className="svg-top" ref={ref} style={{ width: containerWidth, height: containerHeight }}>
                <g className="x-axis" />
                <g className="y-axis" />
                <g className="mybar" />
            </svg><br />

            시군구코드: <input type="text" value={sggcds} onChange={e => setSggcds(e.target.value)} /><br />
            시작년월: <input type="number" value={startYearMonth} onChange={e => setStartYearMonth(e.target.value)} /><br />
            끝년월: <input type="number" value={endYearMonth} onChange={e => setEndYearMonth(e.target.value)} />

            <button onClick={() => getData(sggcds, startYearMonth, endYearMonth)}>업데이트</button>
        </div>
    );
}

export default Scarp;

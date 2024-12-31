// import axios from 'axios';
import React, { useState, useEffect, useRef } from 'react';
import { select, axisBottom, scaleLinear, axisLeft, min, max, scaleTime, timeMonth, timeFormat, timeDay, axisRight } from 'd3';
import './UmdGraph.css';

const dataSet = [
    {
        "umdnm": "흑석동",
        "data": [
            {
                "avgMonthlyRent": 53.85,
                "avgDeposit": 453.7,
                "count": 27,
                "ym": "202301"
            },
            {
                "avgMonthlyRent": 50.19,
                "avgDeposit": 443.75,
                "count": 48,
                "ym": "202302"
            },
            {
                "avgMonthlyRent": 61.8,
                "avgDeposit": 380,
                "count": 5,
                "ym": "202303"
            },
            {
                "avgMonthlyRent": 48.33,
                "avgDeposit": 333.33,
                "count": 3,
                "ym": "202304"
            },
            {
                "avgMonthlyRent": 55,
                "avgDeposit": 475,
                "count": 4,
                "ym": "202305"
            },
        ],
    },
    {
        "umdnm": "어떤동",
        "data": [
            {
                "avgMonthlyRent": 5,
                "avgDeposit": 475,
                "count": 4,
                "ym": "202212"
            },
            {
                "avgMonthlyRent": 5.85,
                "avgDeposit": 453.7,
                "count": 27,
                "ym": "202301"
            },
            {
                "avgMonthlyRent": 5.19,
                "avgDeposit": 443.75,
                "count": 48,
                "ym": "202302"
            },
            {
                "avgMonthlyRent": 6.8,
                "avgDeposit": 380,
                "count": 5,
                "ym": "202303"
            },
            {
                "avgMonthlyRent": 4.33,
                "avgDeposit": 333.33,
                "count": 3,
                "ym": "202304"
            },
        ],
    },
    {
        "umdnm": "흑석동",
        "data": [
            {
                "avgMonthlyRent": 50.19,
                "avgDeposit": 443.75,
                "count": 48,
                "ym": "202302"
            },
            {
                "avgMonthlyRent": 61.8,
                "avgDeposit": 380,
                "count": 5,
                "ym": "202303"
            },
            {
                "avgMonthlyRent": 48.33,
                "avgDeposit": 333.33,
                "count": 3,
                "ym": "202304"
            },
            {
                "avgMonthlyRent": 55,
                "avgDeposit": 475,
                "count": 4,
                "ym": "202305"
            },
            {
                "avgMonthlyRent": 53.85,
                "avgDeposit": 453.7,
                "count": 27,
                "ym": "202306"
            },
        ],
    },
]

function UmdGraph() {
    const [data, setData] = useState(dataSet[0]);
    const [testNum, setTestNum] = useState(0);

    // const [startYearMonth, setStartYearMonth] = useState('202301');
    // const [endYearMonth, setEndYearMonth] = useState('202307');

    const ref = useRef();

    const containerWidth = 800;
    const containerHeight = 500;

    const transitionDuration = 500;

    const countColor = '#69b3a2';
    const avgMonthlyRentColor = '#F3b3a2';
    const avgDepositColor = '#1372a2';

    function getData(num) {
        setData(dataSet[num]);
    }

    function updateGraph(ogdata) {
        const umdnm = ogdata.umdnm;
        const data = ogdata.data.map(d => {
            return {
                ...d,
                date : new Date(d.ym.slice(0, 4), d.ym.slice(4, 6)-1)
            }});
        const margin = { top: 30, right: 30, bottom: 30, left: 30 };
        const width = containerWidth - margin.left - margin.right;
        const height = containerHeight - margin.top - margin.bottom;

        const svg = select(ref.current);

        // 동 추가
        svg.selectAll(".dong-group")
            .data([umdnm])
            .join(
                enter => {
                    const group = enter.append("g") // 그룹 생성
                        .attr("class", "dong-group");

                    // 직사각형 추가
                    group.append("rect")
                        .attr("class", "dong-bg")
                        .attr("x", margin.right + (width - margin.right) / 2)
                        .attr("y", margin.top)
                        .attr("rx", 5) // 모서리 둥글게
                        .attr("ry", 5);

                    // 동 이름
                    group.append("text")
                        .attr("class", "dong")
                        .attr("x", margin.right + (width - margin.right) / 2)
                        .attr("y", margin.top)
                        .attr("text-anchor", "end")
                        .attr("font-size", "20px")
                        .text(d => `${d}`);
                    return group;
                },
                update => update.select(".dong")
                        .text(d => `${d}`),
                exit => exit.remove()
            );

            // 범례 추가
            svg.selectAll(".legend-group")
                .data([umdnm])
                .join(
                    enter => {
                        const group = enter.append("g")
                            .attr("class", "legend-group");

                            group.append("circle").attr("cx",width - margin.right - 200).attr("cy", 20).attr("r", 6).style("fill", avgMonthlyRentColor)
                            group.append("circle").attr("cx",width - margin.right - 200).attr("cy", 40).attr("r", 6).style("fill", avgDepositColor)
                            group.append("circle").attr("cx",width - margin.right - 200).attr("cy", 60).attr("r", 6).style("fill", countColor)
                            group.append("text").attr("x", width - margin.right - 185).attr("y", 20).text("월평균 월세").style("font-size", "15px").attr("alignment-baseline","middle")
                            group.append("text").attr("x", width - margin.right - 185).attr("y", 40).text("월평균 보증금").style("font-size", "15px").attr("alignment-baseline","middle")
                            group.append("text").attr("x", width - margin.right - 185).attr("y", 60).text("월간 거래량").style("font-size", "15px").attr("alignment-baseline","middle")

                        return group;
                    }
                );

        // 박스 크기 업데이트
        svg.selectAll(".dong-group").each(function() {
            const group = select(this);
            const text = group.select(".dong");
            const rect = group.select(".dong-bg");

            // 텍스트 크기 계산
            const bbox = text.node().getBBox();

            // 직사각형 위치 및 크기 업데이트
            rect.attr("x", bbox.x - 10) // 텍스트 왼쪽으로 약간 여백
                .attr("y", bbox.y - 5) // 텍스트 위로 약간 여백
                .attr("width", bbox.width + 20) // 텍스트 너비 + 좌우 여백
                .attr("height", bbox.height + 10); // 텍스트 높이 + 상하 여백
        });

        // x축
        const xScale = scaleTime(
            // timeDay.offset은 domain 범위를 data의 시간 범위보다 조금 더 넓게 설정해서 양끝에 padding을 주기 위함
            [timeDay.offset(min(data.map(d => d.date)), -10), timeDay.offset(max(data.map(d => d.date)), 20)],
            [margin.left, width]
        );
        svg.selectAll(".x-axis")
        .join(
            enter => enter.append("g"),
            update => update.transition()
                .duration(transitionDuration)
                .call(axisBottom(xScale)
                    .ticks(timeMonth, 1)
                    .tickFormat(timeFormat("%y년 %m월")))
        )
        .attr("class", "x-axis")
        .attr("transform", `translate(0,${height - margin.bottom})`);

        // y축 (월세)
        const yScaleL = scaleLinear()
            .domain([min(data.map(d => d.avgMonthlyRent)) * 0.7, max(data.map(d => d.avgMonthlyRent)) * 1.1])
            .range([height - margin.bottom, margin.top]);
        svg.selectAll(".avgMonthlyRentAxis")
            .data([null])
            .join(
                enter => enter.append("g")
                    .attr("class", "avgMonthlyRentAxis")
                    .attr("transform", `translate(${margin.left},0)`),
                update => update.transition()
                    .duration(transitionDuration)
                    .call(axisLeft(yScaleL))
            );

        // 거래량 스케일
        const yScaleCount = scaleLinear()
            .domain([min(data.map(d => d.count)) * 0.7, max(data.map(d => d.count)) * 1.1])
            .range([height - margin.bottom, margin.top]);

        // y축 (보증금)
        const yScaleR = scaleLinear()
            .domain([min(data.map(d => d.avgDeposit)) * 0.7, max(data.map(d => d.avgDeposit)) * 1.1])
            .range([height - margin.bottom, margin.top]);
        svg.selectAll(".avgDepositAxis")
            .data([null])
            .join(
                enter => enter.append("g"),
                update => update.transition()
                    .duration(transitionDuration)
                    .call(axisRight(yScaleR))
            )
            .attr("class", "avgDepositAxis")
            .attr("transform", `translate(${width},0)`);

        // bar chart (count)
        const bars = svg
        .selectAll(".count")
        .data(data, d => d.date);

        bars.join(
        enter => enter
            .append("rect")
            .attr("class", "count")
            .attr("x", d => xScale(d.date))
            .attr("y", d => yScaleCount(d.count))
            .attr("width", 10)
            .attr("height", d => height - margin.top - yScaleCount(d.count))
            .attr("fill", countColor)
            .attr("opacity", 0)
            .transition()
            .delay(transitionDuration)
            .attr("opacity", 1),
        update => update
            .transition()
            .duration(transitionDuration)
            .attr("x", d => xScale(d.date))
            .attr("y", d => yScaleCount(d.count))
            .attr("width", 10)
            .attr("height", d => height - margin.top - yScaleCount(d.count))
            .attr("opacity", 1),
        exit => exit.remove()
        );

        // Bar chart 위에 텍스트 추가
        svg.selectAll(".bar-text")
        .data(data, d => d.date)
        .join(
            enter => enter.append("text")
                .attr("class", "bar-text")
                .attr("x", d => xScale(d.date) + 5) // 막대 중심으로 정렬
                .attr("y", d => yScaleCount(d.count) - 5) // 막대 상단 약간 위
                .attr("text-anchor", "middle")
                .attr("fill", countColor)
                .attr("opacity", 0)
                .text(d => d.count)
                .transition()
                .delay(transitionDuration)
                .attr("opacity", 1),
            update => update.transition()
                .duration(transitionDuration)
                .attr("opacity", 1)
                .attr("x", d => xScale(d.date) + 5)
                .attr("y", d => yScaleCount(d.count) - 5)
                .text(d => d.count), // 업데이트된 값
            exit => exit.remove()
        );

        // line chart (avgMonthlyRent)
        // 데이터에서 선분 생성
        const segmentsMonthlyRent = data.slice(0, -1).map((d, i) => ({
            date: d.date,
            x1: xScale(d.date),
            y1: yScaleL(d.avgMonthlyRent),
            x2: xScale(data[i + 1].date),
            y2: yScaleL(data[i + 1].avgMonthlyRent)
        }));

        svg.selectAll(".line-segmentL")
            .data(segmentsMonthlyRent, segments => segments.date)
            .join(
                enter => enter.append("line")
                    .attr("class", "line-segmentL")
                    .attr("x1", d => d.x1)
                    .attr("y1", d => d.y1)
                    .attr("x2", d => d.x2)
                    .attr("y2", d => d.y2)
                    .transition()
                    .delay(transitionDuration)
                    .attr("stroke-width", 4)
                    .attr("stroke", avgMonthlyRentColor),
                update => update.transition()
                    .duration(transitionDuration)
                    .attr("x1", d => d.x1)
                    .attr("y1", d => d.y1)
                    .attr("x2", d => d.x2)
                    .attr("y2", d => d.y2)
                    .attr("stroke-width", 4)
                    .attr("stroke", avgMonthlyRentColor),
                exit => exit.remove()
            );

        // Add dots
        svg.selectAll(".myCirclesL")
            .data(data, d => d.date)
            .join(
                enter => enter.append("circle")
                    .attr("class", "myCirclesL")
                    .attr("fill", "red")
                    .attr("stroke", "none")
                    .attr("cx", d => xScale(d.date))
                    .attr("cy", d => yScaleL(d.avgMonthlyRent))
                    .transition()
                    .delay(transitionDuration)
                    .attr("r", 3),
                update => update
                    .transition()
                    .duration(transitionDuration)
                    .attr("cx", d => xScale(d.date))
                    .attr("cy", d => yScaleL(d.avgMonthlyRent))
                    .attr("r", 3),
                exit => exit.remove()
            )

        // Line chart dot 옆에 텍스트 추가
        svg.selectAll(".line-textL")
        .data(data, d => d.date)
        .join(
            enter => enter.append("text")
                .attr("class", "line-textL")
                .attr("x", d => xScale(d.date) + 5)
                .attr("y", d => yScaleL(d.avgMonthlyRent) - 5)
                .attr("text-anchor", "start")
                .attr("fill", avgMonthlyRentColor)
                .attr("opacity", 0)
                .attr("font-size", "11px")
                .text(d => d.avgMonthlyRent.toFixed(1))
                .transition()
                .delay(transitionDuration)
                .attr("opacity", 1),
            update => update.transition()
                .duration(transitionDuration)
                .attr("opacity", 1)
                .attr("x", d => xScale(d.date) + 5)
                .attr("y", d => yScaleL(d.avgMonthlyRent) - 5)
                .text(d => d.avgMonthlyRent.toFixed(1)),
            exit => exit.remove()
        );

        // line chart (avgDeposit)
        // 데이터에서 선분 생성
        const segmentsDeposit = data.slice(0, -1).map((d, i) => ({
            date: d.date,
            x1: xScale(d.date),
            y1: yScaleR(d.avgDeposit),
            x2: xScale(data[i + 1].date),
            y2: yScaleR(data[i + 1].avgDeposit)
        }));

        svg.selectAll(".line-segmentR")
            .data(segmentsDeposit, segments => segments.date)
            .join(
                enter => enter.append("line")
                    .attr("class", "line-segmentR")
                    .attr("x1", d => d.x1)
                    .attr("y1", d => d.y1)
                    .attr("x2", d => d.x2)
                    .attr("y2", d => d.y2)
                    .transition()
                    .delay(transitionDuration)
                    .attr("stroke-width", 4)
                    .attr("stroke", avgDepositColor),
                update => update.transition()
                    .duration(transitionDuration)
                    .attr("x1", d => d.x1)
                    .attr("y1", d => d.y1)
                    .attr("x2", d => d.x2)
                    .attr("y2", d => d.y2)
                    .attr("stroke-width", 4)
                    .attr("stroke", avgDepositColor),
                exit => exit.remove()
            );

        // Add dots
        svg.selectAll(".myCirclesR")
            .data(data, d => d.date)
            .join(
                enter => enter.append("circle")
                    .attr("class", "myCirclesR")
                    .attr("fill", "red")
                    .attr("stroke", "none")
                    .attr("cx", d => xScale(d.date))
                    .attr("cy", d => yScaleR(d.avgDeposit))
                    .transition()
                    .delay(transitionDuration)
                    .attr("r", 3),
                update => update
                    .transition()
                    .duration(transitionDuration)
                    .attr("cx", d => xScale(d.date))
                    .attr("cy", d => yScaleR(d.avgDeposit))
                    .attr("r", 3),
                exit => exit.remove()
            )

        // Line chart dot 옆에 텍스트 추가
        svg.selectAll(".line-textR")
        .data(data, d => d.date)
        .join(
            enter => enter.append("text")
                .attr("class", "line-textR")
                .attr("x", d => xScale(d.date) + 5)
                .attr("y", d => yScaleR(d.avgDeposit) - 5)
                .attr("text-anchor", "start")
                .attr("fill", avgDepositColor)
                .attr("opacity", 0)
                .attr("font-size", "11px")
                .text(d => d.avgDeposit.toFixed(1))
                .transition()
                .delay(transitionDuration)
                .attr("opacity", 1),
            update => update.transition()
                .duration(transitionDuration)
                .attr("opacity", 1)
                .attr("x", d => xScale(d.date) + 5)
                .attr("y", d => yScaleR(d.avgDeposit) - 5)
                .text(d => d.avgDeposit.toFixed(1)),
            exit => exit.remove()
        );
    }

    useEffect(() => {
        updateGraph(data);
    }, [data]);

    // 초기 데이터 로드
    useEffect(() => {
    //   getData(0);
    }, []);

    return (
        <div>
            <svg className="svg-top" ref={ref} style={{ width: containerWidth, height: containerHeight }}>
                <g className="x-axis" />
                <g className="y-axisR" />
            </svg><br />

            {/* 시작년월: <input type="number" value={startYearMonth} onChange={e => setStartYearMonth(e.target.value)} /><br />
            끝년월: <input type="number" value={endYearMonth} onChange={e => setEndYearMonth(e.target.value)} /> */}

            <button onClick={() => {
                setTestNum((testNum+1)%3);
                getData(testNum);
            }}>업데이트</button>
        </div>
    );
}

export default UmdGraph;

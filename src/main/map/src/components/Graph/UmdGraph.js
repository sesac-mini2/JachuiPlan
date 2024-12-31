// import axios from 'axios';
import React, { useState, useEffect, useRef } from 'react';
import { select, scaleBand, axisBottom, scaleLinear, axisLeft, min, max, line } from 'd3';

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

    const containerWidth = 500;
    const containerHeight = 500;

    const transitionDuration = 500;
    
    function getData(num) {
        setData(dataSet[num]);
    }

    function updateGraph(ogdata) {
        const data = ogdata.data;
        const margin = { top: 30, right: 30, bottom: 30, left: 30 };
        const width = containerWidth - margin.left - margin.right;
        const height = containerHeight - margin.top - margin.bottom;

        const svg = select(ref.current);

        // x축
        const xScale = scaleBand()
        .range([margin.left, width])
        .domain(data.map(d => d.ym))
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

        // Add the line
        svg.selectAll(".line-path")
        .data([data]) // 데이터 바인딩 (단일 path)
        .join(
            enter => enter.append("path")
                .attr("class", "line-path")
                .attr("fill", "none")
                .attr("stroke", "#69b3a2")
                .attr("stroke-width", 4)
                .attr("d", line()
                    .x(d => xScale(d.ym))
                    .y(d => yScale(d.avgMonthlyRent))
                ),
            update => update
                .transition()
                .duration(transitionDuration)
                .attr("d", line()
                    .x(d => xScale(d.ym))
                    .y(d => yScale(d.avgMonthlyRent))
                )
        );

        // Add dots
        svg.selectAll(".myCircles")
            .data(data)
            .join(
                enter => enter.append("circle")
                    .attr("class", "myCircles")
                    .attr("fill", "red")
                    .attr("stroke", "none")
                    .attr("cx", d => xScale(d.ym))
                    .attr("cy", d => yScale(d.avgMonthlyRent))
                    .attr("r", 3),
                update => update
                    .transition()
                    .duration(transitionDuration)
                    .attr("cx", d => xScale(d.ym))
                    .attr("cy", d => yScale(d.avgMonthlyRent)),
                exit => exit.remove()
            )
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
                <g className="y-axis" />
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

import React, { useState } from "react";
import { Link, useLocation } from "react-router-dom";

const Sidebar = (props) => {
  const menus = {
    community: [
      { to: "/board/infolist", label: "정보 게시판" },
      { to: "/board/generallist", label: "일반 게시판" },
      { to: "/board/qnalist", label: "QNA 게시판" },
    ],
    mypage: [
    ],
  };

  const location = useLocation(); // 현재 경로를 가져옴
  const currentPath = location.pathname; // 현재 경로
  const [activeType, setActiveType] = useState("");

  // 경로를 기반으로 활성화된 타입 결정
  React.useEffect(() => {
    if (currentPath.includes("infolist")) {
      setActiveType("0");
    } else if (currentPath.includes("generallist")) {
      setActiveType("1");
    } else if (currentPath.includes("qnalist")) {
      setActiveType("2");
    } else {
      setActiveType("");
    }
  }, [currentPath]);

  return (
    <nav className="col-md-2 sidebar bg-white shadow-sm pt-4">
      <ul className="nav flex-column text-center">
        {menus[props.menuType]?.map((menu, index) => (
          <li className={`nav-item mb-3" ${String(index) === activeType ? "active" : ""}`} key={index}>
            <Link className="nav-link" to={menu.to}>
              {menu.label}
            </Link>
          </li>
        ))}
      </ul>
    </nav>
  );
};

export default Sidebar;
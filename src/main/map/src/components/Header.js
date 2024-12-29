import React from "react";
import { Link } from "react-router-dom";

const Header = (props) => {
  return (
    <header>
      <nav className="navbar navbar-expand-lg navbar-light bg-white shadow-sm text-center">
        <div className="row container-fluid">
          <Link className="d-inline col-md-2 navbar-brand fw-bold my-2" to="/home">
            <img src="/img/logo.png" alt="전월세 평균가 아이콘" className="d-inline align-middle logo" />
            <span className="align-middle">자취플랜</span>
          </Link>

          <div className="col-md-7 collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav ms-3">
              <li className="nav-item me-4">
                <img
                  src={props.menu === "home" ? "/img/home_active.png" : "/img/home.png"}
                  alt="전월세 평균가 아이콘"
                  className="d-inline nav-icon align-middle"
                />
                <Link
                  className={`d-inline nav-link align-middle ${props.menu === "home" ? "active" : ""}`}
                  to="/home"
                >
                  전월세 평균가
                </Link>
              </li>
              <li className="nav-item me-4">
                <img
                  src={props.menu === "community" ? "/img/community_active.png" : "/img/community.png"}
                  alt="커뮤니티 아이콘"
                  className="d-inline nav-icon align-middle"
                />
                <Link
                  className={`d-inline nav-link align-middle ${props.menu === "community" ? "active" : ""}`}
                  to="/board"
                >
                  커뮤니티
                </Link>
              </li>
              <li className="nav-item me-4">
                <img
                  src={props.menu === "mypage" ? "/img/mypage_active.png" : "/img/mypage.png"}
                  alt="마이페이지 아이콘"
                  className="d-inline nav-icon align-middle"
                />
                <Link
                  className={`d-inline nav-link align-middle ${props.menu === "mypage" ? "active" : ""}`}
                  to="/mypage"
                >
                  마이페이지
                </Link>
              </li>
            </ul>
          </div>

          <div className="col-md-3 text-end">
            {props.isAuthenticated ? (
              <>
                <span className="me-3 text-muted align-middle">{props.nickname}</span>
                <Link className="btn btn-outline-secondary btn-sm align-middle" to="/users/logout">
                  로그아웃
                </Link>
              </>
            ) : (
              <Link className="btn btn-outline-secondary btn-sm align-middle" to="/users/login">
                로그인
              </Link>
            )}
          </div>
        </div>
      </nav>
    </header>
  );
};

export default Header;

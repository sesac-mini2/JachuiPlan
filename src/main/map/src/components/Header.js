import React from "react";

const Header = (props) => {
  return (
    <header>
      <nav className="navbar navbar-expand-sm navbar-light bg-white shadow-sm text-center">
        <div className="row container-fluid">
          <a className="d-inline col-sm-2 navbar-brand fw-bold my-2" href="http://localhost:3000">
            <img src="/img/logo.png" alt="전월세 평균가 아이콘" className="d-inline align-middle me-2 logo" />
            <span className="align-middle">자취플랜</span>
          </a>

          <div className="col-sm-7 collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item me-3">
                <img
                  src={props.menu === "home" ? "/img/home_active.png" : "/img/home.png"}
                  alt="전월세 평균가 아이콘"
                  className="d-inline nav-icon align-middle"
                />
                <a
                  className={`d-inline nav-link align-middle ${props.menu === "home" ? "active" : ""}`}
                  href="http://localhost:3000"
                >
                  전월세 평균가
                </a>
              </li>
              <li className="nav-item me-3">
                <img
                  src={props.menu === "community" ? "/img/community_active.png" : "/img/community.png"}
                  alt="커뮤니티 아이콘"
                  className="d-inline nav-icon align-middle"
                />
                <a
                  className={`d-inline nav-link align-middle ${props.menu === "community" ? "active" : ""}`}
                  href="http://localhost/board/infolist"
                >
                  커뮤니티
                </a>
              </li>
              <li className="nav-item me-3">
                <img
                  src={props.menu === "mypage" ? "/img/mypage_active.png" : "/img/mypage.png"}
                  alt="마이페이지 아이콘"
                  className="d-inline nav-icon align-middle"
                />
                <a
                  className={`d-inline nav-link align-middle ${props.menu === "mypage" ? "active" : ""}`}
                  href="http://localhost/users/mypage"
                >
                  마이페이지
                </a>
              </li>
            </ul>
          </div>

          <div className="col-sm-3 text-end">

            {props.isAuthenticated ? (
              <>
                <span className="me-3 text-muted align-middle">{props.nickname}</span>
                <a className="btn btn-outline-secondary btn-sm align-middle" href="http://localhost/users/logout">
                  로그아웃
                </a>
              </>
            ) : (
              <>
                <a className="btn btn-outline-secondary btn-sm align-middle me-3" href="http://localhost/users/login">
                  로그인
                </a>
                <a className="btn btn-outline-secondary btn-sm align-middle" href="http://localhost/users/signup">
                  회원가입
                </a>
              </>
            )}
          </div>
        </div>
      </nav>
    </header>
  );
};

export default Header;

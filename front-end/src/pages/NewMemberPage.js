import React from "react";
import styles from '../styles/pages/NewMemberPage.module.css';
import Header from "../components/Header";
import {Link} from "react-router-dom";

function WelcomePage(props) {
    return (
        <div>
            <Header/>
            <div className={styles.body}>
                <h2>축하합니다.</h2>
                <p>회원가입이 완료되었습니다.</p>
                <Link to="/member/auth"><div>로그인하러 가기</div></Link>
            </div>
        </div>
    );
}

export default WelcomePage;

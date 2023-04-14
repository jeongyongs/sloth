import React from "react";
import styles from '../styles/pages/NotFoundPage.module.css';
import Header from "../components/Header";
import {Link} from "react-router-dom";

function NotFoundPage(props) {
    return (
        <div>
            <Header/>
            <div className={styles.body}>
                <h2>죄송합니다.</h2>
                <p>페이지를 찾을 수 없습니다.</p>
                <Link to="/"><div>메인으로 가기</div></Link>
            </div>
        </div>
    );
}

export default NotFoundPage;

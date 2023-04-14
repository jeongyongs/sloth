import React from "react";
import styles from '../styles/components/WelcomeButton.module.css';
import {Link} from "react-router-dom";

function WelcomeButton(props) {
    return (
        <Link to="/member/auth">
            <div className={styles.button}>
                <p>무료로 시작하기</p>
            </div>
        </Link>
    );
}

export default WelcomeButton;

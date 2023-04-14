import React from "react";
import styles from '../styles/components/WelcomeText.module.css';

function WelcomeText(props) {
    return (
        <div className={styles.welcome}>
            <h2>업무 인수인계.</h2>
            <h3>단 한 곳에서.</h3>
            <p className={styles.p1}>인수인계를 간편하고 빠르게 할 수 있는 서비스,</p>
            <p className={styles.p2}>지금 바로 슬로스에서 시작해보세요.</p>
        </div>
    );
}

export default WelcomeText;

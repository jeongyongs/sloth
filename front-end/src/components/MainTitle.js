import React from "react";
import styles from '../styles/components/MainTitle.module.css';

function MainTitle(props) {
    return (
        <div className={styles.mainTitle}>
            <h2>{props.title}</h2>
            <div>
                <p>{props.name} 님</p>
                <div className={styles.profile}></div>
            </div>
        </div>
    );
}

export default MainTitle;

import React from "react";
import styles from '../styles/pages/WelcomePage.module.css';
import Header from "../components/Header";
import WelcomeText from "../components/WelcomeText";
import WelcomeButton from "../components/WelcomeButton";

function WelcomePage(props) {
    return (
        <div>
            <Header/>
            <div className={styles.body}>
                <WelcomeText/>
                <WelcomeButton/>
            </div>
        </div>
    );
}

export default WelcomePage;

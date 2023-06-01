import React from "react";
import styles from '../styles/components/AppMenu.module.css';
import MenuButton from "./MenuButton";
import {Link} from "react-router-dom";

function AppMenu(props) {
    return (
        <div className={styles.menu}>
            <div className={styles.logo}>
                <img src="/header_logo.png" alt="logo"/>
                <h1>Sloth</h1>
            </div>
            <div className={styles.buttons1}>
                <MenuButton link="/app/dashboard" icon="Team_Dashboard" title="대시보드"/>
                <MenuButton link="/app/team" icon="Groups" title="팀 관리"/>
            </div>
            <div className={styles.buttons2}>
                <Link to="/app/dashboard"><p className={styles.p}>대시보드</p></Link>
                <Link to="/app/team"><p className={styles.p}>팀 관리</p></Link>
            </div>
        </div>
    );
}

export default AppMenu;

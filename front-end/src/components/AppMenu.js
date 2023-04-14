import React from "react";
import styles from '../styles/components/AppMenu.module.css';
import MenuButton from "./MenuButton";

function AppMenu(props) {
    return (
        <div className={styles.menu}>
            <div className={styles.logo}>
                <img src="/header_logo.png" alt="logo"/>
                <h1>Sloth</h1>
            </div>
            <MenuButton link="/app/dashboard" icon="Team_Dashboard" title="대시보드"/>
            <MenuButton link="/app/team" icon="Groups" title="팀 관리"/>
        </div>
    );
}

export default AppMenu;

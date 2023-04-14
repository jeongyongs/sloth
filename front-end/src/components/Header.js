import React from "react";
import styles from '../styles/components/Header.module.css';
import {Link} from "react-router-dom";

function Header(props) {
    return (
        <header className={styles.header}>
            <Link to="/">
                <div>
                    <img src="/header_logo.png" alt="logo"/>
                    <h1>Sloth</h1>
                </div>
            </Link>
        </header>
    );
}

export default Header;

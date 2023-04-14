import React from "react";
import styles from '../styles/components/MenuButton.module.css';
import {Link} from "react-router-dom";

function MenuButton(props) {
    return (
        <Link to={props.link}>
            <div className={styles.button}>
                <span className="material-symbols-outlined">{props.icon}</span>
                <p>{props.title}</p>
            </div>
        </Link>
    );
}

export default MenuButton;

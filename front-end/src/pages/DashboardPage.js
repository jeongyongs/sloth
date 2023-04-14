import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import AppMenu from "../components/AppMenu";
import styles from '../styles/pages/DashboardPage.module.css';
import MainTitle from "../components/MainTitle";

function DashboardPage(props) {

    const [data, setData] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("/api/member", {
            headers: {
                Authorization: `bearer ${props.token}`
            }
        })
            .then(response => {
                setData(response.data.data);
            })
            .catch(e => {
                navigate("/member/auth");
            });
    }, []);

    return (
        <div className={styles.body}>
            <AppMenu/>
            <div className={styles.main}>
                <MainTitle name={data.name}/>
            </div>
        </div>
    );
}

export default DashboardPage;

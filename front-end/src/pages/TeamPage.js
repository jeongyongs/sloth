import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import AppMenu from "../components/AppMenu";
import styles from '../styles/pages/TeamPage.module.css';
import MainTitle from "../components/MainTitle";
import Window from "../components/Window";

function DashboardPage(props) {

    const [userInfo, setUserInfo] = useState({});
    const [teams, setTeams] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {

        axios.get("/api/member", {
            headers: {
                Authorization: `bearer ${props.token}`
            }
        })
            .then(response => {
                setUserInfo(response.data.data);
            })
            .catch(e => {
                navigate("/member/auth");
            });
    }, []);

    useEffect(() => {

        axios.get("/api/team", {
            headers: {
                Authorization: `bearer ${props.token}`
            }
        })
            .then(response => {
                if (response.data.data !== null) {
                    setTeams(response.data.data);
                }
            })
            .catch(e => {
                navigate("/member/auth");
            });
    }, []);

    return (
        <div className={styles.page}>
            <AppMenu/>
            <div className={styles.body}>
                <MainTitle title="팀 관리" name={userInfo.name}/>
                <div className={styles.main}>
                    <Window listType="teams" list={teams} width="1" height="full" title="팀 목록"/>
                </div>
            </div>
        </div>
    );
}

export default DashboardPage;

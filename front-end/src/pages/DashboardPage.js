import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import AppMenu from "../components/AppMenu";
import styles from '../styles/pages/DashboardPage.module.css';
import MainTitle from "../components/MainTitle";
import Window from "../components/Window";

function DashboardPage(props) {

    const [data, setData] = useState({});
    const [userInfo, setUserInfo] = useState({});
    const [invites, setInvites] = useState([]);
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

        axios.get("/api/team/invite", {
            headers: {
                Authorization: `bearer ${props.token}`
            }
        })
            .then(response => {
                if (response.data.data !== null) {
                    setInvites(response.data.data);
                } else {
                    setInvites([]);
                }
            })
            .catch(e => {
                navigate("/member/auth");
            });
    }, [data]);

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
    }, [data]);

    return (
        <div className={styles.page}>
            <AppMenu/>
            <div className={styles.body}>
                <MainTitle title="대시보드" name={userInfo.name}/>
                <div className={styles.main}>
                    <Window setData={setData} token={props.token} listType="invites" list={invites} width="3" title="최근 소식"/>
                    <Window listType="teams" list={teams} width="3" title="소속된 팀"/>
                    <Window list={[]} width="3" title="업무 목록"/>
                </div>
            </div>
        </div>
    );
}

export default DashboardPage;

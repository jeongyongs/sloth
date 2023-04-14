import React, {useEffect} from "react";
import styles from '../styles/pages/AuthPage.module.css';
import Header from "../components/Header";
import AuthForm from "../components/AuthForm";
import {useNavigate} from "react-router-dom";

function AuthPage(props) {

    const navigate = useNavigate();

    useEffect(() => {
        if (props.token !== "") {
            navigate("/app/dashboard")
        }
    }, []);

    return (
        <div>
            <Header/>
            <div className={styles.body}>
                <div className={styles.form}>
                    <h2>반갑습니다.</h2>
                    <p>서비스 이용을 위해 로그인을 해주세요.</p>
                    <AuthForm token={props.token} setToken={props.setToken}/>
                </div>
            </div>
        </div>
    );
}

export default AuthPage;

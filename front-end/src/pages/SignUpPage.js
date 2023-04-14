import React from "react";
import styles from '../styles/pages/SignUpPage.module.css';
import Header from "../components/Header";
import SignUpForm from "../components/SignUpForm";

function SignUpPage(props) {
    return (
        <div>
            <Header/>
            <div className={styles.body}>
                <div className={styles.form}>
                    <h2>회원가입</h2>
                    <p>아래의 양식을 입력해주세요.</p>
                    <SignUpForm/>
                </div>
            </div>
        </div>
    );
}

export default SignUpPage;

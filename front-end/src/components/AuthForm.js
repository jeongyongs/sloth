import React, {useEffect, useState} from "react";
import styles from '../styles/components/AuthForm.module.css';
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";

function AuthForm(props) {

    const [values, setValues] = useState({
        username: "",
        password: ""
    });
    const navigate = useNavigate();
    const [errorUsername, setErrorUsername] = useState("");
    const [errorPassword, setErrorPassword] = useState("");
    const [usernameStyle, setUsernameStyle] = useState();
    const [passwordStyle, setPasswordStyle] = useState();
    const [isClickable, setClickable] = useState(true);

    useEffect(() => {
        if (values.username !== "") {
            setErrorUsername("");
        }
        if (values.password !== "") {
            setErrorPassword("");
        }
    }, [values]);

    function changeHandler(e) {

        setValues({
            ...values,
            [e.target.name]: e.target.value
        })
    }

    function authClickHandler() {

        if (values.username === "" && values.password === "") {
            setErrorUsername("아이디를 입력해주세요.");
            setErrorPassword("비밀번호를 입력해주세요.");
            setUsernameStyle(styles.error);
            setPasswordStyle(styles.error);
            setTimeout(() => {
                setUsernameStyle(undefined);
                setPasswordStyle(undefined);
            }, 500)
            return;
        }
        if (values.username === "" && values.password !== "") {
            setErrorUsername("아이디를 입력해주세요.");
            setErrorPassword("");
            setUsernameStyle(styles.error);
            setTimeout(() => {
                setUsernameStyle(undefined);
            }, 500)
            return;
        }
        if (values.username !== "" && values.password === "") {
            setErrorUsername("");
            setErrorPassword("비밀번호를 입력해주세요.");
            setPasswordStyle(styles.error);
            setTimeout(() => {
                setPasswordStyle(undefined);
            }, 500)
            return;
        }
        setErrorUsername("");
        setErrorPassword("");

        if (isClickable) {

            setClickable(false);

            axios.post(`/api/auth?username=${values.username}&password=${values.password}`)
                .then(response => {
                    props.setToken(response.data.token);
                    navigate('/app/dashboard');
                })
                .catch(e => {
                    setErrorPassword("아이디 또는 비밀번호가 잘못되었습니다.");
                    setTimeout(() => {
                        setClickable(true);
                    }, 1000);
                });
        }
    }

    return (
        <div>
            <div className={styles.form}>
                <input className={`${usernameStyle}`} placeholder="아이디" type="text" name="username"
                       value={values.username}
                       onChange={changeHandler}/>
                <p>{errorUsername}</p>
                <input className={`${passwordStyle}`} placeholder="비밀번호" type="password" name="password"
                       value={values.password} onChange={changeHandler}/>
                <p>{errorPassword}</p>
                <div onClick={authClickHandler}>로그인</div>
            </div>
            <div className={styles.etc}>
                <p>아직 회원이 아니신가요?</p>
                <Link to="/member/create"><p id="signup">회원가입</p></Link>
            </div>
        </div>
    );
}

export default AuthForm;

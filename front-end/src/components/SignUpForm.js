import React, {useEffect, useState} from "react";
import styles from '../styles/components/SignUpForm.module.css';
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";

function SignUpForm(props) {

    const [values, setValues] = useState({
        username: "",
        password: "",
        name: "",
        email: "",
        phone: ""
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

            axios.post(`/api/member?username=${values.username}&password=${values.password}&name=${values.name}&email=${values.email}&phone=${values.phone}`)
                .then(response => {
                    navigate('/member/complete');
                })
                .catch(e => {
                    setErrorUsername("해당 아이디는 사용할 수 없습니다.");
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

                <input className="non" placeholder="이 름" type="text" name="name"
                       value={values.name} onChange={changeHandler}/>
                <input className="non" placeholder="이메일" type="email" name="email"
                       value={values.email} onChange={changeHandler}/>
                <input className="non" placeholder="연락처" type="tel" name="phone"
                       value={values.phone} onChange={changeHandler}/>

                <div onClick={authClickHandler}>가입하기</div>
            </div>
            <div className={styles.etc}>
                <p>이미 회원이신가요?</p>
                <Link to="/member/auth"><p id="auth">로그인</p></Link>
            </div>
        </div>
    );
}

export default SignUpForm;

import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {BLUE, BLUE_ACTIVE, GRAY, LIGHT_GRAY, MAIN_BACKGROUND, WHITE} from "../constants";
import {useNavigate} from "react-router-dom";

const Component = styled.div`
  background-color: ${MAIN_BACKGROUND};
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;

  > div.container {
    box-sizing: border-box;
    background-color: ${WHITE};
    box-shadow: rgba(0, 0, 0, 0.1) 0 0 10px 0;
    width: 500px;
    padding: 20px;
    margin: 10px;

    > h1 {
      text-align: center;
      color: ${BLUE};
    }

    > p {
      text-align: center;
      color: ${GRAY};

      &.signup {
        color: ${BLUE};
        cursor: pointer;
        width: 100px;
        margin-left: auto;
        margin-right: auto;

        &:active {
          border-radius: 100px;
          background-color: ${MAIN_BACKGROUND};
        }
      }
    }

    > div.form {
      padding: 20px 50px;

      > input {
        box-sizing: border-box;
        width: 100%;
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid ${LIGHT_GRAY};
        border-radius: 10px;
        text-align: center;

        &:focus {
          border: 1px solid ${BLUE};
          outline: 2px solid ${BLUE};
        }
      }

      > div.submit {
        display: flex;
        justify-content: center;
        align-items: center;
        color: ${WHITE};
        text-align: center;
        background-color: ${props => props.clickable ? BLUE : LIGHT_GRAY};
        padding: 10px;
        border-radius: 10px;
        cursor: ${props => props.clickable ? "pointer" : "default"};

        &:active {
          background-color: ${props => props.clickable ? BLUE_ACTIVE : ""};
          color: ${props => props.clickable ? LIGHT_GRAY : ""};

          > svg {
            fill: ${props => props.clickable ? LIGHT_GRAY : ""};
          }
        }

        > svg {
          fill: ${WHITE};
          margin-right: 5px;
        }
      }
    }
  }
`

function LoginPage(props) {

    const navigator = useNavigate();
    const [login, setLogin] = useState({username: "", password: ""});
    const [isClickable, setClickable] = useState(false);

    useEffect(() => {
        if (login.username.length <= 0) {
            setClickable(false);
            return;
        }
        if (login.password.length <= 0) {
            setClickable(false);
            return;
        }
        setClickable(true);
    }, [login]);

    function changeHandler(e) {
        setLogin({
            ...login,
            [e.target.id]: e.target.value
        })
    }

    return (
        <Component clickable={isClickable}>
            <div className="container">
                <h1>SLOTH</h1>
                <p>서비스 이용을 위해 로그인해주세요.</p>
                <div className="form">
                    <input type="text" placeholder="아이디" value={login.username} id="username"
                           onChange={changeHandler}/>
                    <input type="password" placeholder="비밀번호" value={login.password} id="password"
                           onChange={changeHandler}/>
                    <div className="submit">
                        <svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                            <path
                                d="M489-120v-60h291v-600H489v-60h291q24 0 42 18t18 42v600q0 24-18 42t-42 18H489Zm-78-185-43-43 102-102H120v-60h348L366-612l43-43 176 176-174 174Z"/>
                        </svg>
                        로그인
                    </div>
                </div>
                <p>아직 회원이 아니신가요?</p>
                <p className="signup" onClick={() => {
                    navigator("/signup")
                }}>가입하기</p>
            </div>
        </Component>
    );
}

export default LoginPage;

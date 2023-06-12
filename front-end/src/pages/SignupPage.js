import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {BLUE, BLUE_ACTIVE, FOOTER_BORDER, GRAY, LIGHT_GRAY, MAIN_BACKGROUND, WHITE} from "../constants";
import {useNavigate} from "react-router-dom";
import Modal from "../components/Modal";
import Terms from "../components/Terms";
import axios from "axios";

const Component = styled.div`
  background-color: ${MAIN_BACKGROUND};
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;

  > div.container {
    display: flex;
    box-sizing: border-box;
    background-color: ${WHITE};
    box-shadow: rgba(0, 0, 0, 0.1) 0 0 10px 0;
    width: 1000px;
    margin: 10px;
    padding: 20px;

    > div.panel {
      box-sizing: border-box;
      width: 50%;
      padding-left: 20px;

      &.left {
        padding-left: 0;
        padding-right: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        border-right: 1px solid ${FOOTER_BORDER};

        > div.content {
          padding: 50px;

          > div.icon {
            display: flex;
            justify-content: center;

            > svg {
              fill: ${LIGHT_GRAY};
            }
          }

          > h2 {
            color: ${GRAY};
            text-align: center;
          }

          > p {
            margin: 0 0 10px;
            text-align: center;
            color: ${GRAY};

            &.login {
              color: ${BLUE};
              cursor: pointer;
              width: 140px;
              margin-left: auto;
              margin-right: auto;

              &:active {
                border-radius: 100px;
                background-color: ${MAIN_BACKGROUND};
              }
            }
          }
        }
      }

      > h1 {
        text-align: center;
        color: ${BLUE};
      }

      > p {
        text-align: center;
        color: ${GRAY};
      }

      > div.form {
        padding: 20px 50px;

        > p {
          margin: 0;
          text-align: center;
          font-size: 14px;
          color: red;
        }

        > div.confirm-container {
          display: flex;
          justify-content: center;
          margin-top: 10px;
          margin-bottom: 10px;

          > label {
            color: ${GRAY};
            font-size: 14px;
          }

          > p {
            padding: 0 5px;
            margin: 0;
            font-size: 14px;
            color: ${BLUE};
            cursor: pointer;

            &:active {
              border-radius: 100px;
              background-color: ${MAIN_BACKGROUND};
            }
          }
        }

        > label {
          color: ${GRAY};
          font-size: 12px;
          margin-left: 10px;
        }

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
          margin-bottom: 10px;

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
  }

  > div.signup-done {
    display: ${props => props.done ? "flex" : "none"};
    justify-content: center;
    align-items: center;
    background-color: rgba(0, 0, 0, 0.5);
    width: 100vw;
    height: 100vh;
    position: fixed;

    > div.container {
      background-color: ${WHITE};
      box-shadow: rgba(0, 0, 0, 0.1) 0 0 10px 0;
      padding: 20px;
      display: flex;
      justify-content: center;
      align-items: center;

      > div.content {
        padding: 50px;

        > div.icon {
          display: flex;
          justify-content: center;

          > svg {
            fill: ${LIGHT_GRAY};
          }
        }

        > h2 {
          color: ${GRAY};
          text-align: center;
        }

        > p {
          margin: 0 0 10px;
          text-align: center;
          color: ${GRAY};

          &.login {
            color: ${BLUE};
            cursor: pointer;
            width: 140px;
            margin-left: auto;
            margin-right: auto;

            &:active {
              border-radius: 100px;
              background-color: ${MAIN_BACKGROUND};
            }
          }
        }
      }
    }
  }
`

function SignupPage(props) {

    const navigator = useNavigate();
    const [data, setData] = useState({
        username: "",
        password: "",
        passwordConfirm: "",
        name: "",
        termsConfirm: false
    });
    const [termsVisible, setTermsVisible] = useState(false);
    const [isClickable, setClickable] = useState(false);
    const [warningUsername, setWarningUsername] = useState("");
    const [warningPasswordConfirm, setWarningPasswordConfirm] = useState("");
    const [signupDone, setSignupDone] = useState(false);

    useEffect(() => {
        if (props.token.length > 0) {
            navigator("/teams/me/dashboard");
        }
    }, []);

    useEffect(() => {
        if (data.username.length > 0 && // 아이디 조건
            data.password.length > 0 && // 비밀번호 조건
            data.passwordConfirm.length > 0 &&  // 비밀번호 확인 조건
            data.password === data.passwordConfirm &&   // 비밀번호 확인
            data.name.length > 0 && // 이름 조건
            data.termsConfirm) {    // 약관 동의 여부
            setClickable(true);
            return;
        }
        setClickable(false);
    }, [data]);

    useEffect(() => {   // 사용할 수 없는 아이디
        setWarningUsername("");
    }, [data.username]);

    useEffect(() => {   // 패스워드 확인
        if (data.password !== data.passwordConfirm) {
            setClickable(false);
            setWarningPasswordConfirm("비밀번호가 일치하지 않습니다");
            return;
        }
        setWarningPasswordConfirm("");
    }, [data.password, data.passwordConfirm]);

    function changeHandler(e) {
        setData({
            ...data,
            [e.target.id]: e.target.value
        });
    }

    function checkHandler(e) {
        setData({
            ...data,
            [e.target.id]: e.target.checked
        });
    }

    function request() {
        if (isClickable) { // 클릭 가능 여부 확인
            setClickable(false);
            axios.post("/api/signup", {
                username: data.username,
                password: data.password,
                name: data.name
            }).then(() => {
                setSignupDone(true);
            }).catch(() => {
                setWarningUsername("사용할 수 없는 아이디입니다");
            });
        }
    }

    return (
        <Component clickable={isClickable} done={signupDone}>
            <div className="container">
                <div className="panel left">
                    <div className="content">
                        <div className="icon">
                            <svg xmlns="http://www.w3.org/2000/svg" height="40" viewBox="0 -960 960 960" width="40">
                                <path
                                    d="M48-672q0-100 70-170t170-70v72q-70 0-119 49t-49 119H48Zm153 448q-82-82-82.5-195.5T199-614l77-78 17 17q22 22 22 51t-22 51l-8 9q-11 11-11 25.5t11 25.5l8 8q21 21 21 51t-21 51l-34 34 34 34 34-34q35-35 35-85t-35-85q14-14 22.5-31.5T361-607l178-178q11-11 25.5-11t25.5 11q11 11 11 25.5T590-734L412-556l34 34 246-246q11-11 25.5-11t25.5 11q11 11 11 25.5T743-717L497-471l34 34 212-212q11-11 25.5-11t25.5 11q11 11 11 25.5T794-598L582-386l34 34 145-145q11-11 25.5-11t25.5 11q11 11 11 25.5T812-446L589-224q-81 81-194 81t-194-81ZM672-48v-72q70 0 119-49t49-119h72q0 100-70 170T672-48Z"/>
                            </svg>
                        </div>
                        <h2>환영합니다!</h2>
                        <p>원활한 인수인계를 위한 최적의 웹 앱 서비스</p>
                        <br/>
                        <p>이미 회원이신가요?</p>
                        <p className="login" onClick={() => {
                            navigator("/login")
                        }}>로그인하러 가기</p>
                    </div>
                </div>
                <div className="panel">
                    <h1>SLOTH</h1>
                    <p>서비스 이용을 위해 로그인해주세요.</p>
                    <div className="form">
                        <label htmlFor="username">아이디</label>
                        <input value={data.username} onChange={changeHandler} id="username" type="text"
                               placeholder="로그인 시 사용할 아이디를 입력하세요"/>
                        <p>{warningUsername}</p>
                        <label htmlFor="password">비밀번호</label>
                        <input value={data.password} onChange={changeHandler} id="password" type="password"
                               placeholder="비밀번호를 입력하세요"/>
                        <label htmlFor="password">비밀번호 확인</label>
                        <input value={data.passwordConfirm} onChange={changeHandler} id="passwordConfirm"
                               type="password" placeholder="비밀번호를 다시 한번 입력하세요"/>
                        <p>{warningPasswordConfirm}</p>
                        <label htmlFor="name">이 름</label>
                        <input value={data.name} onChange={changeHandler} id="name" type="text"
                               placeholder="다른 사람에게 표시될 이름을 입력하세요"/>
                        <div className="confirm-container">
                            <input checked={data.termsConfirm} onChange={checkHandler} id="termsConfirm"
                                   type="checkbox"/>
                            <label htmlFor="confirm" onClick={() => {
                                if (data.termsConfirm) {
                                    setData({
                                        ...data,
                                        termsConfirm: false
                                    })
                                    return;
                                }
                                setData({
                                    ...data,
                                    termsConfirm: true
                                })
                            }}>약관에 동의합니다.</label>
                            <p onClick={() => {
                                setTermsVisible(true);
                            }}>서비스 이용약관</p>
                        </div>
                        <div className="submit" onClick={request}>
                            <svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                                <path
                                    d="M708-432v-84h-84v-72h84v-84h72v84h84v72h-84v84h-72Zm-324-48q-60 0-102-42t-42-102q0-60 42-102t102-42q60 0 102 42t42 102q0 60-42 102t-102 42ZM96-192v-92q0-25.78 12.5-47.39T143-366q55-32 116-49t125-17q64 0 125 17t116 49q22 13 34.5 34.61T672-284v92H96Z"/>
                            </svg>
                            가입하기
                        </div>
                    </div>
                </div>
            </div>
            <Modal size={["500px", "500px"]} title="서비스 이용약관" visible={termsVisible} set={setTermsVisible}>
                <Terms/>
            </Modal>
            <div className="signup-done">
                <div className="container">
                    <div className="content">
                        <div className="icon">
                            <svg xmlns="http://www.w3.org/2000/svg" height="48" viewBox="0 -960 960 960" width="48">
                                <path
                                    d="M140-101q-18 7-32-7t-7-32l133-371q11-32 43.5-39t55.5 16l202 200q24 23 17 55.5T513-235L140-101Zm400-361q-7-7-7-17t7-17l223-223q32-32 81-32.5t81 31.5l1 1q7 7 7 16t-8 17q-7 7-17 7t-17-7l-2-2q-19-19-44-19.5T800-688L573-461q-7 7-16 7t-17-8ZM381-617q-7-7-7-17t7-17l13-13q23-23 21.5-52.5T394-766l-12-12q-7-7-7-16t8-17q7-7 17-7t17 7l9 9q35 35 34.5 87.5T425-627l-11 11q-7 7-16 7t-17-8Zm81 77q-7-7-7-17t7-17l135-135q19-19 18.5-48.5T596-806l-45-45q-7-7-7-16t8-17q7-7 17-7t17 7l46 46q31 32 32 80.5T633-677L495-539q-7 7-16 7t-17-8Zm158 159q-7-7-7-17t7-17l30-30q35-35 84-36t84 34l35 35q7 7 7 16t-8 17q-7 7-17 7t-17-7l-35-35q-23-23-48-23t-48 23l-34 34q-7 7-16 7t-17-8Z"/>
                            </svg>
                        </div>
                        <h2>축하합니다!</h2>
                        <p>가입이 완료되었습니다.</p>
                        <p className="login" onClick={() => {
                            navigator("/login")
                        }}>로그인하러 가기</p>
                    </div>
                </div>
            </div>
        </Component>
    );
}

export default SignupPage;

import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {BLUE, BLUE_ACTIVE, GRAY, LIGHT_GRAY, MAIN_BACKGROUND, WHITE} from "../constants";
import axios from "axios";

const Component = styled.div`
  padding: 20px 50px;

  > p {
    margin: 0 0 10px;
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
`

function NewTeamForm(props) {

    const [warningTeam, setWarningTeam] = useState("");
    const [isClickable, setClickable] = useState(false);
    const [data, setData] = useState("");

    useEffect(() => {
        setWarningTeam("");
        if (data.length > 0) {
            setClickable(true);
            return;
        }
        setClickable(false);
    }, [data]);

    function changeHandler(e) {
        setData(e.target.value);
    }

    function refresh() {
        if (props.refresh) {
            props.setRefresh(false);
            return;
        }
        props.setRefresh(true);
    }

    function request() {
        if (isClickable) {
            setClickable(false);
            axios.post("/api/teams", {name: data}, {
                headers: {
                    "Authorization": `Bearer ${props.token}`
                }
            })
                .then(() => {
                    setData("");
                    props.set(false);
                    props.setMove(true);
                    refresh();
                }).catch(() => {
                setWarningTeam("사용할 수 없는 팀 이름입니다");
            });
        }
    }

    return (
        <Component clickable={isClickable}>
            <label htmlFor="name">팀 이름</label>
            <input value={data} onChange={changeHandler} id="name" type="text" placeholder="팀 이름을 입력하세요"/>
            <p>{warningTeam}</p>
            <div className="submit" onClick={request}>
                <svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                    <path
                        d="M450-483q26-30 40-66t14-75q0-38-14-74t-40-67q8-2 15-2.5t15-.5q60 0 102 42t42 102q0 60-42 102t-102 42q-8 0-15.5-.5T450-483Zm198 291v-92q0-41-19-76.5T576-421q68 16 130 44t62 93v92H648Zm132-240v-84h-84v-72h84v-84h72v84h84v72h-84v84h-72Zm-492-48q-60 0-102-42t-42-102q0-60 42-102t102-42q60 0 102 42t42 102q0 60-42 102t-102 42ZM0-192v-92q0-25 12.5-46.5T47-366q54-34 115.5-50T288-432q63 0 124 17t117 49q21 14 34 35.5t13 46.5v92H0Z"/>
                </svg>
                만들기
            </div>
        </Component>
    );
}

export default NewTeamForm;

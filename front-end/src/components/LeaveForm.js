import React, {useState} from "react";
import styled from "styled-components";
import {GRAY, LIGHT_GRAY, RED, RED_ACTIVE, WHITE} from "../constants";
import {useParams} from "react-router-dom";
import axios from "axios";

const Component = styled.div`
  padding: 20px;

  > p {
    text-align: center;
    margin: 0 0 20px;
  }

  > div.submit {
    cursor: ${props => props.clickable ? "pointer" : "default"};
    border-radius: 3px;
    padding: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: ${props => props.clickable ? RED : GRAY};
    color: ${WHITE};

    > svg {
      fill: ${WHITE};
    }

    &:active {
      background-color: ${props => props.clickable ? RED_ACTIVE : ""};
      color: ${props => props.clickable ? LIGHT_GRAY : ""};

      > svg {
        fill: ${props => props.clickable ? LIGHT_GRAY : ""};
      }
    }
  }
`

function LeaveForm(props) {

    const {teamId} = useParams();
    const [clickable, setClickable] = useState(true);

    function leave(e) {
        if (clickable) {
            setClickable(false);
            axios.delete(`/api/teams/${teamId}/leave`, {
                headers: {
                    "Authorization": `Bearer ${props.token}`
                }
            }).then(() => {
                props.set(false);
                setClickable(true);
                if (props.refresh) {
                    props.setRefresh(false);
                    return;
                }
                props.setRefresh(true);
            }).catch(() => {
                setClickable(true);
                if (props.refresh) {
                    props.setRefresh(false);
                    return;
                }
                props.setRefresh(true);
            });
        }
    }

    return (
        <Component clickable={clickable}>
            <p>정말로 {props.teamName} 팀에서 나가시겠습니까?</p>
            <div className="submit" onClick={leave}>
                <svg xmlns="http://www.w3.org/2000/svg" height="16" viewBox="0 -960 960 960"
                     width="16">
                    <path
                        d="M180-120q-24 0-42-18t-18-42v-600q0-24 18-42t42-18h291v60H180v600h291v60H180Zm486-185-43-43 102-102H375v-60h348L621-612l43-43 176 176-174 174Z"/>
                </svg>
                탈퇴하기
            </div>
        </Component>
    );
}

export default LeaveForm;

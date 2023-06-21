import React, {useState} from "react";
import styled from "styled-components";
import {BLUE, BLUE_ACTIVE, GRAY, LIGHT_GRAY, RED, RED_ACTIVE, SIDEBAR_BACKGROUND, WHITE} from "../constants";
import axios from "axios";
import {useParams} from "react-router-dom";

const Component = styled.div`
  padding: 20px 50px;

  > p {
    margin: 0 0 5px;
  }

  > div.search-bar {
    display: flex;
    margin-bottom: 10px;


    > input {
      box-sizing: border-box;
      width: 100%;
      padding: 10px;
      border: 1px solid ${LIGHT_GRAY};
      border-radius: 3px;
      text-align: center;
      margin-right: 10px;

      &:focus {
        border: 1px solid ${BLUE};
        outline: 2px solid ${BLUE};
      }
    }

    > div.search {
      cursor: ${props => props.clickable ? "pointer" : "default"};
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: ${props => props.clickable ? BLUE : GRAY};
      color: ${WHITE};
      padding: 0 30px;
      white-space: nowrap;
      border-radius: 3px;

      &:active {
        background-color: ${props => props.clickable ? BLUE_ACTIVE : ""};
        color: ${props => props.clickable ? LIGHT_GRAY : ""};
      }
    }
  }

  > div.result {
    > div.list {
      &.body {
        height: 360px;
        overflow-y: auto;
      }

      > div.data {
        display: flex;
        text-align: center;
        white-space: nowrap;
        border-bottom: 1px solid ${LIGHT_GRAY};
        padding: 10px 0;

        &.title {
          background-color: ${SIDEBAR_BACKGROUND};
          border-radius: 3px;
          color: ${WHITE};

          > div.manage {
            display: block;
          }
        }

        > div.id {
          width: 10%;
          overflow-x: hidden;
          text-overflow: ellipsis;
        }

        > div.username {
          width: 30%;
          overflow-x: hidden;
          text-overflow: ellipsis;
        }

        > div.name {
          width: 30%;
          overflow-x: hidden;
          text-overflow: ellipsis;
        }

        > div.invite {
          width: 30%;
          overflow-x: hidden;
          text-overflow: ellipsis;

          > div.button {
            display: flex;
            justify-content: center;

            > div.send {
              cursor: ${props => props.clickable ? "pointer" : "default"};
              background-color: ${props => props.clickable ? BLUE : GRAY};
              color: ${WHITE};
              padding: 0 10px;
              border-radius: 3px;

              &:active {
                background-color: ${props => props.clickable ? BLUE_ACTIVE : ""};
                color: ${props => props.clickable ? LIGHT_GRAY : ""};
              }
            }

            > div.cancel {
              cursor: ${props => props.clickable ? "pointer" : "default"};
              background-color: ${props => props.clickable ? RED : GRAY};
              color: ${WHITE};
              padding: 0 10px;
              border-radius: 3px;

              &:active {
                background-color: ${props => props.clickable ? RED_ACTIVE : ""};
                color: ${props => props.clickable ? LIGHT_GRAY : ""};
              }
            }
          }
        }
      }
    }
  }
`

function InviteForm(props) {

    const {teamId} = useParams();
    const [data, setData] = useState([]);
    const [clickable, setClickable] = useState(true);

    function search() {
        if (props.input.length < 1) {   // 입력값이 없을 경우 리턴
            return;
        }
        if (clickable) {
            setClickable(false);
            axios.get(`/api/users/teams/${teamId}`, {
                params: {
                    "search": props.input
                },
                headers: {
                    "Authorization": `Bearer ${props.token}`
                }
            }).then(response => {
                setData(response.data)
                setClickable(true);
            }).catch(() => {
                setClickable(true);
            });
        }
    }

    function changeHandler(e) {
        props.setInput(e.target.value);
    }

    function send(username) {
        if (clickable) {
            setClickable(false);
            axios.post(`/api/invite`, {
                "teamId": teamId,
                "username": username
            }, {
                headers: {
                    "Authorization": `Bearer ${props.token}`
                }
            }).then(() => {
                search();
            }).catch(() => {
                setClickable(true);
            });
        }
    }

    function cancel(username) {
        if (clickable) {
            setClickable(false);
            axios.delete(`/api/invite`, {
                params: {
                    "teamId": teamId,
                    "username": username
                },
                headers: {
                    "Authorization": `Bearer ${props.token}`
                }
            }).then(() => {
                search();
            }).catch(() => {
                search();
                setClickable(true);
            });
        }
    }

    return (
        <Component clickable={clickable}>
            <div className="search-bar">
                <input value={props.input} onChange={changeHandler} type="search" placeholder="팀에 초대할 유저의 아이디를 입력하세요"/>
                <div className="search" onClick={search}>검 색</div>
            </div>
            <p>검색결과 : 총 {data.length} 명</p>
            <div className="result">
                <div className="list">
                    <div className="data title">
                        <div className="id">번 호</div>
                        <div className="username">아이디</div>
                        <div className="name">이 름</div>
                        <div className="invite">초 대</div>
                    </div>
                </div>
                <div className="list body">
                    {data.map((user, index) => (
                        <div className="data">
                            <div className="id">{index + 1}</div>
                            <div className="username">{user.username}</div>
                            <div className="name">{user.name}</div>
                            <div className="invite">
                                <div className="button">
                                    {user.invited ?
                                        (<div className="cancel" onClick={() => cancel(user.username)}>취 소</div>) :
                                        (<div className="send" onClick={() => send(user.username)}>초 대</div>)}
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </Component>
    );
}

export default InviteForm;

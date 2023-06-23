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

        > div.transferee {
          width: 30%;
          overflow-x: hidden;
          text-overflow: ellipsis;

          > div.button {
            display: flex;
            justify-content: center;

            > div.add {
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

            > div.delete {
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

function TransfereeForm(props) {

    const {teamId} = useParams();
    const [data, setData] = useState([]);
    const [clickable, setClickable] = useState(true);
    const [input, setInput] = useState("");

    function search() {
        if (input.length < 1) {   // 입력값이 없을 경우 리턴
            return;
        }
        if (clickable) {
            setClickable(false);
            axios.get(`/api/handovers/${props.select}/members`, {
                params: {
                    "search": input
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
        setInput(e.target.value);
    }

    function add(username) {
        if (clickable) {
            setClickable(false);
            axios.post(`/api/transferees`, {
                "handoverId": props.select,
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

    function remove(username) {
        if (clickable) {
            setClickable(false);
            axios.delete(`/api/transferees`, {
                params: {
                    "handoverId": props.select,
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
                <input value={input} onChange={changeHandler} type="search" placeholder="인수자 아이디를 입력하세요"/>
                <div className="search" onClick={search}>검 색</div>
            </div>
            <p>검색결과 : 총 {data.length} 명</p>
            <div className="result">
                <div className="list">
                    <div className="data title">
                        <div className="id">번 호</div>
                        <div className="username">아이디</div>
                        <div className="name">이 름</div>
                        <div className="transferee">인 계</div>
                    </div>
                </div>
                <div className="list body">
                    {data.map((user, index) => (
                        <div className="data">
                            <div className="id">{index + 1}</div>
                            <div className="username">{user.username}</div>
                            <div className="name">{user.name}</div>
                            <div className="transferee">
                                <div className="button">
                                    {user.transferee ?
                                        (<div className="delete" onClick={()=>{
                                            remove(user.username);
                                        }}>삭 제</div>) :
                                        (<div className="add" onClick={() => {
                                            add(user.username);
                                        }}>추 가</div>)}
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </Component>
    );
}

export default TransfereeForm;

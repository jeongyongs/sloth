import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {BLUE, BLUE_ACTIVE, GRAY, LIGHT_GRAY, RED, RED_ACTIVE, WHITE} from "../constants";
import axios from "axios";
import {useParams} from "react-router-dom";

const Component = styled.div`
  padding: 20px 40px;

  > h2 {
    margin: 0 0 10px;
  }

  > label {
    color: ${GRAY};
    font-size: 16px;
    margin-left: 10px;
  }

  > div.content {
    white-space: pre-line;
    margin-bottom: 20px;
    max-height: 300px;
    overflow-y: auto;
  }

  > input {
    box-sizing: border-box;
    width: 100%;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid ${LIGHT_GRAY};
    border-radius: 3px;
    font-size: 20px;

    &:focus {
      border: 1px solid ${BLUE};
      outline: 2px solid ${BLUE};
    }
  }

  > textarea {
    box-sizing: border-box;
    width: 100%;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid ${LIGHT_GRAY};
    border-radius: 3px;
    resize: none;
    font-size: 20px;
    max-height: 1000px;

    &:focus {
      border: 1px solid ${BLUE};
      outline: 2px solid ${BLUE};
    }
  }

  > div.container {
    display: flex;
    justify-content: space-between;

    > div.clear {
      cursor: pointer;
      background-color: ${LIGHT_GRAY};
      color: ${WHITE};
      padding: 10px 20px;
      border-radius: 3px;

      &:active {
        background-color: ${GRAY};
        color: ${LIGHT_GRAY};
      }
    }

    > div.submit {
      cursor: ${props => props.clickable ? "pointer" : "default"};
      background-color: ${props => props.clickable ? BLUE : GRAY};
      color: ${WHITE};
      padding: 10px 20px;
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
      padding: 10px 20px;
      border-radius: 3px;
      margin-bottom: 20px;

      &:active {
        background-color: ${props => props.clickable ? RED_ACTIVE : ""};
        color: ${props => props.clickable ? LIGHT_GRAY : ""};
      }
    }
  }

  > div.comment-container {
    border-top: 1px solid ${LIGHT_GRAY};
    margin-bottom: 10px;
    max-height: 150px;
    overflow-y: auto;

    > div.comment {
      border-bottom: 1px solid ${LIGHT_GRAY};
      padding: 10px 0 10px;

      > p {
        margin: 0;
      }

      > div.info-container {
        display: flex;
        justify-content: space-between;

        > p {
          color: ${LIGHT_GRAY};
          margin: 0;
        }
      }
    }
  }
`

function ViewHandoverForm(props) {

    const [clickable, setClickable] = useState(true);
    const [data, setData] = useState({});
    const [comment, setComment] = useState("");

    useEffect(() => {
        if (props.select !== 0) {
            axios.get(`/api/handovers/${props.select}`, {
                headers: {
                    "Authorization": `Bearer ${props.token}`
                }
            }).then(response => {
                setData(response.data);
            }).catch(() => {
            })
        }
    }, [props.select]);

    function changeHandler(e) {
        setComment(e.target.value);
    }

    function deleteHandover() {
        axios.delete(`/api/handovers/${props.select}`, {
            headers: {
                "Authorization": `Bearer ${props.token}`
            }
        }).then(() => {
            props.set(false);
            if (props.refresh) {
                props.setRefresh(false);
                return;
            }
            props.setRefresh(true);
        }).catch(() => {
        })
    }

    return (
        <Component clickable={clickable}>
            <label htmlFor="title">프로젝트/작업명</label>
            <h2>{data.title}</h2>
            <label htmlFor="content">내 용</label>
            <div className="content">{data.content}</div>
            <div className="container">
                <div className="delete" onClick={deleteHandover}>삭 제</div>
            </div>
            <label htmlFor="comment">코멘트</label>
            <div className="comment-container">
                <div className="comment">
                    <div className="info-container">
                        <p>이 름</p>
                        <p>2023-06-14</p>
                    </div>
                    <p>이 부분이 잘 이해가 안됩니다.</p>
                </div>
            </div>
            <input onChange={changeHandler} value={comment} type="text" id="comment"/>
            <div className="container">
                <div className="clear" onClick={() => {
                    setComment("");
                }}>지우기
                </div>
                <div className="submit">작 성</div>
            </div>
        </Component>
    );
}

export default ViewHandoverForm;

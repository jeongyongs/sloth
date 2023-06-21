import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {BLUE, BLUE_ACTIVE, GRAY, LIGHT_GRAY, WHITE} from "../constants";
import axios from "axios";
import {useParams} from "react-router-dom";

const Component = styled.div`
  padding: 20px 40px;

  > label {
    color: ${GRAY};
    font-size: 16px;
    margin-left: 10px;
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
  }
`

function NewHandoverForm(props) {

    const {teamId} = useParams();
    const [clickable, setClickable] = useState(false);
    const [data, setData] = useState({
        title: "",
        content: ""
    });

    function setTextareaHeight(e) {
        e.target.style.height = "auto";
        e.target.style.height = Math.min(e.target.scrollHeight + 2, 500) + "px";
    }

    useEffect(() => {
        if (data.title.length === 0) {
            setClickable(false);
            return;
        }
        if (data.content.length === 0) {
            setClickable(false);
            return;
        }
        setClickable(true);
    }, [data]);

    function changeHandler(e) {
        setData({
            ...data,
            [e.target.id]: e.target.value
        })
    }

    function textareaChangeHandler(e) {
        setTextareaHeight(e);
        changeHandler(e);
    }

    function submit() {
        if (clickable) {
            setClickable(false);
            axios.post(`/api/handovers`, {
                teamId: teamId,
                title: data.title,
                content: data.content
            }, {
                headers: {
                    "Authorization": `Bearer ${props.token}`
                }
            }).then(() => {
                setClickable(true);
                props.setNewVisible(false);
                setData({title: "", content: ""});
                if (props.refresh) {
                    props.setRefresh(false);
                    return;
                }
                props.setRefresh(true);
            }).catch(() => {
                setClickable(true);
            });
        }
    }

    return (
        <Component clickable={clickable}>
            <label htmlFor="title">프로젝트/작업명</label>
            <input value={data.title} type="text" id="title" onChange={changeHandler}/>
            <label htmlFor="content">내 용</label>
            <textarea value={data.content} id="content" rows="1" onChange={textareaChangeHandler}></textarea>
            <div className="container">
                <div className="clear" onClick={() => setData({title: "", content: ""})}>초기화</div>
                <div className="submit" onClick={submit}>등 록</div>
            </div>
        </Component>
    );
}

export default NewHandoverForm;

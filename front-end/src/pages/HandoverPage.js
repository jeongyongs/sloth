import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {BLUE, BLUE_ACTIVE, GRAY, LIGHT_GRAY, SIDEBAR_BACKGROUND, WHITE} from "../constants";
import Modal from "../components/Modal";
import NewHandoverForm from "../components/NewHandoverForm";
import axios from "axios";
import {useParams} from "react-router-dom";

const Component = styled.div`
  padding-top: 20px;

  > h2 {
    margin: 0 0 10px;
    color: ${GRAY};
  }

  > div.content { // 컨텐츠 박스
    background-color: ${WHITE};
    border-radius: 3px;
    box-shadow: rgba(0, 0, 0, 0.1) 0 0 10px 0;
    padding: 20px;
    margin-bottom: 20px;

    > div.title {
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;

      > p {
        color: ${GRAY};
        margin: 0 0 10px 0;
      }
    }

    > div.data { // 컬럼
      display: flex;
      text-align: center;
      white-space: nowrap;
      border-bottom: 1px solid ${LIGHT_GRAY};
      padding: 10px 0;

      &.column {
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

      > div.date {
        width: 15%;
        overflow-x: hidden;
        text-overflow: ellipsis;
      }

      > div.title {
        width: 40%;
        overflow-x: hidden;
        text-overflow: ellipsis;
      }

      > div.transferor {
        width: 10%;
        overflow-x: hidden;
        text-overflow: ellipsis;
      }

      > div.transferee {
        width: 10%;
        overflow-x: hidden;
        text-overflow: ellipsis;
      }

      > div.state {
        width: 10%;
        overflow-x: hidden;
        text-overflow: ellipsis;
      }
    }

    div.container {
      display: flex;

      > div.new {
        cursor: ${props => props.clickable ? "pointer" : "default"};
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 0 20px;
        border-radius: 3px;
        background-color: ${props => props.clickable ? BLUE : GRAY};
        color: ${WHITE};

        &:active {
          background-color: ${props => props.clickable ? BLUE_ACTIVE : ""};
          color: ${props => props.clickable ? LIGHT_GRAY : ""};
        }
      }
    }
  }
`

function HandoverPage(props) {

    const {teamId} = useParams();
    const [transfereeTotal, setTransfereeTotal] = useState(0);
    const [transferorTotal, setTransferorTotal] = useState(0);
    const [transferee, setTransferee] = useState([]);
    const [transferor, setTransferor] = useState([]);
    const [clickable, setClickable] = useState(true);
    const [refresh, setRefresh] = useState(false);
    const [newVisible, setNewVisible] = useState(false);

    useEffect(() => {
        axios.get(`/api/handovers`, {
            params: {
                "teamId": teamId
            },
            headers: {
                "Authorization": `Bearer ${props.token}`
            }
        }).then(response => {
            setTransferor(response.data);
            setTransferorTotal(response.data.length);
        }).catch(() => {
        });
    }, [refresh])

    useEffect(() => {
        axios.get(`/api/transferees`, {
            params: {
                "teamId": teamId
            },
            headers: {
                "Authorization": `Bearer ${props.token}`
            }
        }).then(response => {
            setTransferee(response.data);
            setTransfereeTotal(response.data.length);
        }).catch(() => {
        });
    }, [refresh])

    return (
        <Component clickable={clickable}>
            <h2>작성한 인수인계</h2>
            <div className="content">
                <div className="title">
                    <p>목 록 (총 {transferorTotal} 건)</p>
                    <div className="container">
                        <div className="new" onClick={() => clickable ? setNewVisible(true) : null}>인수인계 추가</div>
                    </div>
                </div>
                <div className="data column">
                    <div className="id">번 호</div>
                    <div className="date">작성 날짜</div>
                    <div className="title">프로젝트/작업명</div>
                    <div className="transferor">인계자</div>
                    <div className="state">진행 상태</div>
                </div>
                {transferor.map((handover, index) => (
                    <div className="data">
                        <div className="id">{transferor.length - index}</div>
                        <div className="date">{new Date(handover.createDate).toLocaleDateString()}</div>
                        <div className="title">{handover.title}</div>
                        <div className="transferor">{handover.transferor}</div>
                        <div className="state">{handover.state ? "진행중" : "완 료"}</div>
                    </div>
                ))}
            </div>
            <h2>받은 인수인계</h2>
            <div className="content">
                <div className="title">
                    <p>목 록 (총 {transfereeTotal} 건)</p>
                </div>
                <div className="data column">
                    <div className="id">번 호</div>
                    <div className="date">작성 날짜</div>
                    <div className="title">프로젝트/작업명</div>
                    <div className="transferor">인계자</div>
                    <div className="transferee">인수자</div>
                    <div className="state">진행 상태</div>
                </div>
                {transferee.map((handover, index) => (
                    <div className="data">
                        <div className="id">{transferee.length - index}</div>
                        <div className="date">{handover.transferDate}</div>
                        <div className="title">{handover.title}</div>
                        <div className="transferor">{handover.transferor}</div>
                        <div className="transferee">{handover.transferee}</div>
                        <div className="state">{handover.state ? "진행중" : "완 료"}</div>
                    </div>
                ))}
            </div>
            <Modal size={["800px", ""]} title="새 인수인계" visible={newVisible} set={setNewVisible}>
                <NewHandoverForm refresh={refresh} setRefresh={setRefresh} token={props.token}
                                 setNewVisible={setNewVisible}/>
            </Modal>
        </Component>
    );
}

export default HandoverPage;

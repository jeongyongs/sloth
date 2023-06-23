import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {BLUE, BLUE_ACTIVE, FOOTER_BORDER, GRAY, LIGHT_GRAY, SIDEBAR_BACKGROUND, WHITE} from "../constants";
import Modal from "../components/Modal";
import NewHandoverForm from "../components/NewHandoverForm";
import axios from "axios";
import {useParams} from "react-router-dom";
import ViewHandoverForm from "../components/ViewHandoverForm";
import TransfereeForm from "../components/TransfereeForm";
import ViewHandoverTransfereeForm from "../components/ViewHandoverTransfereeForm";

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

      &.row {
        cursor: pointer;

        &:hover {
          background-color: ${FOOTER_BORDER};
        }

        &:active {
          background-color: ${LIGHT_GRAY};
        }
      }

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

        > div.transfer {
          > div {
            cursor: pointer;
            display: flex;
            justify-content: center;
            align-items: center;
            color: ${WHITE};
            border-radius: 3px;
            background-color: ${BLUE};
            padding: 2px 10px;
            font-size: 14px;

            &:active {
              background-color: ${BLUE_ACTIVE};
              color: ${LIGHT_GRAY};

              > svg {
                fill: ${LIGHT_GRAY};
              }
            }

            > svg {
              fill: ${WHITE};
              margin-right: 2px;
            }
          }
        }
      }

      > div.state {
        width: 15%;
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
    const [viewVisible, setViewVisible] = useState(false);
    const [viewTransfereeVisible, setViewTransfereeVisible] = useState(false);
    const [selectHandover, setSelectHandover] = useState(0);
    const [transfereeVisible, setTransfereeVisible] = useState(false);

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

        axios.get(`/api/handovers/transferee`, {
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
                    <div className="transferee">인수자</div>
                    <div className="state">진행 상태</div>
                </div>
                {transferor.map((handover, index) => (
                    <div className="data row" onClick={() => {
                        setSelectHandover(handover.handoverId);
                        setViewVisible(true);
                    }}>
                        <div className="id">{transferor.length - index}</div>
                        <div className="date">{new Date(handover.createDate).toLocaleDateString()}</div>
                        <div className="title">{handover.title}</div>
                        <div className="transferor">{handover.transferor}</div>
                        <div className="transferee">
                            <div className="transfer">
                                <div onClick={(e) => {
                                    e.stopPropagation(); // 버블링 현상 방지
                                    setSelectHandover(handover.handoverId);
                                    setTransfereeVisible(true);
                                }}>
                                    <svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960"
                                         width="20">
                                        <path
                                            d="m639-144-12-56q-14-5-26.5-12T577-228l-55 17-32-55 41-40q-4-14-3.5-29t3.5-29l-41-39 32-56 54 16q11-10 24-17.5t27-11.5l13-56h64l13 56q14 5 27 12t24 17l54-15 32 55-40 38q2 15 2 30t-3 29l41 39-32 55-55-16q-11 9-23.5 16.5T717-200l-14 56h-64ZM96-192v-92q0-26 12.5-47.5T143-366q55-32 116-49t125-17q11 0 20.5.5T425-430q-22 55-15 121.5T451-192H96Zm576-72q30 0 51-21t21-51q0-30-21-51t-51-21q-30 0-51 21t-21 51q0 30 21 51t51 21ZM384-480q-60 0-102-42t-42-102q0-60 42-102t102-42q60 0 102 42t42 102q0 60-42 102t-102 42Z"/>
                                    </svg>
                                    관 리
                                </div>
                            </div>
                        </div>
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
                    <div className="data row" onClick={() => {
                        setSelectHandover(handover.handoverId);
                        setViewTransfereeVisible(true);
                    }}>
                        <div className="id">{transferee.length - index}</div>
                        <div className="date">{new Date(handover.transferDate).toLocaleDateString()}</div>
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
            <Modal size={["800px", ""]} title="작성한 인수인계" visible={viewVisible} set={setViewVisible}>
                <ViewHandoverForm visible={viewVisible} set={setViewVisible} refresh={refresh} setRefresh={setRefresh}
                                  token={props.token} select={selectHandover}/>
            </Modal>
            <Modal size={["800px", ""]} title="받은 인수인계" visible={viewTransfereeVisible} set={setViewTransfereeVisible}>
                <ViewHandoverTransfereeForm visible={viewTransfereeVisible} set={setViewTransfereeVisible}
                                            refresh={refresh} setRefresh={setRefresh}
                                            token={props.token} select={selectHandover}/>
            </Modal>
            <Modal size={["600px", "600px"]} title="인수자 관리" visible={transfereeVisible} set={setTransfereeVisible}>
                <TransfereeForm select={selectHandover} token={props.token}/>
            </Modal>
        </Component>
    );
}

export default HandoverPage;

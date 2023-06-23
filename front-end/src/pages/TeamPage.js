import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {useNavigate, useParams} from "react-router-dom";
import {BLUE, BLUE_ACTIVE, GRAY, LIGHT_GRAY, RED, RED_ACTIVE, WHITE} from "../constants";
import axios from "axios";
import Members from "../components/Members";
import Modal from "../components/Modal";
import InviteForm from "../components/InviteForm";
import LeaveForm from "../components/LeaveForm";

const Component = styled.div`
  > div.profile { // 팀 네임 카드
    display: flex;
    margin-bottom: 20px;

    > div.icon {
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 0 3px 0 3px;
      background-color: ${BLUE};
      border-radius: 3px;
      margin-right: 10px;

      > svg {
        fill: ${WHITE};
      }
    }

    > div.info {
      > div.top {
        display: flex;

        > p.team-name {
          margin: 0 10px 2px 0;
          font-weight: 600;
          white-space: nowrap;
        }

        > p.team-id {
          color: ${LIGHT_GRAY};
          margin: 0;
          white-space: nowrap;
        }
      }

      > div.bottom {
        display: flex;

        > p.leader-name {
          background-color: ${GRAY};
          border-radius: 3px;
          padding: 0 4px 0 4px;
          margin: 0 8px 0 0;
          color: ${WHITE};
          font-size: 14px;
          white-space: nowrap;
        }

        > p.create-date {
          color: ${LIGHT_GRAY};
          font-size: 14px;
          margin: 0;
          white-space: nowrap;
        }
      }
    }
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

      div.container {
        display: flex;

        > div.invite {
          cursor: ${props => props.isLeader ? "pointer" : "default"};
          display: ${props => props.isLeader ? "flex" : "none"};
          justify-content: center;
          align-items: center;
          padding: 0 20px;
          border-radius: 3px;
          background-color: ${BLUE};
          color: ${WHITE};

          &:active {
            background-color: ${BLUE_ACTIVE};
            color: ${LIGHT_GRAY};
          }
        }

        > div.leave {
          cursor: ${props => !props.isLeader ? "pointer" : "default"};
          display: ${props => !props.isLeader ? "flex" : "none"};
          justify-content: center;
          align-items: center;
          padding: 0 20px;
          border-radius: 3px;
          background-color: ${RED};
          color: ${WHITE};

          &:active {
            background-color: ${RED_ACTIVE};
            color: ${LIGHT_GRAY};
          }
        }
      }
    }
  }
`

function TeamPage(props) {

    const {teamId} = useParams();
    const [data, setData] = useState({});
    const [total, setTotal] = useState(0);
    const [isLeader, setIsLeader] = useState(false);
    const [inviteVisible, setInviteVisible] = useState(false);
    const [leaveVisible, setLeaveVisible] = useState(false);
    const [input, setInput] = useState("");
    const navigator = useNavigate();

    useEffect(() => {
        if (teamId === "me") {
            props.setSelect(1);
            navigator("/teams/me/dashboard");
        }
    }, [teamId]);

    useEffect(() => {
        if (teamId === "me") {
            return;
        }
        setData({});
        axios.get(`/api/teams/${teamId}`, {
            headers: {
                "Authorization": `Bearer ${props.token}`
            }
        }).then(response => {
            setData(response.data);
        }).catch(() => {
            props.setSelect(1);
            props.setCurrent(["대시보드"]);
            navigator("/teams/me/dashboard");
            if (props.refresh) {
                props.setRefresh(false);
                return;
            }
            props.setRefresh(true);
        });
    }, [teamId, props.refresh]);

    function set() {
        setInput("");
        setInviteVisible(false);
    }

    return (
        <Component isLeader={isLeader}>
            <br/>
            <div className="profile">
                <div className="icon">
                    <svg xmlns="http://www.w3.org/2000/svg" height="40" viewBox="0 -960 960 960" width="40">
                        <path
                            d="M40-160v-160q0-29 20.5-49.5T110-390h141q17 0 32.5 8.5T310-358q29 42 74 65t96 23q51 0 96-23t75-65q11-15 26-23.5t32-8.5h141q29 0 49.5 20.5T920-320v160H660v-119q-36 33-82.5 51T480-210q-51 0-97-18t-83-51v119H40Zm440-170q-35 0-67.5-16.5T360-392q-16-23-38.5-37T273-448q29-30 91-46t116-16q54 0 116.5 16t91.5 46q-26 5-48.5 19T601-392q-20 29-52.5 45.5T480-330ZM160-460q-45 0-77.5-32.5T50-570q0-46 32.5-78t77.5-32q46 0 78 32t32 78q0 45-32 77.5T160-460Zm640 0q-45 0-77.5-32.5T690-570q0-46 32.5-78t77.5-32q46 0 78 32t32 78q0 45-32 77.5T800-460ZM480-580q-45 0-77.5-32.5T370-690q0-46 32.5-78t77.5-32q46 0 78 32t32 78q0 45-32 77.5T480-580Z"/>
                    </svg>
                </div>
                <div className="info">
                    <div className="top">
                        <p className="team-name">{data.teamName}</p>
                        <p className="team-id"># {teamId}</p>
                    </div>
                    <div className="bottom">
                        <p className="leader-name">{data.leaderName}</p>
                        <p className="create-date">{new Date(data.createDate).toLocaleDateString()}</p>
                    </div>
                </div>
            </div>
            <div className="content">
                <div className="title">
                    <p>구성원 (총 {total} 명)</p>
                    <div className="container">
                        <div className="invite" onClick={() => {
                            setInviteVisible(true)
                        }}>초 대
                        </div>
                        <div className="leave" onClick={() => {
                            setLeaveVisible(true)
                        }}>탈 퇴
                        </div>
                    </div>
                </div>
                <Members isLeader={isLeader} setIsLeader={setIsLeader} setTotal={setTotal} token={props.token}/>
            </div>
            <Modal size={["600px", "600px"]} title="초 대" visible={inviteVisible} set={set}>
                <InviteForm input={input} setInput={setInput} token={props.token}/>
            </Modal>
            <Modal size={["300px", ""]} title="탈 퇴" visible={leaveVisible} set={setLeaveVisible}>
                <LeaveForm refresh={props.refresh} setRefresh={props.setRefresh} token={props.token}
                           teamName={data.teamName}/>
            </Modal>
        </Component>
    );
}

export default TeamPage;

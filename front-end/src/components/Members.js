import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {LIGHT_GRAY, RED, RED_ACTIVE, SIDEBAR_BACKGROUND, WHITE} from "../constants";
import axios from "axios";
import {useParams} from "react-router-dom";
import KickForm from "./KickForm";
import Modal from "./Modal";

const Component = styled.div`
  > div.data { // 컬럼
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
      width: 20%;
      overflow-x: hidden;
      text-overflow: ellipsis;
    }

    > div.name {
      width: 20%;
      overflow-x: hidden;
      text-overflow: ellipsis;
    }

    > div.join-date {
      width: 20%;
      overflow-x: hidden;
      text-overflow: ellipsis;
    }

    > div.manage {
      display: flex;
      justify-content: center;
      width: 30%;
      overflow-x: hidden;
      text-overflow: ellipsis;

      > div.kick {
        > div {
          cursor: pointer;
          display: flex;
          justify-content: center;
          align-items: center;
          color: ${WHITE};
          border-radius: 3px;
          background-color: ${RED};
          padding: 2px 10px;
          font-size: 14px;

          &:active {
            background-color: ${RED_ACTIVE};
            color: ${LIGHT_GRAY};

            > svg {
              fill: ${LIGHT_GRAY};
            }
          }

          > svg {
            fill: ${WHITE};
          }
        }
      }
    }
  }
`

function Members(props) {

    const {teamId} = useParams();
    const [members, setMembers] = useState([]);
    const [visible, setVisible] = useState(false);
    const [kickUserId, setKickUserId] = useState("");
    const [kickUsername, setKickUsername] = useState("");
    const [refresh, setRefresh] = useState(false);

    useEffect(() => {
        setMembers([]);
        props.setIsLeader(false);
        axios.get(`/api/teams/${teamId}/members`, {
            headers: {
                "Authorization": `Bearer ${props.token}`
            }
        }).then(responese => {
            setMembers(responese.data.members);
            props.setIsLeader(responese.data.leader);
            props.setTotal(responese.data.members.length);
        }).catch(() => {
        });
    }, [teamId, refresh]);

    function set(bool) {
        setKickUserId("");
        setKickUsername("");
        setVisible(bool);
    }

    function kickConfirm(e) {
        setKickUserId(e.target.id);
        setKickUsername(e.target.className);
        setVisible(true);
    }

    return (
        <Component>
            <div className="data title">
                <div className="id">번 호</div>
                <div className="join-date">가입 날짜</div>
                <div className="username">아이디</div>
                <div className="name">이 름</div>
                <div className="manage">{props.isLeader ? "관 리" : ""}</div>
            </div>
            {members.map((member, index) => (
                <div className="data">
                    <div className="id">{index + 1}</div>
                    <div className="join-date">{new Date(member.joinDate).toLocaleDateString()}</div>
                    <div className="username">{member.username}</div>
                    <div className="name">{member.name}</div>
                    <div className="manage">
                        {props.isLeader && member.role !== "leader" ? (
                            <div className="kick">
                                <div id={member.id} className={member.username} onClick={kickConfirm}>
                                    <svg xmlns="http://www.w3.org/2000/svg" height="16" viewBox="0 -960 960 960"
                                         width="16">
                                        <path
                                            d="M180-120q-24 0-42-18t-18-42v-600q0-24 18-42t42-18h291v60H180v600h291v60H180Zm486-185-43-43 102-102H375v-60h348L621-612l43-43 176 176-174 174Z"/>
                                    </svg>
                                    내보내기
                                </div>
                            </div>) : ""}
                    </div>
                </div>
            ))}
            <Modal size={["300px", ""]} title="내보내기" visible={visible} set={set}>
                <KickForm kickUsername={kickUsername} setKickUsername={setKickUsername} set={setVisible}
                          kickUserId={kickUserId} setKickUserId={setKickUserId} refresh={refresh}
                          setRefresh={setRefresh} token={props.token}/>
            </Modal>
        </Component>
    );
}

export default Members;

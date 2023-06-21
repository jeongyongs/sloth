import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {BLUE, BLUE_ACTIVE, GRAY, LIGHT_GRAY, RED, RED_ACTIVE, SIDEBAR_BACKGROUND, WHITE} from "../constants";
import axios from "axios";

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

      > div.team-name {
        width: 20%;
        overflow-x: hidden;
        text-overflow: ellipsis;
      }

      > div.leader {
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

        > div.accept {
          cursor: ${props => props.clickable ? "pointer" : "default"};
          background-color: ${props => props.clickable ? BLUE : GRAY};
          color: ${WHITE};
          padding: 0 10px;
          margin-right: 10px;
          border-radius: 3px;

          &:active {
            background-color: ${props => props.clickable ? BLUE_ACTIVE : ""};
            color: ${props => props.clickable ? LIGHT_GRAY : ""};
          }
        }

        > div.decline {
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
`

function InvitePage(props) {

    const [total, setTotal] = useState(0);
    const [data, setData] = useState([]);
    const [clickable, setClickable] = useState(true);
    const [refresh, setRefresh] = useState(false);

    useEffect(() => {
        axios.get(`/api/invites`, {
            headers: {
                "Authorization": `Bearer ${props.token}`
            }
        }).then(response => {
            setData(response.data);
            setTotal(response.data.length);
        }).catch(() => {
        });
    }, [refresh])

    function refreshTeam() {
        if (props.refresh) {
            props.setRefresh(false);
            return;
        }
        props.setRefresh(true);
    }

    function accept(inviteId) {
        if (clickable) {
            setClickable(false);
            axios.post(`/api/invites/${inviteId}`, null, {
                headers: {
                    "Authorization": `Bearer ${props.token}`
                }
            }).then(response => {
                setClickable(true);
                refreshTeam();
                if (refresh) {
                    setRefresh(false);
                    return;
                }
                setRefresh(true);
            }).catch(() => {
                setClickable(true);
            });
        }
    }

    function decline(inviteId) {
        if (clickable) {
            setClickable(false);
            axios.delete(`/api/invites/${inviteId}`, {
                headers: {
                    "Authorization": `Bearer ${props.token}`
                }
            }).then(response => {
                setClickable(true);
                if (refresh) {
                    setRefresh(false);
                    return;
                }
                setRefresh(true);
            }).catch(() => {
                setClickable(true);
            });
        }
    }

    return (
        <Component clickable={clickable}>
            <h2>받은 초대</h2>
            <div className="content">
                <div className="title">
                    <p>목 록 (총 {total} 건)</p>
                </div>
                <div className="data column">
                    <div className="id">번 호</div>
                    <div className="join-date">초대 날짜</div>
                    <div className="team-name">팀 명</div>
                    <div className="leader">리 더</div>
                    <div className="manage">응 답</div>
                </div>
                {data.map((invite, index) => (
                    <div className="data">
                        <div className="id">{index + 1}</div>
                        <div className="join-date">{new Date(invite.inviteDate).toLocaleDateString()}</div>
                        <div className="team-name">{invite.teamName}(#{invite.teamId})</div>
                        <div className="leader">{invite.leaderName}({invite.leaderUsername})</div>
                        <div className="manage">
                            <div className="accept" onClick={() => accept(invite.inviteId)}>수락</div>
                            <div className="decline" onClick={() => decline(invite.inviteId)}>거절</div>
                        </div>
                    </div>
                ))}
            </div>
        </Component>
    );
}

export default InvitePage;

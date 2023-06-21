import React, {useState} from "react";
import styled from "styled-components";
import {GRAY, LIGHT_GRAY, SIDEBAR_BACKGROUND, WHITE} from "../constants";

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
      }
    }
  }
`

function ReportPage(props) {

    const [total, setTotal] = useState(0);

    return (
        <Component>
            <h2>보고서</h2>
            <div className="content">
                <div className="title">
                    <p>조 회 (총 {total} 건)</p>
                </div>
                <div className="data column">
                    <div className="id">번 호</div>
                    <div className="team-name">팀 명</div>
                    <div className="leader">리 더</div>
                    <div className="join-date">초대 일자</div>
                    <div className="manage">응 답</div>
                </div>
                <div className="data">
                    <div className="id">번 호</div>
                    <div className="team-name">팀 명</div>
                    <div className="leader">리 더</div>
                    <div className="join-date">초대 일자</div>
                    <div className="manage">응 답</div>
                </div>
            </div>
        </Component>
    );
}

export default ReportPage;

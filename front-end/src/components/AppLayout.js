import React, {useState} from "react";
import styled from "styled-components";
import {useParams} from "react-router-dom";
import TeamSidebar from "./TeamSidebar";
import MenuSidebar from "./MenuSidebar";
import Topbar from "./Topbar";
import {FOOTER_BACKGROUND, FOOTER_BORDER, MAIN_BACKGROUND, SIDEBAR_BACKGROUND} from "../constants";
import Terms from "./Terms";
import Modal from "./Modal";

const Component = styled.div`
  display: flex;

  > div.main-container {
    background-color: ${MAIN_BACKGROUND};
    height: 100vh;

    > div.main {
      height: calc(100% - 80px);
      overflow-y: auto;

      > div.body {
        height: auto;
        min-height: calc(100% - 81px);
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 10px;
      }

      > div.footer {
        color: ${SIDEBAR_BACKGROUND};
        font-weight: 600;
        display: flex;
        align-items: center;
        padding-left: 50px;
        background-color: ${FOOTER_BACKGROUND};
        position: relative;
        height: 80px;
        border-top: 1px solid ${FOOTER_BORDER};
      }
    }
  }
  
  > div.form {
    background-color: red;
  }
`

function AppLayout(props) {

    const {teamId} = useParams();
    const testList = [{id: 1, name: "슬로스"}, {id: 2, name: "123456"}, {id: 123, name: "TEST"}];
    const [newTeamVisible, setNewTeamVisible] = useState(false);

    function map() {
        if (props.select === 1) {
            return ["대시보드"];
        }
        if (props.select === 2) {
            return ["알 림"];
        }
        if (props.select === 3) {
            return ["프로필"];
        }
        if (props.select === 4) {
            return ["초 대"];
        }
        if (props.select === 5) {
            return [testList.filter(item => item.id === Number(teamId))[0].name, "<NEXT>", "팀"];
        }
        if (props.select === 6) {
            return [testList.filter(item => item.id === Number(teamId))[0].name, "<NEXT>", "인수인계"];
        }
        return [testList.filter(item => item.id === Number(teamId))[0].name, "<NEXT>", "보고서"];
    }

    return (
        <Component>
            <TeamSidebar set={setNewTeamVisible} select={props.select} list={testList}/>
            <MenuSidebar select={props.select}/>
            <div className="main-container">
                <Topbar current={map()}/>
                <div className="main">
                    <div className="body">
                        {props.children}
                    </div>
                    <div className="footer">
                        SLOTH
                    </div>
                </div>
            </div>
            <Modal size={["500px", "700px"]} title="팀 생성" visible={newTeamVisible} set={setNewTeamVisible}>
                <div className="form">
                    <label htmlFor="username">아이디</label>
                    <input id="username" type="text" placeholder="로그인 시 사용할 아이디를 입력하세요"/>
                </div>
            </Modal>
        </Component>
    );
}

export default AppLayout;

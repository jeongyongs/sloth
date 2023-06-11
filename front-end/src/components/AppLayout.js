import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {useNavigate, useParams} from "react-router-dom";
import TeamSidebar from "./TeamSidebar";
import MenuSidebar from "./MenuSidebar";
import Topbar from "./Topbar";
import {FOOTER_BACKGROUND, FOOTER_BORDER, MAIN_BACKGROUND, SIDEBAR_BACKGROUND} from "../constants";
import Modal from "./Modal";
import NewTeamForm from "./NewTeamForm";
import axios from "axios";

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
    const [teams, setTeams] = useState([]);
    const [newTeamVisible, setNewTeamVisible] = useState(false);
    const [refresh, setRefresh] = useState(false);
    const [move, setMove] = useState(false);
    const [current, setCurrent] = useState([]);
    const navigator = useNavigate();

    useEffect(() => {
        axios.get("/api/teams", {
            headers: {
                "Authorization": `Bearer ${props.token}`
            }
        })
            .then(responese => {
                setTeams(responese.data);
            }).catch(() => {
            props.setToken("");
        });
    }, [refresh]);

    useEffect(() => {
        if (props.token.length === 0) {
            navigator("/login");
        }
    }, []);

    useEffect(() => {
        if (move) {
            setMove(false);
            navigator(`/teams/${teams[teams.length - 1].id}/dashboard`);
            return;
        }
        if (props.select === 1) {
            setCurrent(["대시보드"]);
            return;
        }
        if (props.select === 2) {
            setCurrent(["알 림"]);
            return;
        }
        if (props.select === 3) {
            setCurrent(["프로필"]);
            return;
        }
        if (props.select === 4) {
            setCurrent(["초 대"]);
            return;
        }
        if (props.select === 5) {
            setCurrent([teams.filter(item => item.id === Number(teamId))[0] ? teams.filter(item => item.id === Number(teamId))[0].name : "", "<NEXT>", "팀"]);
            return;
        }
        if (props.select === 6) {
            setCurrent([teams.filter(item => item.id === Number(teamId))[0] ? teams.filter(item => item.id === Number(teamId))[0].name : "", "<NEXT>", "인수인계"]);
            return;
        }
        setCurrent([teams.filter(item => item.id === Number(teamId))[0] ? teams.filter(item => item.id === Number(teamId))[0].name : "", "<NEXT>", "보고서"]);
    }, [teams, teamId]);

    return (
        <Component>
            <TeamSidebar set={setNewTeamVisible} select={props.select} list={teams}/>
            <MenuSidebar select={props.select}/>
            <div className="main-container">
                <Topbar current={current}/>
                <div className="main">
                    <div className="body">
                        {props.children}
                    </div>
                    <div className="footer">
                        SLOTH
                    </div>
                </div>
            </div>
            <Modal size={["500px", ""]} title="팀 생성" visible={newTeamVisible} set={setNewTeamVisible}>
                <NewTeamForm setMove={setMove} select={props.select} token={props.token} set={setNewTeamVisible}
                             refresh={refresh} setRefresh={setRefresh}/>
            </Modal>
        </Component>
    );
}

export default AppLayout;

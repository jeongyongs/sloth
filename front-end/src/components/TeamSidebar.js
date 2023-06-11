import React, {useState} from "react";
import styled from "styled-components";
import TeamSidebarButton from "./TeamSidebarButton";
import {BLUE, SIDEBAR_BACKGROUND, SIDEBAR_BORDER, SIDEBAR_BUTTON_HOVER, SIDEBAR_TEAM_BUTTON} from "../constants";
import Modal from "./Modal";
import Terms from "./Terms";

const Component = styled.div`
  background-color: ${SIDEBAR_BACKGROUND};
  box-sizing: border-box;
  height: 100vh;
  border-right: 1px solid ${SIDEBAR_BORDER};
  padding: 10px;

  > div.me-container {
    box-sizing: border-box;

    > div.line {
      height: 10px;
      border-top: 2px solid ${SIDEBAR_BORDER};
    }
  }

  > div.teams-container {
    box-sizing: border-box;
    height: calc(100% - 70px);
    overflow-y: scroll;

    &::-webkit-scrollbar {
      display: none;
    }

    > div.create-new-team-button {
      cursor: pointer;
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: ${SIDEBAR_TEAM_BUTTON};
      width: 50px;
      height: 50px;
      border-radius: 100px;

      &:active {
        background-color: ${SIDEBAR_BUTTON_HOVER};
      }

      > svg {
        fill: ${BLUE};
      }
    }
  }
`

function TeamSidebar(props) {
    return (
        <Component>
            <div className="me-container">
                <TeamSidebarButton select={props.select} id="me">
                    <svg xmlns="http://www.w3.org/2000/svg" height="30" viewBox="0 -960 960 960" width="30">
                        <path d="M192-144v-456l288-216 288 216v456H552v-264H408v264H192Z"/>
                    </svg>
                </TeamSidebarButton>
                <div className="line"/>
            </div>
            <div className="teams-container">
                {props.list.map((team) => (<TeamSidebarButton select={props.select} id={team.id} name={team.name}/>))}
                <div className="create-new-team-button" onClick={() => {
                    props.set(true);
                }}>
                    <svg xmlns="http://www.w3.org/2000/svg" height="32" viewBox="0 -960 960 960" width="32">
                        <path d="M444-240v-204H240v-72h204v-204h72v204h204v72H516v204h-72Z"/>
                    </svg>
                </div>
            </div>
        </Component>
    );
}

export default TeamSidebar;

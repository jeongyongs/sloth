import React from "react";
import styled from "styled-components";
import {useNavigate, useParams} from "react-router-dom";
import {BLUE, SIDEBAR_BUTTON_HOVER, SIDEBAR_TEAM_BUTTON, WHITE} from "../constants";

const Component = styled.div`
  background-color: ${props => props.selected ? BLUE : SIDEBAR_TEAM_BUTTON};
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  border-radius: 100px;
  width: 50px;
  height: 50px;
  margin-bottom: 10px;

  &:active {
    background-color: ${SIDEBAR_BUTTON_HOVER};
  }

  > div.inner {
    color: ${WHITE};
    overflow: hidden;
    text-align: center;
    width: 50px;

    > svg {
      fill: ${WHITE};
    }
  }
`

function TeamSidebarButton(props) {

    const {teamId} = useParams();
    const navigator = useNavigate();

    return (
        <Component onClick={() => {
            if (props.select === 1) {
                navigator(`/teams/${props.id}/dashboard`);
                return;
            }
            if (props.select === 2) {
                navigator(`/teams/${props.id}/notification`);
                return;
            }
            if (props.select === 3) {
                navigator(`/teams/${props.id}/profile`);
                return;
            }
            if (props.select === 4) {
                navigator(`/teams/${props.id}/invites`);
                return;
            }
            if (props.select === 5) {
                navigator(`/teams/${props.id}`);
                return;
            }
            if (props.select === 6) {
                navigator(`/teams/${props.id}/handovers`);
                return;
            }
            navigator(`/teams/${props.id}/reports`);
        }} selected={String(props.id) === teamId}>
            <div className="inner">
                {props.name}
                {props.children}
            </div>
        </Component>
    );
}

export default TeamSidebarButton;

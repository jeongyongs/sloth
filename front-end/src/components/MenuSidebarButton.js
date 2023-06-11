import React from "react";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";
import {GRAY, SIDEBAR_BUTTON_HOVER, WHITE} from "../constants";

const Component = styled.div`
  cursor: ${props => props.selected ? "default" : "pointer"};
  display: flex;
  align-items: center;
  padding: 10px;

  > svg {
    fill: ${props => props.selected ? WHITE : GRAY};
    margin-right: 10px;
  }

  > p {
    margin: 0;
    color: ${props => props.selected ? WHITE : GRAY};
    font-weight: ${props => props.selected ? "bold" : ""};
  }

  &:hover {
    background-color: ${props => props.selected ? "" : SIDEBAR_BUTTON_HOVER};
  }
`

function MenuSidebarButton(props) {

    const navigator = useNavigate();
    const isClickable = !props.selected;

    return (
        <Component selected={props.selected} onClick={() => {
            if (isClickable) {
                navigator(props.to);
            }
        }}>
            {props.children}
            <p>{props.name}</p>
        </Component>
    );
}

export default MenuSidebarButton;

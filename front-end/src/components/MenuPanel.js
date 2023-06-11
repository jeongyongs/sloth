import React from "react";
import styled from "styled-components";
import {SIDEBAR_BORDER, WHITE} from "../constants";

const Component = styled.div`
  display: ${props => props.hide ? "none" : "block"};
  box-sizing: border-box;
  border-bottom: 1px solid ${SIDEBAR_BORDER};
  padding: 20px;

  > h1 {
    font-size: 20px;
    color: ${WHITE};
    margin: 0;
    padding: 0 10px;
  }
`

function MenuPanel(props) {
    return (
        <Component hide={props.hide}>
            {props.children}
        </Component>
    );
}

export default MenuPanel;

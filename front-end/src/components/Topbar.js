import React from "react";
import styled from "styled-components";
import {GRAY, LIGHT_GRAY, WHITE} from "../constants";

const Component = styled.div`
  display: flex;
  align-items: center;
  background-color: ${WHITE};
  box-sizing: border-box;
  width: calc(100vw - 331px);
  height: 80px;
  padding: 20px 50px;
  box-shadow: rgba(0, 0, 0, 0.1) 0 0 10px 0;

  > p {
    color: ${GRAY};
  }

  > svg {
    fill: ${LIGHT_GRAY};
    margin: 0 10px;
  }
`

function Topbar(props) {
    return (
        <Component>
            {props.current.map(item => {
                if (item === "<NEXT>") {
                    return (<svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                        <path d="m384-240-51-51 189-189-189-189 51-51 240 240-240 240Z"/>
                    </svg>);
                }
                return <p>{item}</p>;
            })}
        </Component>
    );
}

export default Topbar;

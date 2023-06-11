import React from "react";
import styled from "styled-components";
import {MAIN_BACKGROUND, WHITE} from "../constants";

const Component = styled.div`
  display: ${props => props.visible ? "flex" : "none"};
  justify-content: center;
  align-items: center;
  position: fixed;
  background-color: rgba(0, 0, 0, 0.5);
  width: 100vw;
  height: 100vh;

  > div.container {
    background-color: ${WHITE};
    padding: 20px;
    width: ${props => props.size[0]};
    height: ${props => props.size[1]};
    margin: 10px;

    > div.main {
      box-sizing: border-box;
      height: calc(100% - 24px);
      overflow-y: auto;
    }

    > div.title {
      display: flex;
      justify-content: space-between;

      > p {
        margin: 0 0 10px;
      }

      > svg {
        border-radius: 100px;
        cursor: pointer;

        &:active {
          background-color: ${MAIN_BACKGROUND};
        }
      }
    }
  }
`

function Modal(props) {
    return (
        <Component visible={props.visible} size={props.size}>
            <div className="container">
                <div className="title">
                    <p>{props.title}</p>
                    <svg onClick={() => {
                        props.set(false);
                    }} xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                        <path
                            d="m291-240-51-51 189-189-189-189 51-51 189 189 189-189 51 51-189 189 189 189-51 51-189-189-189 189Z"/>
                    </svg>
                </div>
                <div className="main">
                    {props.children}
                </div>
            </div>
        </Component>
    );
}

export default Modal;

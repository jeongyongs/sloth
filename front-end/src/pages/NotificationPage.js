import React from "react";
import styled from "styled-components";
import {GRAY, WHITE} from "../constants";

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
  }
`

function NotificationPage(props) {
    return (
        <Component>
            <h2>알 림</h2>
            <div className="content"></div>
        </Component>
    );
}

export default NotificationPage;

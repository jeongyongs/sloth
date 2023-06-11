import React from "react";
import styled from "styled-components";
import {BLUE, GRAY, MAIN_BACKGROUND} from "../constants";
import {useNavigate} from "react-router-dom";

const Component = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${MAIN_BACKGROUND};
  width: 100vw;
  height: 100vh;
  white-space: pre-line;

  > div.container {
    color: ${GRAY};
    width: 700px;
    padding: 20px;

    > * {
      margin: 0;
      text-align: center;
    }

    > h1 {
      width: 100px;
      padding: 10px 0;
      margin: 0 auto;
      cursor: pointer;
      color: ${BLUE};
      font-size: 16px;
    }

    > h2 {
      font-size: 100px;
    }

    > h3 {
      font-size: 50px;
      margin-bottom: 50px;
    }

    > p {
      font-size: 20px;
      margin-bottom: 100px;
    }
  }
`

function NotFoundPage(props) {

    const navigator = useNavigate();

    return (
        <Component>
            <div className="container">
                <h2>404</h2>
                <h3>Not Found</h3>
                <p>
                    죄송합니다, 요청하신 페이지를 찾을 수 없습니다.<br/>
                    해당 URL에는 현재 콘텐츠가 존재하지 않거나, 이동하거나 삭제되었을 수 있습니다.
                </p>
                <h1 onClick={() => {
                    navigator("/");
                }}>SLOTH</h1>
            </div>
        </Component>
    );
}

export default NotFoundPage;

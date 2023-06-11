import React from "react";
import styled from "styled-components";
import AppLayout from "../components/AppLayout";
import {MAIN_BACKGROUND} from "../constants";
import {useNavigate} from "react-router-dom";

const Component = styled.div`
  background-color: ${MAIN_BACKGROUND};

  > * {
    margin: 0;
  }

  > div.test {
    background-color: green;
    height: 200px;
    margin-bottom: 20px;
  }
`

function DashboardPage(props) {
    const navigator = useNavigate();
    return (
        <AppLayout select={1}>
            <Component>
                <h2>대시보드</h2>
                <div className="test"></div>
                <div className="test"></div>
                <div className="test"></div>
                <div className="test"></div>
                <div className="test"></div>
                <div className="test"></div>
                <div className="test"></div>
                <div className="test"></div>
                <div className="test"></div>
            </Component>
        </AppLayout>
    );
}

export default DashboardPage;

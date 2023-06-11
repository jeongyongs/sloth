import React from "react";
import styled from "styled-components";
import AppLayout from "../components/AppLayout";
import {MAIN_BACKGROUND} from "../constants";

const Component = styled.div`
  background-color: ${MAIN_BACKGROUND};

  > * {
    margin: 0;
  }
`

function NotificationPage(props) {
    return (
        <AppLayout select={2}>
            <Component>
                <h2>알 림</h2>
            </Component>
        </AppLayout>
    );
}

export default NotificationPage;

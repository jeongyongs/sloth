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

function InvitePage(props) {
    return (
        <AppLayout select={4}>
            <Component>
                <h2>초 대</h2>
            </Component>
        </AppLayout>
    );
}

export default InvitePage;

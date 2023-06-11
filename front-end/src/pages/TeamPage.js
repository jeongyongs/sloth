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

function TeamPage(props) {
    return (
        <AppLayout select={5}>
            <Component>
                <h2>팀</h2>
            </Component>
        </AppLayout>
    );
}

export default TeamPage;

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

function HandoverPage(props) {
    return (
        <AppLayout select={6}>
            <Component>
                <h2>인수인계</h2>
            </Component>
        </AppLayout>
    );
}

export default HandoverPage;

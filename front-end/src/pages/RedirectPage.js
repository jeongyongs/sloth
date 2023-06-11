import React, {useEffect} from "react";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";

const Component = styled.div`
`

function RedirectPage(props) {

    const navigator = useNavigate();

    useEffect(() => {
        navigator("/teams/me/dashboard");
    })

    return (
        <Component>
            ds
        </Component>
    );
}

export default RedirectPage;

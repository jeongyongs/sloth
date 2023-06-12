import React, {useEffect} from "react";
import styled from "styled-components";
import {useNavigate, useParams} from "react-router-dom";

const Component = styled.div`
`

function ReportPage(props) {

    const {teamId} = useParams();
    const navigator = useNavigate();

    useEffect(() => {
        if (teamId === "me") {
            props.setSelect(1);
            navigator("/teams/me/dashboard");
        }
    }, [teamId]);

    return (
        <Component>
            <h2>보고서</h2>
        </Component>
    );
}

export default ReportPage;

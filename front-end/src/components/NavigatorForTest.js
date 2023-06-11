import React from "react";
import {WHITE} from "../constants";

function NavigatorForTest(props) {

    const uriList = ["/login", "/signup", "/teams/123/dashboard", "/teams/123/notification", "/teams/123/profile", "/teams/123/invites", "/teams/123", "/teams/123/handovers", "/teams/123/reports", "/random", "/teams/me", "/teams/me/handovers", "/teams/me/reports"];

    return (
        <ul style={{position: "fixed", top: "100px", right: 0, backgroundColor: WHITE}}>
            {uriList.map((uri) => (<li><a href={uri}>{uri}</a></li>))}
        </ul>
    );
}

export default NavigatorForTest;

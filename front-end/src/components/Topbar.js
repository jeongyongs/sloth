import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {GRAY, LIGHT_GRAY, WHITE} from "../constants";
import axios from "axios";

const Component = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: ${WHITE};
  box-sizing: border-box;
  width: calc(100vw - 331px);
  height: 80px;
  padding: 20px 50px;
  box-shadow: rgba(0, 0, 0, 0.1) 0 0 10px 0;
  position: relative;

  > div.nav {
    display: flex;
    align-items: center;

    > p {
      color: ${GRAY};
      margin: 0;
      white-space: nowrap;
    }

    > svg {
      fill: ${LIGHT_GRAY};
      margin: 0 10px;
    }
  }

  > div.user {
    > p {
      color: ${GRAY};
      white-space: nowrap;
    }
  }
`

function Topbar(props) {

    const [name, setName] = useState("");

    useEffect(() => {
        axios.get("/api/users/me", {
            headers: {
                "Authorization": `Bearer ${props.token}`
            }
        })
            .then(responese => {
                setName(responese.data);
            }).catch(() => {
            props.setToken("");
        });
    }, []);

    return (
        <Component>
            <div className="nav">
                {props.current.map(item => {
                    if (item === "<NEXT>") {
                        return (<svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                            <path d="m384-240-51-51 189-189-189-189 51-51 240 240-240 240Z"/>
                        </svg>);
                    }
                    return <p>{item}</p>;
                })}
            </div>
            <div className="user">
                <p>{name}</p>
            </div>
        </Component>
    );
}

export default Topbar;

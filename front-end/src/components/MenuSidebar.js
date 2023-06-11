import React from "react";
import styled from "styled-components";
import MenuSidebarButton from "./MenuSidebarButton";
import MenuPanel from "./MenuPanel";
import {useParams} from "react-router-dom";
import {SIDEBAR_BACKGROUND} from "../constants";

const Component = styled.div`
  background-color: ${SIDEBAR_BACKGROUND};
  box-sizing: border-box;
  width: 260px;
  min-width: 260px;
  height: 100vh;
`

function MenuSidebar(props) {

    const {teamId} = useParams();

    return (
        <Component>
            <MenuPanel>
                <h1>SLOTH</h1>
            </MenuPanel>
            <MenuPanel>
                <MenuSidebarButton selected={props.select === 1} name="대시보드" to={`/teams/${teamId}/dashboard`}>
                    <svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                        <path
                            d="M120-510v-330h330v330H120Zm0 390v-330h330v330H120Zm390-390v-330h330v330H510Zm0 390v-330h330v330H510Z"/>
                    </svg>
                </MenuSidebarButton>
            </MenuPanel>
            <MenuPanel>
                <MenuSidebarButton selected={props.select === 2} name="알 림" to={`/teams/${teamId}/notification`}>
                    <svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                        <path
                            d="M192-216v-72h48v-240q0-85.741 53.5-152.37Q347-747 432-763v-53q0-20 14-34t34-14q20 0 34 14t14 34v53q85 16 138.5 82T720-528v240h48v72H192ZM479.788-96Q450-96 429-117.15 408-138.3 408-168h144q0 30-21.212 51-21.213 21-51 21Z"/>
                    </svg>
                </MenuSidebarButton>
                <MenuSidebarButton selected={props.select === 3} name="프로필" to={`/teams/${teamId}/profile`}>
                    <svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                        <path
                            d="M237-285q54-38 115.5-56.5T480-360q66 0 127.5 18.5T723-285q35-41 52-91t17-104q0-129.675-91.23-220.838Q609.541-792 479.77-792 350-792 259-700.838 168-609.675 168-480q0 54 17 104t52 91Zm243-123q-60 0-102-42t-42-102q0-60 42-102t102-42q60 0 102 42t42 102q0 60-42 102t-102 42Zm.276 312Q401-96 331-126q-70-30-122.5-82.5T126-330.958q-30-69.959-30-149.5Q96-560 126-629.5t82.5-122Q261-804 330.958-834q69.959-30 149.5-30Q560-864 629.5-834t122 82.5Q804-699 834-629.276q30 69.725 30 149Q864-401 834-331q-30 70-82.5 122.5T629.276-126q-69.725 30-149 30Z"/>
                    </svg>
                </MenuSidebarButton>
                <MenuSidebarButton selected={props.select === 4} name="초 대" to={`/teams/${teamId}/invites`}>
                    <svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                        <path
                            d="M168-192q-29 0-50.5-21.5T96-264v-432q0-29 21.5-50.5T168-768h624q30 0 51 21.5t21 50.5v432q0 29-21 50.5T792-192H168Zm312-240 312-179v-85L480-517 168-696v85l312 179Z"/>
                    </svg>
                </MenuSidebarButton>
            </MenuPanel>
            <MenuPanel hide={teamId === "me"}>
                <MenuSidebarButton selected={props.select === 5} name="팀" to={`/teams/${teamId}`}>
                    <svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                        <path
                            d="M96-192v-92q0-26 12.5-47.5T143-366q54-32 114.5-49T384-432q66 0 126.5 17T625-366q22 13 34.5 34.5T672-284v92H96Zm648 0v-92q0-42-19.5-78T672-421q39 8 75.5 21.5T817-366q22 13 34.5 34.5T864-284v92H744ZM384-480q-60 0-102-42t-42-102q0-60 42-102t102-42q60 0 102 42t42 102q0 60-42 102t-102 42Zm336-144q0 60-42 102t-102 42q-8 0-15-.5t-15-2.5q25-29 39.5-64.5T600-624q0-41-14.5-76.5T546-765q8-2 15-2.5t15-.5q60 0 102 42t42 102Z"/>
                    </svg>
                </MenuSidebarButton>
                <MenuSidebarButton selected={props.select === 6} name="인수인계" to={`/teams/${teamId}/handovers`}>
                    <svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                        <path
                            d="M636-457q-70-64-114.5-107T452-637.5Q427-668 417.5-691t-9.5-48q0-56 34.5-90.5T533-864q30 0 56 12.5t47 35.5q19-23 45.5-35.5T739-864q55 0 90 35t35 90q0 24-8 47t-32.5 54q-24.5 31-69 74T636-457ZM551-96l-287-79v-353h72l266 95q38 14 54.5 37t16.5 60H568q-32 0-53.5-4T471-352l-23-9-17 49 31 10q28 9 50.5 11.5T568-288h224q29 0 50.5 19.5T864-216L551-96ZM48-96v-432h144v432H48Z"/>
                    </svg>
                </MenuSidebarButton>
                <MenuSidebarButton selected={props.select === 7} name="보고서" to={`/teams/${teamId}/reports`}>
                    <svg xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 -960 960 960" width="20">
                        <path
                            d="M680-330q-50 0-85-35t-35-85q0-50 35-85t85-35q50 0 85 35t35 85q0 50-35 85t-85 35ZM440-50v-116q0-21 10-39.5t28-29.5q28-17 58-29.5t62-20.5l82 106 82-106q32 8 61.5 20.5T881-235q18 11 28.5 29.5T920-166v116H440Zm-60-116v46H180q-24.75 0-42.375-17.625T120-180v-600q0-24.75 17.625-42.375T180-840h600q24.75 0 42.375 17.625T840-780v247q-23-45-66-71t-94-26v-50H280v60h341q-38 14-67.5 43T510-510H280v60h220q0 31 10 59t28 51H280v60h157q-27 20-42 50.5T380-166Z"/>
                    </svg>
                </MenuSidebarButton>
            </MenuPanel>
        </Component>
    );
}

export default MenuSidebar;

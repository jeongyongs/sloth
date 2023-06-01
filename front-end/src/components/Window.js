import React from "react";
import styles from '../styles/components/Window.module.css';
import axios from "axios";
import {useNavigate} from "react-router-dom";

function Window(props) {

    const navigate = useNavigate();

    // 윈도우 넓이 설정
    let width = styles.window1;
    if (props.width === "1") {
        width = styles.window1;
    }
    if (props.width === "2") {
        width = styles.window2;
    }
    if (props.width === "3") {
        width = styles.window3;
    }
    // 윈도우 높이 설정
    let height = styles.panel_div1;
    if (props.height === "full") {
        height = styles.panel_div0;
    }
    if (props.height === "1") {
        height = styles.panel_div1;
    }

    // 수락 버튼
    function clickHandler(e) {
        console.log(props.token);
        axios.post(`/api/team/accept?inviteId=${e.target.id}`, {}, {
            headers: {
                Authorization: `bearer ${props.token}`
            }
        })
            .then(response => {
                props.setData(response.data);
            })
            .catch(e => {
                navigate("/member/auth");
            });
    }

    // 빈 리스트
    if (props.list.length === 0) {
        return (
            <div className={width}>
                <p className={styles.panel_p}>{props.title}</p>
                <div className={height}>
                    <p className={styles.panel_p}>목록이 비었습니다.</p>
                </div>
            </div>
        );
    }

    // 팀 초대 목록
    if (props.listType === "invites") {
        return (
            <div className={width}>
                <p className={styles.panel_p}>{props.title}</p>
                <div className={height}>
                    {props.list.map(item => (
                        <div className={styles.article}>
                            <p className={styles.p1}>초대</p>
                            <p>{item.teamName}</p>
                            <p>{item.teamOwner}</p>
                            <p className={styles.p1}>{Math.floor((item.expired - new Date().getTime()) / (1000 * 60 * 60))}시간</p>
                            <p id={item.id} onClick={clickHandler} className={styles.button}>수락</p>
                        </div>
                    ))}
                </div>
            </div>
        );
    }

    // 팀 목록
    if (props.listType === "teams") {
        return (
            <div className={width}>
                <p className={styles.panel_p}>{props.title}</p>
                <div className={height}>
                    {props.list.map(item => (
                        <div className={styles.article}>
                            {item.name}
                        </div>
                    ))}
                </div>
            </div>
        );
    }
    // 그 외
    return (
        <div className={width}>
            <p className={styles.panel_p}>{props.title}</p>
            <div className={height}>
                윈도우 리스트 타입을 재설정 해주십시오.
            </div>
        </div>
    );
}

export default Window;

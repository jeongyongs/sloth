package com.sloth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegratedTest {

    @Autowired
    MockMvc request;

    void printResult(MvcResult result) throws UnsupportedEncodingException {
        result.getResponse().setCharacterEncoding("UTF-8");
        System.out.println();
        System.out.println(result.getResponse().getStatus());
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("통합테스트")
    @Transactional
    @Rollback(value = false)
    void integratedTest() throws Exception {

        // 1. 회원 가입 1
        String ownerName = "team_owner";
        MultiValueMap<String, String> signupParams1 = new LinkedMultiValueMap<>();
        signupParams1.add("username", ownerName);
        signupParams1.add("password", ownerName);
        signupParams1.add("name", "팀장");
        signupParams1.add("email", "team_owner@sloth.com");
        signupParams1.add("phone", "000-0000-0000");
        MvcResult signupResult1 = request.perform(post("/api/member").params(signupParams1)).andReturn();

        // 2. 회원 가입 2
        String memberName = "team_member";
        MultiValueMap<String, String> signupParams2 = new LinkedMultiValueMap<>();
        signupParams2.add("username", memberName);
        signupParams2.add("password", memberName);
        signupParams2.add("name", "팀원");
        signupParams2.add("email", "team_member@sloth.com");
        signupParams2.add("phone", "000-0000-0000");
        MvcResult signupResult2 = request.perform(post("/api/member").params(signupParams2)).andReturn();

        // 3. 로그인
        MultiValueMap<String, String> loginParams1 = new LinkedMultiValueMap<>();
        loginParams1.add("username", ownerName);
        loginParams1.add("password", ownerName);
        MvcResult loginResult1 = request.perform(post("/api/auth").params(loginParams1)).andReturn();
        String jwt1 = loginResult1.getResponse().getContentAsString().split("\"")[3];

        // 4. 팀 생성
        String teamName = "슬로스";
        MvcResult createTeamResult =
                request.perform(post("/api/team").param("name", teamName)
                        .header("Authorization", "bearer " + jwt1)).andReturn();

        // 5. 팀 초대
        MultiValueMap<String, String> inviteParams = new LinkedMultiValueMap<>();
        inviteParams.add("name", teamName);
        inviteParams.add("username", memberName);
        MvcResult inviteTeamResult = request.perform(post("/api/team/invite").params(inviteParams)
                .header("Authorization", "bearer " + jwt1)).andReturn();

        // 6. 로그인
        MultiValueMap<String, String> loginParams2 = new LinkedMultiValueMap<>();
        loginParams2.add("username", memberName);
        loginParams2.add("password", memberName);
        MvcResult loginResult2 = request.perform(post("/api/auth").params(loginParams2)).andReturn();
        String jwt2 = loginResult2.getResponse().getContentAsString().split("\"")[3];

        // 7. 초대 확인
        MvcResult checkInvite = request.perform(get("/api/team/invite")
                .header("Authorization", "bearer " + jwt2)).andReturn();
        String id = checkInvite.getResponse().getContentAsString().split("\"")[10].split(":")[1].split(",")[0];

        // 8. 초대 수락
        MvcResult acceptResult = request.perform(post("/api/team/accept").param("inviteId", id)
                .header("Authorization", "bearer " + jwt2)).andReturn();

        // 9. 가입한 팀 조회
        MvcResult getJoinedTeamResult = request.perform(get("/api/team")
                .header("Authorization", "bearer " + jwt2)).andReturn();

        // 출 력
        printResult(loginResult1);
        printResult(createTeamResult);
        printResult(inviteTeamResult);
        printResult(loginResult2);
        printResult(checkInvite);
        printResult(acceptResult);
        printResult(getJoinedTeamResult);
    }
}

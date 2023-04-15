package com.sloth.team;

import com.sloth.authentication.service.JwtService;
import com.sloth.member.domain.Member;
import com.sloth.member.repository.MemberRepository;
import com.sloth.team.domain.InviteTeam;
import com.sloth.team.domain.Team;
import com.sloth.team.repository.InviteTeamRepository;
import com.sloth.team.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    InviteTeamRepository inviteTeamRepository;

    @Test
    @DisplayName("팀 생성 컨트롤러 테스트")
    @Transactional
    void createTeamTest() throws Exception {

        // given
        Member member = Member.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        memberRepository.save(member);
        String jwt = jwtService.createJwt("jeongyongs");

        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/team")
                        .param("name", "Sloth").header("Authorization", "bearer " + jwt))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("name: Sloth has been created."))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andReturn();

        // then
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    @Test
    @DisplayName("가입한 팀 조회 컨트롤러 테스트")
    @Transactional
    void getJoinedTeamTest() throws Exception {

        // given
        Member member = Member.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        memberRepository.save(member);
        String jwt = jwtService.createJwt("jeongyongs");
        mockMvc.perform(post("/api/team")
                .param("name", "Sloth").header("Authorization", "bearer " + jwt));

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/team")
                        .header("Authorization", "bearer " + jwt))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("success"))
                .andReturn();

        // then
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    @Test
    @DisplayName("팀 초대 보내기 테스트")
    @Transactional
    void inviteTeamTest() throws Exception {

        // given
        Member member1 = Member.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        Member member2 = Member.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        memberRepository.save(member1);
        String jwt = jwtService.createJwt("jeongyongs");
        Team team = Team.builder().name("Sloth").owner(member1).build();
        teamRepository.save(team);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", team.getName());
        params.add("username", member2.getUsername());

        // when
        mockMvc.perform(post("/api/team/invite").params(params).header("Authorization", "bearer " + jwt))
                .andDo(print())

                // then
                .andExpect(jsonPath("$.message").value("Invite complete."))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("팀 초대 확인 테스트")
    @Transactional
    void checkAllInviteByMemberTest() throws Exception {

        // given
        Member member = Member.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        memberRepository.save(member);
        Team team = Team.builder().name("Sloth").owner(member).build();
        teamRepository.save(team);
        String jwt = jwtService.createJwt(member.getUsername());
        InviteTeam inviteTeam =
                InviteTeam.builder().member(member).team(team).expired(new Date(System.currentTimeMillis()).getTime() + 1000 * 60 * 60 * 24).build();
        inviteTeamRepository.save(inviteTeam);

        // when
        MvcResult result = mockMvc.perform(get("/api/team/invite").header("Authorization", "bearer " + jwt))
                .andDo(print())

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("팀 초대 수락 테스트")
    @Transactional
    void acceptInviteTest() throws Exception {

        // given
        Member member = Member.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        memberRepository.save(member);
        Team team = Team.builder().name("Sloth").owner(member).build();
        teamRepository.save(team);
        String jwt = jwtService.createJwt(member.getUsername());
        InviteTeam inviteTeam =
                InviteTeam.builder().member(member).team(team).expired(new Date(System.currentTimeMillis()).getTime() + 1000 * 60 * 60 * 24).build();
        inviteTeamRepository.save(inviteTeam);

        // when
        MvcResult result = mockMvc.perform(post("/api/team/accept")
                        .param("inviteId", String.valueOf(inviteTeam.getId())).header("Authorization", "bearer " + jwt))
                .andDo(print())

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}

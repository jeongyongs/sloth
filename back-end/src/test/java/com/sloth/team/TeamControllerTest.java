package com.sloth.team;

import com.sloth.global.auth.service.JwtService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
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
    UserRepository userRepository;
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
        User user = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        userRepository.save(user);
        String jwt = jwtService.create("jeongyongs");

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
        User user = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        userRepository.save(user);
        String jwt = jwtService.create("jeongyongs");
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
        User user1 = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        User user2 = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        userRepository.save(user1);
        String jwt = jwtService.create("jeongyongs");
        Team team = Team.builder().name("Sloth").owner(user1).build();
        teamRepository.save(team);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", team.getName());
        params.add("username", user2.getUsername());

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
        User user = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        userRepository.save(user);
        Team team = Team.builder().name("Sloth").owner(user).build();
        teamRepository.save(team);
        String jwt = jwtService.create(user.getUsername());
        InviteTeam inviteTeam =
                InviteTeam.builder().user(user).team(team).expired(new Date(System.currentTimeMillis()).getTime() + 1000 * 60 * 60 * 24).build();
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
        User user = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        userRepository.save(user);
        Team team = Team.builder().name("Sloth").owner(user).build();
        teamRepository.save(team);
        String jwt = jwtService.create(user.getUsername());
        InviteTeam inviteTeam =
                InviteTeam.builder().user(user).team(team).expired(new Date(System.currentTimeMillis()).getTime() + 1000 * 60 * 60 * 24).build();
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

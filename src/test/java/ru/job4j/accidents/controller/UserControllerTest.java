package ru.job4j.accidents.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.AuthorityService;
import ru.job4j.accidents.service.SimpleAuthorityService;
import ru.job4j.accidents.service.SimpleUserService;
import ru.job4j.accidents.service.UserService;

import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc

class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService users;
    @MockBean
    private AuthorityService authorities;

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users/register"));
    }

    @Test
    void thenPostRegSaveUserThenReturnRegPageErrorAndArgumentCaptureEquals() throws Exception {
        var errorMsg = "Пользователь с таким логином уже существует";
        var authority = new Authority(1, "ROLE_USER");
        var user = new User(0, "username", "password", authority, true);
        when(authorities.findByAuthority(authority.getAuthority())).thenReturn(authority);
        when(users.create(user)).thenReturn(Optional.empty());

        this.mockMvc.perform(post("/reg")
                        .param("username", user.getUsername())
                        .param("password", user.getPassword()))
                .andDo(print())
                .andExpect(model().attribute("error", errorMsg))
                .andExpect(status().isOk())
                .andExpect(view().name("users/register"));

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(users).create(argument.capture());

        assertThat(argument.getValue().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void whenPostRegSaveUserThenShouldReturnLoginPage() throws Exception {
        var authority = new Authority(1, "ROLE_USER");
        var user = new User(0, "username", "password", authority, true);
        when(authorities.findByAuthority(authority.getAuthority())).thenReturn(authority);
        when(users.create(user)).thenReturn(Optional.of(user));

        this.mockMvc.perform(post("/reg")
                        .param("username", user.getUsername())
                        .param("password", user.getPassword()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(users).create(argument.capture());

        assertThat(argument.getValue().getUsername()).isEqualTo(user.getUsername());
    }
}
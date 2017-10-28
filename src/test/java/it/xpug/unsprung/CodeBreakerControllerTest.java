package it.xpug.unsprung;

import it.xpug.unsprung.domain.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CodeBreakerController.class)
public class CodeBreakerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameRepository gameRepository;

    @Test
    public void createNewGame() throws Exception {
        when(gameRepository.createNewGame()).thenReturn(new Game(123L));

        mockMvc.perform(post("/hangout/game"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("gameId", is("7b")))
        ;
    }

    @Test
    public void findGame() throws Exception {
        Game game = new Game(15L);
        when(gameRepository.findGame(15L)).thenReturn(Optional.of(game));

        mockMvc.perform(get("/hangout/game/f"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("gameId", is("f")))
        ;
    }

    @Test
    public void cannotFindGame() throws Exception {
        when(gameRepository.findGame(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/hangout/game/777"))
                .andExpect(status().isNotFound())
        ;
    }

}

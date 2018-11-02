package it.xpug.frameworkless.hangman.domain;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

public class WordListTest {

    @Test
    public void randomWord() throws Exception {
        WordList list = new WordList(new Random(123));
        assertThat(list.getRandomWord(), is("sunny"));
    }

}
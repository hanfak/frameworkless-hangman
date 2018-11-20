package it.xpug.frameworkless.hangman.domain;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PrisonerTest {
	Prisoner prisoner = new Prisoner("someword");

	@Test
	public void newPrisoner() {
		assertEquals(18, prisoner.getGuessesRemaining());
		assertEquals("help", prisoner.getState());
		assertEquals("********", prisoner.getMaskedWord());
	}

	@Test
	public void miss() throws Exception {
		prisoner.guess("a");
		assertEquals(17, prisoner.getGuessesRemaining());
		assertEquals(set("a"), prisoner.getMisses());
		assertEquals(set(), prisoner.getHits());
		assertEquals("********", prisoner.getMaskedWord());
	}

	@Test
	public void hit() throws Exception {
		prisoner.guess("o");
		assertEquals(17, prisoner.getGuessesRemaining());
		assertEquals(set(), prisoner.getMisses());
		assertEquals(set("o"), prisoner.getHits());
		assertEquals("*o***o**", prisoner.getMaskedWord());
	}

	@Test
	public void lose() throws Exception {
		miss18times();
		assertEquals("lost", prisoner.getState());
	}

	private void miss18times() {
		for (int i = 0; i < 18; i++) {
			prisoner.guess("a");
		}
	}

	@Test
	public void win() throws Exception {
		prisoner.guess("s");
		prisoner.guess("o");
		prisoner.guess("m");
		prisoner.guess("e");
		prisoner.guess("w");
		prisoner.guess("r");
		prisoner.guess("d");
		assertEquals("rescued", prisoner.getState());
	}

	@Test
	public void noFurtherGuesses() throws Exception {
		miss18times();
		prisoner.guess("x");
		assertEquals(set("a"), prisoner.getMisses());
		assertEquals(0, prisoner.getGuessesRemaining());
	}

    private Set<Guess> set(String ... letters) {
		Set<Guess> result = new HashSet<>();
		for (String string : letters) {
			result.add(new Guess(string));
		}
		return result;
	}
}

package it.xpug.frameworkless.hangman.web;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

class InvalidGuessException extends ClientError {
    public InvalidGuessException(String guess) {
        super(SC_BAD_REQUEST, String.format("Guess '%s' invalid: must be a single letter", guess));
    }
}

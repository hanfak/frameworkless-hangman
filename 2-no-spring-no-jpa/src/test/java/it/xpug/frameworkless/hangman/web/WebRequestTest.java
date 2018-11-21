package it.xpug.frameworkless.hangman.web;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static it.xpug.frameworkless.hangman.web.HttpMethod.GET;
import static it.xpug.frameworkless.hangman.web.HttpMethod.OTHER;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

public class WebRequestTest {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    WebRequest webRequest = new WebRequest(httpServletRequest);

    @Test
    public void getPath() throws Exception {
        when(httpServletRequest.getRequestURI()).thenReturn("/foo/bar");

        assertThat(webRequest.getPath(), is("/foo/bar"));
    }

    @Test
    public void getMethod() throws Exception {
        when(httpServletRequest.getMethod()).thenReturn("get");

        assertThat(webRequest.getMethod(), is(GET));
    }

    @Test
    public void getMethod_notGetOrPost() throws Exception {
        when(httpServletRequest.getMethod()).thenReturn("SOMETHING ELSE");

        assertThat(webRequest.getMethod(), is(OTHER));
    }

    @Test
    public void getOptionalParameter_found() throws Exception {
        when(httpServletRequest.getParameter("found")).thenReturn("here I am");

        assertThat(webRequest.getOptionalParameter("found"), is(Optional.of("here I am")));
    }

    @Test
    public void getOptionalParameter_notFound() throws Exception {
        assertThat(webRequest.getOptionalParameter("non-existing"), is(Optional.empty()));
    }

    @Test
    public void getMandatoryParameter_found() throws Exception {
        when(httpServletRequest.getParameter("x")).thenReturn("hello");

        assertThat(webRequest.getMandatoryParameter("x"), is("hello"));
    }

    @Test(expected = BadRequestException.class)
    public void getMandatoryParameter_notFound() throws Exception {
        when(httpServletRequest.getParameter("y")).thenReturn(null);

        webRequest.getMandatoryParameter("y");
    }

    @Test
    public void getIpAddress() throws Exception {
        when(httpServletRequest.getRemoteAddr()).thenReturn("2.3.4.5");

        assertThat(webRequest.getIpAddress(), is("2.3.4.5"));
    }

    @Test
    public void getForwardedFor() throws Exception {
        when(httpServletRequest.getHeader("x-forwarded-for")).thenReturn("abc");

        assertThat(webRequest.getForwardedFor(), is("abc"));
    }
}
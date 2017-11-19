package ch.e3ag.test.smtp.server.ui;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ch.e3ag.test.smtp.server.smtp.MessageListener;

/**
 * @author Alexey Merezhin
 * @since 5/4/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RestServiceTest {
    @Autowired
    MessageListener listener;
    @Autowired
    RestService restService;

    @Before
    public void setUp() throws Exception {
        listener.messages.add(new Email());
        listener.messages.add(new Email());
        listener.messages.add(new Email());
        for (int i = 0; i < listener.messages.size(); i++) {
            listener.messages.get(i).id = i+1;
            listener.messages.get(i).sender = "from1@test.com";
            listener.messages.get(i).recipients = new String[]{"to1@test.com"};
            listener.messages.get(i).user = "to1@test.com";
            listener.messages.get(i).subject = "subj " + i;
            listener.messages.get(i).body = "body " + i;
        }
        listener.messages.get(1).recipients = new String[]{"to1@test.com", "to2@test.com"};
        listener.messages.get(1).user = "to2@test.com";
        listener.messages.get(2).sender = "from2@test.com";
    }

    @After
    public void tearDown() throws Exception {
        listener.messages.clear();
    }

    @Test
    public void listEmails() throws IOException {
        Integer[] output = restService.listEmails().stream().map(e -> e.id).toArray(Integer[]::new);
        assertEquals(Arrays.asList(1,2,3), Arrays.asList(output));
    }

    @Test
    public void listEmailsForUser() throws IOException {
        assertEquals(Arrays.asList(1,3),
                Arrays.asList(restService.listEmailsForUser("to1@test.com")));
        assertEquals(Arrays.asList(2),
                Arrays.asList(restService.listEmailsForUser("to2@test.com")));
    }

    @Test
    public void getEmail() throws IOException {
        Email result = restService.getEmail("2");
        assertEquals(2, result.id);
        assertEquals("from1@test.com", result.sender);
        assertArrayEquals(new String[]{"to1@test.com", "to2@test.com"}, result.recipients);
        assertEquals("subj 1", result.subject);
        assertEquals("body 1", result.body);
    }

}

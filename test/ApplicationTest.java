import org.junit.*;
import play.test.*;
import play.mvc.Http.*;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertStatus(302, response);
        assertEquals("/client", response.getHeader("Location"));
        response = GET(response.getHeader("Location"), true);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }
    
}
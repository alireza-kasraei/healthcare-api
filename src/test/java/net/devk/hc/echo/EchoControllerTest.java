package net.devk.hc.echo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import net.devk.hc.HealthCareApplication;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EchoControllerTest {

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class).addClasses(EchoController.class, HealthCareApplication.class);
	}

	@ArquillianResource
	private URL base;

	@Test
	@RunAsClient
	public void testGet() throws IOException {

		Map<String, String> response = ClientBuilder.newClient()
				.target(URI.create(new URL(base, "api/echo/sample").toExternalForm()))
				.request(MediaType.APPLICATION_JSON).get(Map.class);

		assertEquals("sample", response.get("echo"));
	}
}
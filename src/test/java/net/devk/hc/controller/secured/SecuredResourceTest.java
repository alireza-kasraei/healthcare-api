package net.devk.hc.controller.secured;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.test.FluentTestsHelper;

import net.devk.hc.HealthCareApplication;
import net.devk.hc.model.Patient;
import net.devk.hc.patient.PatientService;
import net.devk.hc.secured.Message;
import net.devk.hc.secured.Resource;

@RunWith(Arquillian.class)
public class SecuredResourceTest {

	@ArquillianResource
	private URL base;

	private static Client client;
	private static FluentTestsHelper testsHelper;

	static {
		try {
			testsHelper = new FluentTestsHelper("http://192.168.5.2:8080/auth", "admin", "admin", "master", "admin-cli",
					"master").init().importTestRealm("/quickstart-realm.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		try {
//			TestsHelper.appName = "test-demo";
//			TestsHelper.baseUrl = "http://localhost:18080/test-demo";
//			importTestRealm("admin", "admin", "/quickstart-realm.json");
//			createDirectGrantClient();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private static Package[] getCorePackages() {
		return new Package[] { Patient.class.getPackage(), PatientService.class.getPackage() };
	}

	@Deployment(name = "test-deployment.war", testable = false)
	public static Archive<?> createTestArchive() throws IOException {

		File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
				.withTransitivity().asFile();

		return ShrinkWrap.create(WebArchive.class)// .addPackages(false, Filters.exclude(".*Test.*"), getCorePackages())
				.addClass(HealthCareApplication.class)// .addClass(EntityManagerProducer.class)
				.addClass(Resource.class).addClass(Message.class).addAsLibraries(files)
				// .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				// .addAsResource("META-INF/apache-deltaspike.properties")
				.addAsResource("META-INF/beans.xml")
				// .addAsResource("META-INF/load.sql")
				.addAsWebInfResource("test-keycloak.json", "keycloak.json")
				.setWebXML(new File("src/main/webapp", "WEB-INF/web.xml"));
	}

	@BeforeClass
	public static void init() {
		client = ClientBuilder.newClient();
	}

	@AfterClass
	public static void cleanUp() throws IOException {
		testsHelper.deleteRealm("quickstart");
	}

//	@Ignore
//	@Test
//	public void testSecuredEndpoint() {
//		try {
//			Assert.assertTrue(TestsHelper.returnsForbidden("api/secured"));
//		} catch (IOException e) {
//			Assert.fail();
//		}
//	}
//
//	@Ignore
//	@Test
//	public void testAdminEndpoint() {
//		try {
//			Assert.assertTrue(TestsHelper.returnsForbidden("api/admin"));
//		} catch (IOException e) {
//			Assert.fail();
//		}
//	}

	@Test
	public void testPublicEndpoint() throws URISyntaxException {
		try {
//			Assert.assertFalse(TestsHelper.returnsForbidden("api/public"));
//			String token = testsHelper.getToken();
//			System.out.println("token************************************************** " + token);
//			Assert.assertFalse(testsHelper.returnsForbidden("api/public"));
			WebTarget target = client.target(URI.create(new URL(base, "api/public").toURI().toString()));
			Assert.assertEquals(200, target.request().get().getStatus());
		} catch (IOException e) {
			Assert.fail();
		}
	}

	@Test
	public void testSecuredEndpoint() throws URISyntaxException {
		try {
//			Assert.assertFalse(TestsHelper.returnsForbidden("api/public"));
			String token = testsHelper.getToken();
			System.out.println("token************************************************** " + token);
//			Assert.assertFalse(testsHelper.returnsForbidden("api/public"));
			WebTarget target = client.target(URI.create(new URL(base, "api/secured").toURI().toString()));
			Assert.assertEquals(401, target.request().get().getStatus());
		} catch (IOException e) {
			Assert.fail();
		}
	}

	@Test
	public void testAdminEndpoint() throws URISyntaxException {
		try {
//			Assert.assertFalse(TestsHelper.returnsForbidden("api/public"));
//			String token = testsHelper.getToken();
//			System.out.println("token************************************************** " + token);
//			Assert.assertFalse(testsHelper.returnsForbidden("api/public"));
			WebTarget target = client.target(URI.create(new URL(base, "api/admin").toURI().toString()));
			Assert.assertEquals(401, target.request().get().getStatus());
		} catch (IOException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testSecuredEndpointWithToken() throws URISyntaxException {
		try {
			String token = testsHelper.getToken();
//			System.out.println("token************************************************** " + token);
//			Assert.assertFalse(testsHelper.returnsForbidden("api/public"));
			WebTarget target = client.target(URI.create(new URL(base, "api/secured").toURI().toString()));
			Assert.assertEquals(401, target.request().get().getStatus());
		} catch (IOException e) {
			Assert.fail();
		}
	}

//	@Ignore
//	@Test
//	public void testSecuredEndpointWithAuth() {
//		try {
//			Assert.assertTrue(TestsHelper.testGetWithAuth("api/secured",
//					TestsHelper.getToken("alice", "password", TestsHelper.testRealm)));
//		} catch (IOException e) {
//			Assert.fail();
//		}
//	}
//
//	@Ignore
//	@Test
//	public void testAdminEndpointWithAuthButNoRole() {
//		try {
//			Assert.assertFalse(TestsHelper.testGetWithAuth("api/admin",
//					TestsHelper.getToken("alice", "password", TestsHelper.testRealm)));
//		} catch (IOException e) {
//			Assert.fail();
//		}
//	}
//
//	@Ignore
//	@Test
//	public void testAdminEndpointWithAuthAndRole() {
//		try {
//			Assert.assertTrue(TestsHelper.testGetWithAuth("api/admin",
//					TestsHelper.getToken("test-admin", "password", TestsHelper.testRealm)));
//		} catch (IOException e) {
//			Assert.fail();
//		}
//	}
}
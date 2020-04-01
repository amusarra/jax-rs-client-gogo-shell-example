package it.dontesta.labs.liferay.jaxrs.client.gogoshell;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Antonio Musarra
 */
@Component(
	property = {
		"osgi.command.function=getGitHubUserInfo", "osgi.command.scope=gitHub"
	},
	service = Object.class
)
public class JAXRSClientExampleCommands {

	public void getGitHubUserInfo(String username) throws Exception {
		Client client = ClientBuilder.newBuilder(
		).newClient();

		WebTarget webTarget = client.target(_BASE_REST_GITHUB_URI);

		webTarget = webTarget.path(_USER_INFO_PATH + username);

		Invocation.Builder builder = webTarget.request(
		).accept(
			MediaType.APPLICATION_JSON
		);

		String responseAsString = builder.get(String.class);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			responseAsString);

		System.out.println("Repository URL: " + jsonObject.get("repos_url"));
		System.out.println("Name: " + jsonObject.get("name"));

		if (_log.isInfoEnabled()) {
			_log.info(jsonObject);
		}
	}

	private static final String _BASE_REST_GITHUB_URI =
		"https://api.github.com";

	private static final String _USER_INFO_PATH = "/users/";

	private static final Log _log = LogFactoryUtil.getLog(
		JAXRSClientExampleCommands.class);

}
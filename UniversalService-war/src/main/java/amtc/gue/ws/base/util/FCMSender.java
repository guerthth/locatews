package amtc.gue.ws.base.util;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Class resonsible for sending FCM messages to registered clients
 * 
 * @author Thomas
 *
 */
public class FCMSender {
	private static final Logger log = Logger.getLogger(FCMSender.class.getName());

	private static final String PROJECT_ID = "universalservice-dcafd";
	private static final String BASE_URL = "https://fcm.googleapis.com";
	private static final String FCM_SEND_ENDPOINT = "/v1/projects/" + PROJECT_ID + "/messages:send";

	private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
	private static final String[] SCOPES = { MESSAGING_SCOPE };
	
	private static final String TITLE = "FCM Notification";
	private static final String BODY = "Notification from FCM";
	public static final String MESSAGE_KEY = "message";

	/**
	 * Send notification message to FCM for delivery to registered devices 
	 * 
	 * @throws IOException
	 */
	public static void sendCommonMessage() throws IOException {
		JsonObject message = buildNotificationMessage();
		log.info("FCM request body for message: " + prettyPrint(message));
		sendMessage(message);
	}

	/**
	 * Construct body of a notification message request
	 * 
	 * @return JSON of notification message
	 */
	private static JsonObject buildNotificationMessage() {
		JsonObject jNotification = new JsonObject();
		jNotification.addProperty("title", TITLE);
		jNotification.addProperty("body", BODY);

		JsonObject jMessage = new JsonObject();
		jMessage.add("notification", jNotification);
		jMessage.addProperty("topic", "DBChange");

		JsonObject jFcm = new JsonObject();
		jFcm.add(MESSAGE_KEY, jMessage);

		return jFcm;
	}

	/**
	 * Pretty print JSON object
	 * 
	 * @param jsonObject
	 *            JsonObject to pretty print
	 * @return the formatted JsonObject
	 */
	private static String prettyPrint(JsonObject jsonObject) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(jsonObject) + "/n";
	}

	/**
	 * Send request to FCM message using HTTP
	 * 
	 * @param fcmMessage
	 *            the json message that should be sent
	 * @throws IOException 
	 */
	private static void sendMessage(JsonObject fcmMessage) throws IOException {
		HttpURLConnection connection = getConnection();
		connection.setDoOutput(true);
	    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
	    outputStream.writeBytes(fcmMessage.toString());
	    outputStream.flush();
	    outputStream.close();

	    int responseCode = connection.getResponseCode();
	    if (responseCode == 200) {
	      String response = inputstreamToString(connection.getInputStream());
	      System.out.println("Message sent to Firebase for delivery, response:");
	      System.out.println(response);
	    } else {
	      System.out.println("Unable to send message to Firebase:");
	      String response = inputstreamToString(connection.getErrorStream());
	      System.out.println(response);
	    }
	}

	/**
	 * Create HttpURLConnection that can be used for both retrieving and
	 * publishing.
	 * 
	 * @return HttpURLConnection
	 * @throws IOException
	 */
	private static HttpURLConnection getConnection() throws IOException {
		URL url = new URL(BASE_URL + FCM_SEND_ENDPOINT);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
		httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
		return httpURLConnection;
	}

	/**
	 * Retrieve a valid access token that can be used to authorize requests to
	 * the FCM REST API
	 * 
	 * @return Access token
	 * @throws  IOException
	 */
	private static String getAccessToken() throws IOException {
		GoogleCredential googleCredential = GoogleCredential
		        .fromStream(new FileInputStream("service-account.json"))
		        .createScoped(Arrays.asList(SCOPES));
		    googleCredential.refreshToken();
		    return googleCredential.getAccessToken();
	}
	
	/**
	 * Read contents of InputStream into String.
	 * 
	 * @param inputStream InputStream to read
	 * @return String containing contents of InputStream
	 */
	private static String inputstreamToString(InputStream inputStream) {
		StringBuilder stringBuilder = new StringBuilder();
	    Scanner scanner = new Scanner(inputStream);
	    while (scanner.hasNext()) {
	      stringBuilder.append(scanner.nextLine());
	    }
	    scanner.close();
	    return stringBuilder.toString();
	}
}

package operations;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.*;

public class TwitterSetup {
	protected final static Twitter twitter = new TwitterFactory().getInstance();
	
	private static String CONSUMER_KEY = "nINjrNyRwMQ77tdyqXIWkeLE5";
	private static String CONSUMER_SECRET = "QfGb5A5OcDfCLCN7b9AUVQ0awDliUAQprC3YoNPjDU51T2WIf0";
	
	private static String ACCESS_TOKEN;
	private static String ACCESS_TOKEN_SECRET;
	private static AccessToken accessToken;
	private static RequestToken requestToken;
	
	public TwitterSetup() {
		twitter.setOAuthConsumer("nINjrNyRwMQ77tdyqXIWkeLE5", "QfGb5A5OcDfCLCN7b9AUVQ0awDliUAQprC3YoNPjDU51T2WIf0");
	}
	
	public static void setup() {
		twitter.setOAuthConsumer("nINjrNyRwMQ77tdyqXIWkeLE5", "QfGb5A5OcDfCLCN7b9AUVQ0awDliUAQprC3YoNPjDU51T2WIf0");
		accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		twitter.setOAuthAccessToken(accessToken);
	}
	
	public static Twitter getTwitter() {
		return twitter;
	}
	
	public static AccessToken getToken(String pin) throws TwitterException {
		accessToken = null;
		if (pin.length() > 0) {
			accessToken = getTwitter().getOAuthAccessToken(getRequestToken(), pin);
		} else {
			accessToken = getTwitter().getOAuthAccessToken();
		}
		return accessToken;
	}
	
	public static RequestToken getRequestToken() {
		if (requestToken == null) {
			try {
				requestToken = getTwitter().getOAuthRequestToken();
			} catch (TwitterException e) {
				System.out.println("Internal Error");
			}
		}
		return requestToken;
	}
	
	public static boolean hasAccessToken() {
		return accessToken != null;
	}
	
	
}
	

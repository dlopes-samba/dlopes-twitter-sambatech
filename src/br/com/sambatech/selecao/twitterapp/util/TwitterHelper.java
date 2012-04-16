package br.com.sambatech.selecao.twitterapp.util;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterHelper {

	private static final Logger log = Logger.getLogger(TwitterHelper.class.getName());
	
	private static TwitterHelper instance = null;
	
	private ResourceBundle properties;
	
	/**
	 * Constructor
	 */
	private TwitterHelper() {
		properties = AppResourceBundle.getResourceBundle("twitter4j");
	}
	
	/**
	 * Get TwitterHelp single instance
	 * 
	 * @return
	 */
	public static TwitterHelper getInstance() {
		if (instance == null) {
			instance = new TwitterHelper();
		}
		return instance;
	}
	
	/**
	 * Update application twitter account status
	 * 
	 * @param status
	 * @return True if update is successful and false otherwise
	 */
	public boolean updateStatus(String status) {
		String accessTokenStr = properties.getString("oauth.accessToken");
		String accessTokenSecretStr = properties.getString("oauth.accessTokenSecret");
		
		log.log(Level.INFO, "AccessToken " + accessTokenStr);
		log.log(Level.INFO, "AccessTokenSecret " + accessTokenSecretStr);
		
		AccessToken accessToken = new AccessToken(accessTokenStr, accessTokenSecretStr);
		Twitter appTwitter = new TwitterFactory().getInstance(accessToken);

		try {
			Status result = appTwitter.updateStatus(status);
			log.log(Level.INFO, "Successfully updated status with message [" + result.getText() + "].");
		} catch (TwitterException e) {
			log.log(Level.SEVERE, "Error updating Twitter status. Cause: " + e.getErrorMessage());
			return false;
		}
		return true;
	}
}

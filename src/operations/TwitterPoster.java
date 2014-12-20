package operations;

import java.util.ArrayList;

import twitter4j.*;
public class TwitterPoster {
	protected ArrayList<String> postArr;
	
	public TwitterPoster(String text) {
		postArr = createCharArray(text);
	}
		
	public ArrayList<String> createCharArray(String text) {
		ArrayList<String> arr = new ArrayList<>();
		while(text.length() > 140) {
			arr.add(text.substring(0, 140));
			text = text.substring(140);
		}
		if (text.length() > 0) {
			arr.add(text);
		}
		return arr;
	}
	
	public long post() {
		Twitter twitter = TwitterSetup.getTwitter();
		long headID = 0;
		
		try {
			headID = twitter.updateStatus(postArr.get(0)).getId();
			postArr.remove(0);
			if(postArr.size() > 0) {
				for (String post : postArr) {
					StatusUpdate stat = new StatusUpdate(post);
					stat.setInReplyToStatusId(headID);
					headID = twitter.updateStatus(stat).getId();
				}
			}
		} catch (TwitterException e) {
			System.out.println("Cannot update status");
			e.printStackTrace();
		}
		
		return headID;
	}
	
	public static void main(String[] args) {
		TwitterSetup.setup();
		TwitterPoster tweet = new TwitterPoster("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		tweet.post();
	}


}

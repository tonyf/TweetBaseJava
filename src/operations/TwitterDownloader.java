package operations;
import java.util.ArrayList;

import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;


public class TwitterDownloader {
	private long headID;
	private ArrayList<Status> tweets;

	public TwitterDownloader(long headID) {
		//headID is the last twitter in the reply chain
		//it is the head Node
		this.headID = headID;
		tweets = new ArrayList<>();
		
	}
	
	public String getTweets() {
		String tweetString = "";
		Twitter twitter = TwitterSetup.getTwitter();
		
		try {
			Status last = twitter.showStatus(headID);
			tweets.add(last);
			boolean wasReply = last.getInReplyToStatusId() > 0;
			while (wasReply) {
				Status replyStatus = twitter.showStatus(last.getInReplyToStatusId());
				tweets.add(replyStatus);
				if (replyStatus.getInReplyToStatusId() > 0) {
					replyStatus = twitter.showStatus(replyStatus.getInReplyToStatusId());
				} else {
					replyStatus = null;
				}
			}
			
		} catch (TwitterException e) {
			System.out.println("Tweet retrieval error");
			e.printStackTrace();
		}

		while (tweets.size() > 0) {
			Status stat = tweets.remove(tweets.size() -1);
			tweetString += stat.getText();
		}
		
		return tweetString;
	}
	
	// Duplicates code from TwitterPoster to test logic
	public static void main(String[] args) {
		TwitterSetup.setup();
		Twitter twitter = TwitterSetup.getTwitter();
		long headID = 0;
		ArrayList<String> postArr = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			postArr.add("" + i);
		}
		
		try {
			headID = twitter.updateStatus(postArr.get(0)).getId();
			postArr.remove(0);
			if(postArr.size() > 1) {
//				long replyID = headID;
				for (String post : postArr) {
					StatusUpdate stat = new StatusUpdate(post);
					stat.setInReplyToStatusId(headID);
//					replyID = twitter.updateStatus(stat).getId();
					headID = twitter.updateStatus(stat).getId();
				}
			}
		} catch (TwitterException e) {
			System.out.println("Cannot update status");
		}
		
		TwitterDownloader dl = new TwitterDownloader(headID);
		System.out.println(dl.getTweets());
		
	}
	
	
}

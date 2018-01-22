package chikokutter;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterSource extends Thread {
	public static final int QUERY_INTERVAL = 10000;
	public static final int WAIT_UPON_RATE_LIMIT_EXCEED = 60000;

	@Override
	public void run() {
		Twitter twitter = new TwitterFactory().getInstance();
		long sinceID = 0;
		while (true) {
			try {
				Query query = new Query(
						composeQuery(Line.defaultLines()) 
						//"‹‰¤ü OR ¬“c‹} OR ç‘ã“cü OR “Œ‰¡ü OR ‘åˆä’¬ü OR •›“sSü"
//						"¬“c‹} OR ˆä‚Ì“ªü OR ’†‰›ü"
						);
				query.setResultType(Query.RECENT);
				query.setCount(100);
				query.setSinceId(sinceID);
				QueryResult result;
				
//				System.err.println("runs a query: " + query);
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				System.err.printf("obtained %d tweets.\n", tweets.size());
				model.purgeBeforeAdd();
				// iterate from the last to the first element; i.e., from older
				// to newer
				for (int index = tweets.size()-1; index>=0; index--) {
					Status tweet = tweets.get(index);
					
//					Object[] row = new Object[] { tweet.getText(),
//							tweet.getUser().getScreenName(),
//							tweet.getCreatedAt() };
					model.addTweet(tweet);
					sinceID = Math.max(sinceID, tweet.getId());
				}
			} catch (TwitterException te) {
				te.printStackTrace();
				System.out.println("Failed to search tweets: "
						+ te.getMessage());
				try {
					Thread.sleep(WAIT_UPON_RATE_LIMIT_EXCEED);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
//			System.out.println("pause before issuing next query");
			try {
				Thread.sleep(QUERY_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private String composeQuery(Line[] defaultLines) {
		StringBuffer q = new StringBuffer();
		for (Line l : defaultLines) {
			q.append(" OR ");
			q.append(l.getLineName());
		}
		q.delete(0, 4);
		return q.toString();
	}

	private StreamModel model;


	public TwitterSource(StreamModel model) {
		this.model = model;
	}


	public static TweetSourceFactory getFactory() {
		return new TweetSourceFactory() {

			@Override
			public Thread makeInstance(TableStreamModel model) {
				return new TwitterSource(model);
			}
		};
	}

}

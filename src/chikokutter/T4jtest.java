package chikokutter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class T4jtest {

	
	 /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param args
	 * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
//        if (args.length < 1) {
//            System.out.println("java twitter4j.examples.search.SearchTweets [query]");
//            System.exit(-1);
//        }
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            Query query = new Query("¬“c‹} OR ç‘ã“cü OR “Œ‹} OR “Œ‰¡ü OR ‘åˆä’¬ü OR •›“sSü");
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
//                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                	Calendar c = Calendar.getInstance();
                	c.setTime(tweet.getCreatedAt());
                	System.out.printf(
                			"{\"%s\", \"%s\", new Date(%d,%d,%d,%d,%d,%d)},\n",
                			tweet.getText(), tweet.getUser().getScreenName(), 
                			c.get(Calendar.YEAR)-1900,
                			c.get(Calendar.MONTH),
                			c.get(Calendar.DATE),
                			c.get(Calendar.HOUR_OF_DAY),
                			c.get(Calendar.MINUTE),
                			c.get(Calendar.SECOND)
                			);
                }
                Thread.sleep(10);
            } while ((query = result.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }
    
}

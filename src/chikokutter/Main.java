package chikokutter;
import javax.swing.JFrame;
//TODO UI to change query (based on the route you go)
//TODO count and show the number of tweets for each keyword
//TODO show a tweet of long text
//TODO smooth scroll
//TODO keyword based filtering
public class Main {

	static TweetSourceFactory tweetSourceFactory = TwitterSource.getFactory();
	
	public static void main(String[] args) {
		parseOptions(args);
		
		JFrame testFrame = new JFrame("Chikokutter");
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		StreamPanel streamPanel = new StreamPanel();
		testFrame.getContentPane().add(streamPanel);

		tweetSourceFactory.makeInstance(streamPanel.getModel()).start();

		testFrame.setSize(1000, 200);
		testFrame.setVisible(true);
	}

	private static void parseOptions(String[] args) {
		for (String arg: args) {
			if (arg.equals("--replay")) {
				tweetSourceFactory = TwitterReplayer.getFactory(false);
			} else if (arg.equals("--replay=fast")) {
					tweetSourceFactory = TwitterReplayer.getFactory(true);
			} else {
				throw new Error(String.format("invalid option: %s", arg));
			}
		}
		
	}

}

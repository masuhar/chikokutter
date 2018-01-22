package chikokutter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class TwitterReplayer extends Thread implements StreamModel, Runnable {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
//		convert();
		record();
	}

	private static void record() throws FileNotFoundException, IOException {
		TwitterReplayer replayer = new TwitterReplayer();
		TwitterSource source = new TwitterSource(replayer);
		source.start();
		replayer.waitTweets(50);
		source.stop();
		replayer.dump();
	}
//	private static void convert() throws FileNotFoundException, IOException {
//		List<TweetI> log = load();
//		List<Status> newLog = new ArrayList();
//		for (TweetI tweet: log) 
//			newLog.add(tweet.getStatus());
//		dumpNewLog(newLog);
//	}

	private static void dumpNewLog(List<Status> log) throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("tweetsNew.dump")));
		out.writeObject(log);
		out.close();
	}

	private synchronized void waitTweets(int count) {
		try {
			while (true) {
				if (log.size() >= count) {
					break;
				}
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private synchronized void dump() throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("tweets.dump")));
		out.writeObject(log);
		out.close();
	}

	List<Status> log = new ArrayList<Status>();
	private StreamModel model;
	private boolean fast;

	@Override
	public synchronized void addTweet(Status row) {
		log.add(row);
		System.out.format("received %d tweets\n", log.size());
		notifyAll();
	}

	TwitterReplayer(StreamModel model, boolean fast) {
		this.model = model;
		this.fast = fast;
		log = load();
	}

	public TwitterReplayer() {
		// constructor for dumping
	}

	private static List<Status> load() {
		ObjectInputStream out = null;
		try {
			out = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream("tweets.dump")));
			return (List<Status>) out.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		System.err.format("Replaying with %d tweets.\n", log.size());
		for (Status tweet : log) {
			model.addTweet(tweet);
			if (!fast) {
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
		}
		System.err.format("Replaying done.\n", log.size());
	}

	public static TweetSourceFactory getFactory(final boolean fast) {
		return new TweetSourceFactory() {

			@Override
			public Thread makeInstance(TableStreamModel model) {
				return new TwitterReplayer(model, fast);
			}
			
		};
	}

	@Override
	public void purgeBeforeAdd() {
		//???
	}

}

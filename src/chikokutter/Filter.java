package chikokutter;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Filter {
	
	protected static final Set<String> NG_NAMES = new FileStringSet("NG_NAMES.txt");

	private static final Pattern BOT_PATTERN = Pattern.compile(".*([bBＢｂ][oOＯｏ][tTｔＴ]|ハウジング|不動産|賃貸|部屋探し|イー・モバイル|エステート)");

	static Filter[] FILTERS = { new Filter() {
		@Override
		public boolean apply(TweetI tweet) {
			for (Line l : Line.values()) {
				applyLineName(tweet, l);
			}
			return false;
		}

		private void applyLineName(TweetI tweet, Line line) {
			//TODO use Regexp class to avoid string matching twice
			if (tweet.getText().indexOf(line.getLineName()) >= 0) {
				tweet.setText(tweet.getText().replace(line.getLineName(),
						"<b>" + line.getLineName() + "</b>"));
				tweet.setColor(line.getColor());
			}
		}
	}, new Filter() {
		@Override
		public boolean apply(TweetI tweet) {
			boolean retweet = tweet.isRetweet();
			if (retweet)
				tweet.setColor(Color.gray);
			return retweet;
		}
	},
	new Filter() {		

		@Override
		public boolean apply(TweetI tweet) {
			//			boolean isBot = tweet.getScreenName().matches(".*bot");
			return checkIfBot(tweet, tweet.getScreenName(), "screen name")
					|| checkIfBot(tweet, tweet.getName(), "name");
	}

		private boolean checkIfBot(TweetI tweet, String name, String field) {
			boolean isBot = BOT_PATTERN.matcher(name).find();
			if (isBot) {
				tweet.setColor(Color.gray);
				System.err.printf("filtering by %s %s: %s\n", field, name,tweet.getText());
			}
			return isBot;
		}},

		new Filter() {
			Pattern p = Pattern.compile("https?://|山のホテル|I'm at |つり革におにぎり|電話の中だから電車|神奈川をあまり|トマトいるんだけど|あなたは今から働きに行くんだろう|妹にしか見れない|パリーカンカン|<html>[（〔［｛〈《「『【]|あるある|レスキューナウニュース|それぞれの好き度|原因が謎すぎ|目黒線をご利用ください。なお、目黒線の再開の見込みはありません|神々の争いにより.*が遅延|ピカチュウ.*小田急|お金を嫌いな人は");
			Map<String,Integer> hitCount = new HashMap<>();
			@Override
			public boolean apply(TweetI tweet) {
				String t = tweet.getText();
				Matcher m = p.matcher(t);
				if (m.find(0)) {
					String matched = m.group(0);
					int count = 1;
					if (!hitCount.containsKey(matched)) 
						hitCount.put(matched,1);
					else
						hitCount.put(matched, count = 1+ hitCount.get(matched));
					System.err.printf("%d'th filtering with %s in %s.\n", count, matched, t);
					return true;
				}
				return false;
			}
			
		},

		
		new Filter() {
		private static final String delayPrefix = "<font color=red>";
		private static final String delaySuffix = "</font>";

		@Override
		public boolean apply(TweetI tweet) {
			//if (tweet.getText())
			Pattern p = Pattern.compile("遅延|遅れ|事故|人身|故障|止ま(っ|る|り|ら|れ)|見合わせ|停止");
			//TODO clean up this code
			String t = tweet.getText();
			StringBuffer ts = new StringBuffer();
			Matcher m = p.matcher(t);
			while (m.find(0)) {
				ts.append(t.substring(0,m.start()));
				ts.append(delayPrefix);
				ts.append(m.group());
				ts.append(delaySuffix);
				t = t.substring(m.end());
				m = p.matcher(t);
			}
			ts.append(t);
			tweet.setText(ts.toString());
			return false;
		}
		
	},
	
	new Filter () {
		@Override
		public boolean apply(TweetI tweet) {
			String name = tweet.getScreenName();
			boolean inNgNames = NG_NAMES.contains(name);
			if (inNgNames)
				System.err.printf("@%s's tweet '%s' is filtered.\n",
						name, tweet.getText());
			return inNgNames;
		}
	}
	};

	public abstract boolean apply(TweetI tweet);

	public static void filterThisID(String screenName) {
		NG_NAMES.add(screenName);
	}

}

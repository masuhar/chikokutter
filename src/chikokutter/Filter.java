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

	private static final Pattern BOT_PATTERN = Pattern.compile(".*([bB�a��][oO�n��][tT���s]|�n�E�W���O|�s���Y|����|�����T��|�C�[�E���o�C��|�G�X�e�[�g)");

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
			Pattern p = Pattern.compile("https?://|�R�̃z�e��|I'm at |��v�ɂ��ɂ���|�d�b�̒�������d��|�_�ސ�����܂�|�g�}�g����񂾂���|���Ȃ��͍����瓭���ɍs���񂾂낤|���ɂ�������Ȃ�|�p���[�J���J��|<html>[�i�k�m�o�q�s�u�w�y]|���邠��|���X�L���[�i�E�j���[�X|���ꂼ��̍D���x|�������䂷��|�ڍ����������p���������B�Ȃ��A�ڍ����̍ĊJ�̌����݂͂���܂���|�_�X�̑����ɂ��.*���x��|�s�J�`���E.*���c�}|�����������Ȑl��");
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
			Pattern p = Pattern.compile("�x��|�x��|����|�l�g|�̏�|�~��(��|��|��|��|��)|�����킹|��~");
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

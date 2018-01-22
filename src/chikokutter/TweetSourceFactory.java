package chikokutter;

public interface TweetSourceFactory {

	Thread makeInstance(TableStreamModel model);

}

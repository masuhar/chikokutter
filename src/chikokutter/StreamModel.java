package chikokutter;

import twitter4j.Status;

public interface StreamModel {

	void addTweet(Status tweet);

	void purgeBeforeAdd();

}

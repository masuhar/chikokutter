package chikokutter;

import java.awt.Color;
import java.util.Date;

import twitter4j.Status;

public interface TweetI {

	long getId();

	String getText();

	String getScreenName();

	String getName();

	Date getTime();

	Color getColor();
//	Status getStatus();

	boolean isRetweet();

	void setColor(Color color);

	void setText(String newString);
	
	boolean isHidden();
}

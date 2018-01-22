package chikokutter;

import java.awt.Color;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

public class TweetWrapper implements TweetI, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4924949219050416498L;

	private Status status;
	private Color color;
	private String text;
	private boolean hidden;

	public TweetWrapper(Status status) {
		this.status = status;
		this.text = "<html>" + status.getText() + "</html>";
		this.hidden = false;
		for (Filter filter : Filter.FILTERS)
			hidden = filter.apply(this) || hidden;
//		this.color = status.isRetweet() ? Color.gray : Color.white;
	
	}

	@Override
	public long getId() {
		return status.getId();
	}

	@Override
	public String getText() {
		return this.text;
	}

	@Override
	public String getScreenName() {
		return status.getUser().getScreenName();
	}

	@Override
	public Date getTime() {
		return status.getCreatedAt();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public boolean isRetweet() {
		return status.isRetweet();
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void setText(String newString) {
		this.text = newString;
	}

	@Override
	public boolean isHidden() {
		return hidden;
	}

	@Override
	public String getName() {
		return status.getUser().getName();
	}

//	@Override
//	public Status getStatus() {
//		return status;
//	}


}

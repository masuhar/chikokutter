package chikokutter;
import java.util.Date;

public class TwitterMock extends Thread {
	@Override
	public void run() {
		int half = datas.length/2;
		// add last (older) half of tweets in a batch
		for (int i=half; i < datas.length; i++)
			model.addTweet(datas[i]);
		// add first (newer) half of tweets one by one with a random interval
		for (int i=half-1; i>=0; i--) {
			try {
				Thread.sleep((int)(Math.random()*3000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			model.addTweet(datas[i]);
		}
	}

	private TableStreamModel model;
	public static Object[][] datas= new Object[][] {
	{"下北沢遠いと思ったけど（遠いけど）乗換一回ですむから楽だな．渋谷よりも駅が複雑じゃない分まし．新宿は小田急見つけられなくて迷ったけど", "shippo_kawaii", makeDate(2013,11,22,0,39,20)},
	{"渋谷ＮＨＫホール　◆JR山手線　原宿駅より徒歩１０分、各線渋谷駅より徒歩１５分　◆キャパシティ：３,６７７　◆ロッカー：クローク有、最寄駅に有　◆周辺施設：渋谷東武ホテル、渋谷エクセルホテル東急、こどもの城ホテル等", "event_hall_bot", makeDate(2013,11,22,0,39,9)},
	{"小田急終電接続しないの？？", "orangehake", makeDate(2013,11,22,0,39,8)},
	{"東京横浜間のアクセスはJRに京急、東急と色々あって便利ですな", "Linspeed", makeDate(2013,11,22,0,38,50)},
	{"ほぉ、小田急線に臨時準急なるものが出てるのね。忘年会シーズンだからかしら。", "T_songthrush", makeDate(2013,11,22,0,38,47)},
	{"繁華街 西口 ： 池袋駅西口 駅前から池袋西口公園にかけての地下は 有楽町線 および 副都心線 となっている。 http://t.co/ocbe93jQDI", "IkebukuroWatch", makeDate(2013,11,22,0,38,44)},
	{"小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ小田急遅延しろ", "kirurili_bot", makeDate(2013,11,22,0,38,40)},
	{"#love#art #花くまゆうさく#小田急#マナーを守ってみんな快適\nだいすき！花くま先生！ @ 喜多見駅2番ホーム http://t.co/ebKgg4QF3s", "Chamico2011", makeDate(2013,11,22,0,38,32)},
	{"タダ茹でて塩で食べるだけで旨味がわかるんです。全身で旨さを実感できるのは市場直送で低農薬で作った野菜をだからです。 #小田急　山手", "YsaiOisi_bot", makeDate(2013,11,22,0,38,26)},
	{"東横線でサンタの格好した女性が僕にピッタリ引っ付く状況になってるのはクリスマスプレゼントでしょうか???", "xxxGataiGaxxx", makeDate(2013,11,22,0,37,59)},
	{"あるある30:渋谷行きの多くは渋谷で副都心線ホームではなく東横線のホーム(4番線)に到着する", "TY_suki", makeDate(2013,11,22,0,37,58)},
	{"本日も東急電鉄をご利用いただきありがとうございます", "tokyu_8500_bot", makeDate(2013,11,22,0,37,50)},
	{"あーほんと横浜線と小田急線の相性悪すぎていらいらするーなんで15分で帰れるところを1時間かけなきゃいけないんだよーもっと電車出せよー", "1992_yukamiy", makeDate(2013,11,22,0,37,48)},
	{"小田急最高", "Ishidalf", makeDate(2013,11,22,0,37,46)},
	};
	
	private static Date makeDate(int year, int month, int day, int hour, int minutes, int second) {
		return new Date(year-1900, month, day, hour, minutes, second);
	}

	TwitterMock(TableStreamModel model){
		this.model = model;
	}
	
}

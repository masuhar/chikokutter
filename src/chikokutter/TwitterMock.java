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
	{"���k�򉓂��Ǝv�������ǁi�������ǁj�抷���ł��ނ���y���ȁD�a�J�����w�����G����Ȃ����܂��D�V�h�͏��c�}�������Ȃ��Ė���������", "shippo_kawaii", makeDate(2013,11,22,0,39,20)},
	{"�a�J�m�g�j�z�[���@��JR�R����@���h�w���k���P�O���A�e���a�J�w���k���P�T���@���L���p�V�e�B�F�R,�U�V�V�@�����b�J�[�F�N���[�N�L�A�Ŋ�w�ɗL�@�����ӎ{�݁F�a�J�����z�e���A�a�J�G�N�Z���z�e�����}�A���ǂ��̏�z�e����", "event_hall_bot", makeDate(2013,11,22,0,39,9)},
	{"���c�}�I�d�ڑ����Ȃ��́H�H", "orangehake", makeDate(2013,11,22,0,39,8)},
	{"�������l�Ԃ̃A�N�Z�X��JR�ɋ��}�A���}�ƐF�X�����ĕ֗��ł���", "Linspeed", makeDate(2013,11,22,0,38,50)},
	{"�ق��A���c�}���ɗՎ����}�Ȃ���̂��o�Ă�̂ˁB�Y�N��V�[�Y�������炩����B", "T_songthrush", makeDate(2013,11,22,0,38,47)},
	{"�ɉ؊X ���� �F �r�܉w���� �w�O����r�ܐ��������ɂ����Ă̒n���� �L�y���� ����� ���s�S�� �ƂȂ��Ă���B http://t.co/ocbe93jQDI", "IkebukuroWatch", makeDate(2013,11,22,0,38,44)},
	{"���c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x�����돬�c�}�x������", "kirurili_bot", makeDate(2013,11,22,0,38,40)},
	{"#love#art #�Ԃ��܂䂤����#���c�}#�}�i�[������Ă݂�ȉ��K\n���������I�Ԃ��ܐ搶�I @ �쑽���w2�ԃz�[�� http://t.co/ebKgg4QF3s", "Chamico2011", makeDate(2013,11,22,0,38,32)},
	{"�^�_䥂łĉ��ŐH�ׂ邾���Ŏ|�����킩���ł��B�S�g�Ŏ|���������ł���͎̂s�꒼���Œ�_��ō������؂�������ł��B #���c�}�@�R��", "YsaiOisi_bot", makeDate(2013,11,22,0,38,26)},
	{"�������ŃT���^�̊i�D�����������l�Ƀs�b�^�������t���󋵂ɂȂ��Ă�̂̓N���X�}�X�v���[���g�ł��傤��???", "xxxGataiGaxxx", makeDate(2013,11,22,0,37,59)},
	{"���邠��30:�a�J�s���̑����͏a�J�ŕ��s�S���z�[���ł͂Ȃ��������̃z�[��(4�Ԑ�)�ɓ�������", "TY_suki", makeDate(2013,11,22,0,37,58)},
	{"�{�������}�d�S�������p�����������肪�Ƃ��������܂�", "tokyu_8500_bot", makeDate(2013,11,22,0,37,50)},
	{"���[�ق�Ɖ��l���Ə��c�}���̑����������Ă��炢�炷��[�Ȃ��15���ŋA���Ƃ����1���Ԃ����Ȃ��Ⴂ���Ȃ��񂾂�[�����Ɠd�ԏo����[", "1992_yukamiy", makeDate(2013,11,22,0,37,48)},
	{"���c�}�ō�", "Ishidalf", makeDate(2013,11,22,0,37,46)},
	};
	
	private static Date makeDate(int year, int month, int day, int hour, int minutes, int second) {
		return new Date(year-1900, month, day, hour, minutes, second);
	}

	TwitterMock(TableStreamModel model){
		this.model = model;
	}
	
}

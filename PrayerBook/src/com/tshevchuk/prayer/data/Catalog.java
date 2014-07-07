package com.tshevchuk.prayer.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.text.TextUtils;
import android.util.SparseArray;

import com.tshevchuk.prayer.Utils;

public class Catalog {
	private static final String SRC_BIBLIA = "Біблія, переклад Івана Хоменка";
	private static final String SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA = "Молитовник «Прийдіте поклонімся»";
	private static final String SRC_MOLYTVOSLOV = "Молитвослов. Видавництво ОО. Василіян, Рим-Торонто, 1990.";
	private static final String SRC_DODATOK_KATEKHYZMU_2012 = "Додаток до Катехизму УГКЦ «Христос – наша Пасха» 2012 року";
	private static final String SRC_PRO_SPOVID = "Про Сповідь. о. Порфирій В. Шумило, ЧСВВ, бр. Пімен І. Коневич, ЧСВВ http://osbm-buchach.org.ua/knygy/pro-spovid.html";
	private static final String SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH = "Уроки Біблії http://bible-lessons.in.ua/molutva/dljavagitnux.html";
	private static final String SRC_AKAFISTY_MOLYTVA = "МОЛИТВА - Акафіст http://molytva.at.ua/index/akafist/0-293";
	private static final String SRC_AKAFISTY_KYRIOS = "Християнський портал Кіріос - Акафісти http://kyrios.org.ua/spirituality/akafisti.html";
	private static final String SRC_VERVYTSYA_MOLYTVA = "МОЛИТВА - Вервиці http://molytva.at.ua/index/vervici/0-46";
	private static final String SRC_MOLEBNI_KYRIOS = "Християнський портал Кіріос - Молебні http://kyrios.org.ua/spirituality/molebni.html";
	private static final String SRC_MOLEBNI_MOLYTVA = "МОЛИТВА - Молебні http://molytva.at.ua/index/molebni/0-274";
	private static final String SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA = "МОЛИТОВНИК військовослужбовця «Молись і служи!». Департамент Патріаршої курії Української Греко-Католицької Церкви у справах душпастирства силових структур України. Київ. 2013 рік";
	private static final String SRC_TREBNYK_LITURHIJNI_PEREKLADY = "Літургійні переклади УКГЦ - Требник http://ugcc-littexts-ukr.blogspot.com/p/blog-page_7695.html";
	private static final String SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY = "Літургійні переклади УКГЦ - Требник http://ugcc-littexts-ukr.blogspot.com/p/blog-page_7695.html на основі Требник Львів 2001";
	private static final String SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY = "Літургійні переклади УКГЦ - Требник http://ugcc-littexts-ukr.blogspot.com/p/blog-page_7695.html на основі Требник Львів 2012";
	private static final String SRC_PISNI_KYRIOS = "Християнський портал Кіріос - Слова і ноти http://kyrios.org.ua/music/words-and-notes.html";
	private static final String SRC_MOLYTVY_KYRIOS = "Християнський портал Кіріос - Молитви http://kyrios.org.ua/spirituality/prayer.html";
	private static final String SRC_LITURHIYA_KYRIOS = "Християнський портал Кіріос - Чин священної і Божественної Літургії святого Івана Золотоустого http://kyrios.org.ua/spirituality/bogosluzhinnja/1198-bozhestvenna-liturgija.html";

	public static final int ID_SCHODENNI_MOLYTVY = 1;
	public static final int ID_CALENDAR = 5;
	public static final int ID_FOR_EVERY_DAY = 85;
	public static final int ID_FOR_EVERY_OCASION = 164;
	public static final int ID_RECENT_SCREENS = 400;

	private static final int NEXT_ID_TO_ADD = 702;

	private List<MenuItemBase> topMenu = new ArrayList<MenuItemBase>();
	private SparseArray<MenuItemBase> menuItemsByIds = new SparseArray<MenuItemBase>();

	{
		MenuItemSubMenu menu = new MenuItemSubMenu(0, "top");
		menu.addSubItem(new MenuItemOftenUsed(400));
		menu.html(ID_SCHODENNI_MOLYTVY, "Щоденні молитви",
				"molytvy-schodenni.html", SRC_DODATOK_KATEKHYZMU_2012);
		menu.addSubItem(addMolytvyRizniPotreby());
		menu.addSubItem(addMolytvyNaKozhenDen());
		menu.addSubItem(addMolytvyRizni());

		menu.addSubItem(new MenuItemCalendar(ID_CALENDAR));

		MenuItemSubMenu psalmy = new MenuItemSubMenu(6, "Псалми");
		addPsaloms(psalmy);
		menu.addSubItem(psalmy);

		menu.addSubItem(addAkafisty());
		menu.addSubItem(addVervytsya());

		menu.addSubItem(addBohosluzhinnya());
		menu.addSubItem(addKatekhPravdy());
		menu.addSubItem(addProSpovidIPrychastya());

		menu.addSubItem(addPisni());

		topMenu = menu.getSubItems();

		verifyUniqueId();
	}

	private MenuItemSubMenu addMolytvyRizni() {
		MenuItemSubMenu menu = new MenuItemSubMenu(565, "Молитви різні");
		menu.addSubItem(addMolytvyDoSvyatykh());
		menu.addSubItem(addMolytvyNaProslavuUkrSluhBozhykh());
		menu.addSubItem(addMolytvyMytropolytaAndreya());
		menu.addSubItem(addMolytvyVijskovykhFormuvan());
		menu.html(2, "Ранішні молитви", "molytvy/molytvy-ranishni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(700, "Ранішні молитви (1)",
				"molytvy/molytvy-ranishni-1.html", SRC_MOLYTVY_KYRIOS);
		menu.html(3, "Вечірні молитви", "molytvy/molytvy-vechirni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(701, "Вечірні молитви (1)",
				"molytvy/molytvy-vechirni-1.html", SRC_MOLYTVY_KYRIOS);
		menu.text(594, "Молитва Іларіона, митрополита Київського",
				"molytvy/ilariona.html", SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		return menu;
	}

	private MenuItemSubMenu addMolytvyMytropolytaAndreya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(582,
				"Молитви митрополита Андрея");
		menu.text(583, "Приречення вірної служби Ісусові Христові",
				"molytvy/mytropolyta-andreya/sluzhby-isusovi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(584, "Молитва про божу мудрість",
				"molytvy/mytropolyta-andreya/bozhu-mudrist.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(585, "Інша молитва про божу мудрість",
				"molytvy/mytropolyta-andreya/bozhu-mudrist-insha.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(586, "Молитва за український нарід",
				"molytvy/mytropolyta-andreya/ukr-narid.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(587, "Молитва про кращу долю українського народу",
				"molytvy/mytropolyta-andreya/dolyu-ukr-narodu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(588, "Інша молитва за український нарід",
				"molytvy/mytropolyta-andreya/ukr-narid-insha.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(589, "Молитва до Пресвятої Богоматері",
				"molytvy/mytropolyta-andreya/presv-bohomateri.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(590, "Молитва за українську родину",
				"molytvy/mytropolyta-andreya/ukr-rodynu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(591, "Молитва до св. Йосифа за родину",
				"molytvy/mytropolyta-andreya/sv-josypha-za-rodynu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(592, "Молитва про добрих українських священиків",
				"molytvy/mytropolyta-andreya/dobrykh-svyaschennykiv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(593, "Молитва про ласки для священиків",
				"molytvy/mytropolyta-andreya/lasky-dlya-svyaschennykiv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addMolytvyNaProslavuUkrSluhBozhykh() {
		MenuItemSubMenu menu = new MenuItemSubMenu(576,
				"Молитви на прославу українських слуг Божих");
		menu.text(577, "Молитва за прославу митрополита Андрея Шептицького",
				"molytvy/na-proslavu-ukr-sluh-bozhykh/a-sheptytskoho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(578, "Молитва за прославу єпископа Павла Ґойдича",
				"molytvy/na-proslavu-ukr-sluh-bozhykh/p-gojdycha.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(579, "Молитва про заступництво отця Климентія Шептицького",
				"molytvy/na-proslavu-ukr-sluh-bozhykh/k-sheptytskoho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(580, "Молитва за прославу сестри Софронії Ерделі",
				"molytvy/na-proslavu-ukr-sluh-bozhykh/s-erdeli.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(581, "Молитва за прославу сестри Йосафати Гордашевськоі",
				"molytvy/na-proslavu-ukr-sluh-bozhykh/j-hordashevskoji.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addMolytvyDoSvyatykh() {
		MenuItemSubMenu menu = new MenuItemSubMenu(566, "Молитви до святих");
		menu.text(567, "Молитва до святого Володимира",
				"molytvy-do-svyatykh/volodymyra.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(568, "Молитва до святої княгині Ольги",
				"molytvy-do-svyatykh/olhy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(569, "Молитва до святих Бориса і Гліба",
				"molytvy-do-svyatykh/borysa-i-hliba.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(570, "Молитва до святого Антонія Печерського",
				"molytvy-do-svyatykh/antoniya-pecherskoho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(571, "Молитва до святого Теодосія Печерського",
				"molytvy-do-svyatykh/teodosiya-pechersokoho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(572, "Молитва до святого Йосафата і мучеників",
				"molytvy-do-svyatykh/josaphata-i-muchenykiv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(573, "Молитва до святого Йосафата",
				"molytvy-do-svyatykh/josaphata.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(574, "Молитва до всіх святих українського народу",
				"molytvy-do-svyatykh/vsikh-svyatykh-ukr-narodu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		return menu;
	}

	private MenuItemSubMenu addTrebnyk() {
		MenuItemSubMenu menu = new MenuItemSubMenu(468, "Требник");
		menu.html(469, "Молитви у перший день, коли жінка породила дитину",
				"trebnyk/persh-den-porodyla.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(471, "Молитва для знаменування дитяти у 8-ий день",
				"trebnyk/dytya-8j-den.html", SRC_TREBNYK_LITURHIJNI_PEREKLADY);
		menu.html(472, "Молитва над матір’ю у 40-й день",
				"trebnyk/nad-matiryu-40j-den.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(473, "Молитва над жінкою, яка поронила дитя",
				"trebnyk/zhinkoyu-poronyla-dytya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(474, "Чин оглашення перед Хрещенням",
				"trebnyk/chyn-ohlashennya-pered-khreshchennyam.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(475, "Чин Хрещення", "trebnyk/chyn-khreschennya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(476, "Чин Миропомазання", "trebnyk/chyn-myropomazannya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(477, "Поучення як хрестити коротко в небезпеці смерти",
				"trebnyk/pouchennya-khrestyty-v-nebezp-smerti.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(478, "Чин сповіді", "trebnyk/chyn-spovidi.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(479, "Молитва розрішення від клятви",
				"trebnyk/molytva-rozrishennya-vid-klyatvy.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(480, "Чин причастя хворого",
				"trebnyk/chyn-prychastya-khvoroho.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(481, "Чин вінчання", "trebnyk/chyn-vinchannya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(482, "Чин введення в церкву вперше одруженої",
				"trebnyk/chyn-vvedennya-v-tserkvu-vpershe-odruzhenoji.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(483, "Чин вдруге вінчаних",
				"trebnyk/chyn-vdruhe-vinchanykh.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(484, "Чин малого єлеосвячення",
				"trebnyk/chyn-maloho-yeleoosvyachennya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(485, "Чин похорону мирян",
				"trebnyk/chyn-pokhoronu-myryan.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(486, "Чин похорону усопших у світлий тиждень",
				"trebnyk/chyn-pokhoronu-usopshykh.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(487, "Похорон мирських ієреїв",
				"trebnyk/pokhoron-myrskykh-iyereyiv.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(488, "Похорон ієреїв у світлий тиждень",
				"trebnyk/pokhoron-iyereyiv-svitlyj-tyzhden.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(489, "Чин похорону дитини",
				"trebnyk/chyn-pokhoronu-dytyny.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(490, "Чин похорону дитини у світлий тиждень",
				"trebnyk/chyn-pokhoronu-dytyny-svitlyj-tyzhden.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(491, "Чин великої панахиди або парастасу",
				"trebnyk/chyn-velykoyi-panakhydy-abo-parastasu.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(492, "Молитва на всяку неміч",
				"trebnyk/molytva-na-vsyaku-nemich.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(493, "Молитва тому, хто хоче йти в путь",
				"trebnyk/molytva-tomu-khto-jde-v-put.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(494, "Чин благословення дітей перед початком навчання",
				"trebnyk/chyn-blahoslovennya-ditey-pered-navchannyam.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(495,
				"Чин благословення подруж, що прожили разом 25 або 50 років",
				"trebnyk/chyn-blahoslovennya-podruzh-25-50-rokiv.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(496, "Чин великого освячення води святих Богоявлень",
				"trebnyk/chyn-velykoho-osvyachennya-vody.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(497, "Чин малого освячення води",
				"trebnyk/chyn-maloho-osvyachennya-vody.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(534, "Чин малого освячення води у скороченні",
				"trebnyk/chyn-maloho-osvyach-vody-skoroch-1.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(499, "Благословення свічок у празник Стрітення ГНІХ",
				"trebnyk/blahoslovennya-svichok-stritenya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(
				500,
				"Благословення і освячення верби у Квітну неділю",
				"trebnyk/blahoslovennya-i-osvyachennya-verby-kvitnu-nedily.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(501, "Молитва на благословення артоса",
				"trebnyk/blahoslovennya-artosa.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(502,
				"Чин благословення пасок і іншої поживи у Святу Неділю Пасхи",
				"trebnyk/blahoslovennya-pasok.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(
				503,
				"Чин благословення первоплодів у празник Преображення Господнього",
				"trebnyk/blahoslovennya-pervoplodiv-praznyk-preobrazhennya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(504,
				"Чин благословення зілля в день Успення Пресвятої Богородиці",
				"trebnyk/blahoslovennya-zillya-uspennya-bohorodytsi.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(
				505,
				"Чин благословення книги святих євангелій та інших богослужбових книг",
				"trebnyk/blahoslovennya-yevanhelij.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(506, "Чин благословення і освячення богослужбового посуду",
				"trebnyk/blahoslovennya-bohosluzhbovoho-posudu.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(507,
				"Чин благословення покровців для покривання святих дарів",
				"trebnyk/blahoslovennya-pokrovtsiv-pokryvannya-sv-dariv.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(508, "Чин благословення і освячення священичих риз",
				"trebnyk/blahoslovennya-ryz.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(509, "Чин благословення і освячення новопоставленого хреста",
				"trebnyk/blahoslovennya-novopostavlenoho-khresta.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(510, "Чин благословення і освячення ікон - Тройці",
				"trebnyk/blahoslovennya-ikon-trojtsi.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(511, "Чин благословення і освячення ікон - Христа",
				"trebnyk/blahoslovennya-ikon-khrysta.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(512, "Чин благословення і освячення ікон - Богородиці",
				"trebnyk/blahoslovennya-ikon-bohorodytsi.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(513, "Чин благословення і освячення ікон - Святих",
				"trebnyk/blahoslovennya-ikon-svyatykh.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(514, "Чин благословення і освячення церковних хоругов",
				"trebnyk/blahoslovennya-khoruhov.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(515, "Молитва на освячення всякої речі",
				"trebnyk/molytva-osvyach-vsyakoyi-rechi.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(516, "Чин благословення нового дому",
				"trebnyk/blahoslovennya-novoho-domu.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(517, "Чин святої П’ятдесятниці",
				"trebnyk/chyn-pyatdesyatnytsi.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(518, "Чин загального молебня",
				"trebnyk/chyn-zahalnoho-molebnya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(519, "Молебень на святу Пасху",
				"trebnyk/moleben-na-sv-paskhu.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		menu.html(520, "Скорочений чин Єлеопомазання",
				"trebnyk/skorochenyj-chyn-yeleopomazannya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(521, "Чин молебня над хворим",
				"trebnyk/chyn-molebnya-nad-khvorymy.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(522, "Молитва над хворим, що терпить від безсоння",
				"trebnyk/molytva-khvoryj-bezsonnya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(523, "Чин благословення дітей",
				"trebnyk/chyn-blahoslovennya-ditey.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(524, "Молитва над бездітним подружжям",
				"trebnyk/molytva-nad-bezditnym-podruzzhyam.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(525, "Молитва за тих, що творять нам милостиню",
				"trebnyk/molytva-tvoryat-mylostynyu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(526, "Чин благодарення за здійснені прохання",
				"trebnyk/chyn-blahodarennya-zdijsneni-prokhannya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(527, "Молитвоний чин відвідин перед Різдвом",
				"trebnyk/molytovn-chyn-vidvidyn-pered-rizdvom.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(528, "Молитва під час відвідин на Богоявлення",
				"trebnyk/molytva-vidvidyn-bohoyavlennya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(529, "Молитви на початок святої і великої Чотиридесятниці",
				"trebnyk/molytva-na-pochatok-sv-chotyrydesyatnytsi.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(530, "Молитви перед Пасхальним причастям",
				"trebnyk/molytva-pered-sv-paskhalnym-prychastyam.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(531, "Чин введення новопоставленого пароха",
				"trebnyk/chyn-vvedennya-novopostavlenoho-parokha.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(532, "Чин прийняття у церковне братство",
				"trebnyk/chyn-pryjnyattya-u-tserkovne-bratstvo.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(
				533,
				"Чин присяги стриманости від вживання алкогольних напоїв",
				"trebnyk/chyn-prysyahy-strymanosti-vid-vzhyvannya-alkoholyu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(535, "Чин благословення посуду церковного",
				"trebnyk/blahosl-posudu-tserkovnoho.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(536, "Чин благословення дароносиці",
				"trebnyk/chyn-blahoslovennya-daronosytsi.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(537, "Чин благословення нового кивоту",
				"trebnyk/chyn-blahosl-novoho-kyvotu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(
				538,
				"Чин благословення посудин для зберігання мощей святих",
				"trebnyk/chyn-blahoslovennya-posudyn-zberihannya-moschej-svyatykh.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(539, "Благословення кадила",
				"trebnyk/blahoslovennya-kadyla.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(540, "Чин благословення напрестольного хреста",
				"trebnyk/chyn-blahoslovennya-naprestolnoho-khresta.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(541, "Чин благословення хреста нагрудного",
				"trebnyk/chyn-blahoslovennya-khresta-nahrudnoho.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(542, "Благословення храмових світильників або свічок",
				"trebnyk/blahoslovennya-khramovykh-svitylnykiv.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(
				543,
				"Чин благословення і положення першого каменя на оснування церкви",
				"trebnyk/blahoslovennya-pershoho-kamenya-osnuvannya-tserkvy.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(544, "Чин благословення нової церкви",
				"trebnyk/blahoslovennya-novoyi-tserkvy.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(545, "Чин благословення накупольного хреста",
				"trebnyk/blahoslovennya-nakupolnoho-khresta.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(546, "Чин благословення і освячення іконостасу",
				"trebnyk/blahoslovennya-ikonostasu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(547, "Чин благословення нової дзвениці",
				"trebnyk/blahoslovennya-novoyi-dzvenytsi.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(548, "Чин благословення дзвонів",
				"trebnyk/blahoslovennya-dzvoniv.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(549, "Чин благословення нового цвинтаря",
				"trebnyk/blahoslovennya-novoho-tsvyntarya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(550, "Чин примирення оскверненого цвинтаря",
				"trebnyk/chyn-prymyrennya-oskvernenoho-tsvyntarya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(551, "Чин благословення гробниці або надгробку",
				"trebnyk/chyn-blahoslovennya-hrobnytsi-nadhrobku.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(552, "Молитви під час посухи",
				"trebnyk/molytva-pid-chas-posukhy.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(553, "Молитви під час сльоти",
				"trebnyk/molytva-pid-chas-sljoty.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(554, "Чин благословення оснування нового дому",
				"trebnyk/chyn-blahoslovennya-osnuvannya-novoho-domu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(555, "Чин благословення нового дому",
				"trebnyk/chyn-blahoslovennya-novoho-domu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(556, "Чин благословення моста",
				"trebnyk/chyn-blahoslovennya-mosta.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(557, "Чин благословення транспортного засобу",
				"trebnyk/chyn-blahoslovennya-transportnoho-zasobu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(558, "Чин благословення корабля",
				"trebnyk/chyn-blahoslovennya-korablya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(559, "Благословення на копання криниці",
				"trebnyk/blahoslovennya-na-kopannya-krynytsi.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(560, "Чин благословення стада худоби",
				"trebnyk/chyn-blahoslovennya-stada-khudoby.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(561, "Молитва на освячення всякої речі",
				"trebnyk/molytva-na-osvyachennya-vsyakoyi-rechi.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		menu.html(
				562,
				"Йорданські душпастирські відвідини з свяченою водою",
				"trebnyk/jordanski-dushpastyrski-vidvidyny-z-osvyachenoyu-vodoyu.html",
				SRC_TREBNYK_LITURHIJNI_PEREKLADY);
		menu.html(563, "Благословення транспортного засобу",
				"trebnyk/blahoslovennya-transportnoho-zasobu.html",
				SRC_TREBNYK_LITURHIJNI_PEREKLADY);

		return menu;
	}

	private MenuItemSubMenu addMolytvyVijskovykhFormuvan() {
		MenuItemSubMenu menu = new MenuItemSubMenu(403,
				"Молитви представників військових формувань");

		menu.text(404, "Молитва військовослужбовця",
				"rizni-potreby/vijskovykh-formuvan/vijskovosluzhbovtsya.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(405, "Молитва офіцера",
				"rizni-potreby/vijskovykh-formuvan/ofitsera.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(406, "Молитва військовослужбовця повітряних сил",
				"rizni-potreby/vijskovykh-formuvan/povitryanykh-syl.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(407, "Молитва військовослужбовця військово-морських сил",
				"rizni-potreby/vijskovykh-formuvan/vijskovo-morskykh-syl.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(408, "Молитва військовослужбовця сухопутних військ",
				"rizni-potreby/vijskovykh-formuvan/sukhoputnykh.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(409, "Молитва за щасливу подорож",
				"rizni-potreby/vijskovykh-formuvan/schaslyvu-podorozh.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(410,
				"Молитва за капітанів кораблів і тих, що подорожують на водах",
				"rizni-potreby/vijskovykh-formuvan/korabli-vody.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(411, "Молитва військового водія",
				"rizni-potreby/vijskovykh-formuvan/vodiya.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(412, "Молитва артилериста",
				"rizni-potreby/vijskovykh-formuvan/artelerysta.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(413, "Молитва десантника",
				"rizni-potreby/vijskovykh-formuvan/desantnyka.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(414, "Молитва зв’язківця",
				"rizni-potreby/vijskovykh-formuvan/zvazkivtsya.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(415, "Молитва танкіста",
				"rizni-potreby/vijskovykh-formuvan/tankista.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(416, "Молитва курсанта",
				"rizni-potreby/vijskovykh-formuvan/kursanta.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(417, "Молитва рятівника",
				"rizni-potreby/vijskovykh-formuvan/ryativnyka.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(418, "Молитва прикордонника",
				"rizni-potreby/vijskovykh-formuvan/prykordonnyka.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(
				419,
				"Молитва працівника пенітенціарної служби",
				"rizni-potreby/vijskovykh-formuvan/penitentsiarnoyi-sluzhby.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(420, "Молитва правоохоронця",
				"rizni-potreby/vijskovykh-formuvan/pravookhorontsya.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(421, "Молитва за мир",
				"rizni-potreby/vijskovykh-formuvan/za-myr.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(422, "Молитва під час бойових дій",
				"rizni-potreby/vijskovykh-formuvan/bojovykh-dij.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(423, "Молитва перед боєм",
				"rizni-potreby/vijskovykh-formuvan/pered-boyem.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(424, "Коротка молитва під час бою",
				"rizni-potreby/vijskovykh-formuvan/pid-chas-boyu.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(425, "Молитва полоненого",
				"rizni-potreby/vijskovykh-formuvan/polonenoho.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(426, "Молитва пораненого чи хворого воїна",
				"rizni-potreby/vijskovykh-formuvan/poranenoho-khvoroho.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(427, "Молитва за ворогів",
				"rizni-potreby/vijskovykh-formuvan/vorohiv.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(428, "Пом’яник",
				"rizni-potreby/vijskovykh-formuvan/pomyanyk.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(429, "Молитва за тих, що впали в бою",
				"rizni-potreby/vijskovykh-formuvan/vpaly-v-boyu.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(430, "Молитва матері військового",
				"rizni-potreby/vijskovykh-formuvan/materi.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(431, "Молитва дружини військового",
				"rizni-potreby/vijskovykh-formuvan/druzhyny.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(432, "Молитва воїна за родину",
				"rizni-potreby/vijskovykh-formuvan/za-rodynu.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);

		return menu;
	}

	private MenuItemSubMenu addPisni() {
		MenuItemSubMenu menu = new MenuItemSubMenu(7, "Пісні");

		MenuItemSubMenu sm = menu.subMenu(8, "Молитва прослави");
		sm.text(9, "Пісня подяки св. Амвросія, єпископа Медіолянського",
				"pisni/proslavy/podyaky-amvrosiya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(10, "Молитви за нарід");
		sm.text(11, "Боже великий, єдиний",
				"pisni/za-narid/bozhe-velykyj-jedynyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(12, "Боже, вислухай благання",
				"pisni/za-narid/bozhe-vyslukhaj-blahannya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(13, "Боже великий, Творче всесвіту",
				"pisni/za-narid/bozhe-tvorche-vsesvitu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(14, "Коляди");
		sm.text(15, "Бог предвічний", "pisni/kolyady/boh-predvichynyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(16, "Бог ся рождає", "pisni/kolyady/boh-sya-rozhdaye.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(17, "В Вифлеємі днесь Марія",
				"pisni/kolyady/v-vyflejemi-dnes-mariya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(18, "В Вифлеємі новина",
				"pisni/kolyady/v-vyfleyemi-novyna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(19, "Во Вифлеємі нині новина",
				"pisni/kolyady/vo-vyfleyemi-nyni-novyna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(20, "Возвеселімся всі разом нині",
				"pisni/kolyady/vozveselimsya-vsi-razom-nyni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(21, "Вселенная, веселися",
				"pisni/kolyady/vselennaya-veselysya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(22, "Дивная новина", "pisni/kolyady/dyvnaya-novyna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(23, "На небі зірка ясна засяла",
				"pisni/kolyady/na-nebi-zirka-yasna-zasyala.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(24, "Небо і земля", "pisni/kolyady/nebo-i-zemlya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(25, "Нова радість стала",
				"pisni/kolyady/nova-radist-stala.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(26, "Нова радість стала (1)",
				"pisni/kolyady/nova-radist-stala-1.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(27, "По всьому світу", "pisni/kolyady/po-vsjomu-svitu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.html(632, "Ірод-цар за Христом ганявся",
				"pisni/kolyady/irod-tsar-za-khrystom-hanyavsya.html",
				SRC_PISNI_KYRIOS);
		sm.text(633, "А у місті Русалимі",
				"pisni/kolyady/a-u-misti-rusalymi.html", SRC_PISNI_KYRIOS);
		sm.text(634, "Ангел Божий із небес",
				"pisni/kolyady/anhel-bozhyj-iz-nebes.html", SRC_PISNI_KYRIOS);
		sm.text(635, "Ангели, снижайтеся",
				"pisni/kolyady/anhely-snyzhajtesya.html", SRC_PISNI_KYRIOS);
		sm.text(636, "В Вифлеємі тайна",
				"pisni/kolyady/v-vyfleyemi-tajna.html", SRC_PISNI_KYRIOS);
		sm.text(637, "В Вифлеємі у вертепі",
				"pisni/kolyady/v-vyfleyemi-u-vertepi.html", SRC_PISNI_KYRIOS);
		sm.text(638, "В галицькій землі",
				"pisni/kolyady/v-halytskij-zemli.html", SRC_PISNI_KYRIOS);
		sm.text(639, "В глибокій долині",
				"pisni/kolyady/v-hlybokij-dolyni.html", SRC_PISNI_KYRIOS);
		sm.text(640, "В різдвяну ніч", "pisni/kolyady/v-rizdvyanu-nich.html",
				SRC_PISNI_KYRIOS);
		sm.text(641, "Вітай, Ісусе", "pisni/kolyady/vitaj-isuse.html",
				SRC_PISNI_KYRIOS);
		sm.text(643, "Возвеселіться, християни",
				"pisni/kolyady/vozveselitsya-khrystyyany.html",
				SRC_PISNI_KYRIOS);
		sm.text(644, "Діва Марія церкву строїла",
				"pisni/kolyady/diva-mariya-tserkvu-stroyila.html",
				SRC_PISNI_KYRIOS);
		sm.text(645, "Дивная новина днесь ся являє",
				"pisni/kolyady/dyvnaya-novyna-dnes-sya-yavlyaye.html",
				SRC_PISNI_KYRIOS);
		sm.text(646, "Добрий вечір тобі, пане господарю",
				"pisni/kolyady/dobryj-vechir-tobi-pane-hospodaryu.html",
				SRC_PISNI_KYRIOS);
		sm.text(647, "Землю Юдейську", "pisni/kolyady/zemlyu-yudejsku.html",
				SRC_PISNI_KYRIOS);
		sm.text(648, "Коли Христос родився",
				"pisni/kolyady/koly-khrystos-rodyvsya.html", SRC_PISNI_KYRIOS);
		sm.text(649, "Коляда", "pisni/kolyady/kolyada.html", SRC_PISNI_KYRIOS);
		sm.text(650, "Народився Бог на санях",
				"pisni/kolyady/narodyvsya-boh-na-sanyakh.html",
				SRC_PISNI_KYRIOS);
		sm.text(651, "Не плач, Рахиле", "pisni/kolyady/ne-plach-rakhyle.html",
				SRC_PISNI_KYRIOS);
		sm.text(652, "Нині, Адаме", "pisni/kolyady/nyni-adame.html",
				SRC_PISNI_KYRIOS);
		sm.text(653, "Нова радість стала (2)",
				"pisni/kolyady/nova-radist-stala-2.html", SRC_PISNI_KYRIOS);
		sm.text(654, "Ой, як же було ізпрежди віка",
				"pisni/kolyady/oj-yak-zhe-bulo-izprezhdy-vika.html",
				SRC_PISNI_KYRIOS);
		sm.text(655, "Пречистая Діва сина породила",
				"pisni/kolyady/prechystaya-diva-syna-porodyla.html",
				SRC_PISNI_KYRIOS);
		sm.text(656, "Прилетіли ангелята",
				"pisni/kolyady/pryletily-anhelyata.html", SRC_PISNI_KYRIOS);
		sm.text(657, "Радуйся, світ", "pisni/kolyady/radujsya-svit.html",
				SRC_PISNI_KYRIOS);
		sm.text(658, "Свята ніч", "pisni/kolyady/svyata-nich.html",
				SRC_PISNI_KYRIOS);
		sm.text(659, "Спи, Ісусе, спи", "pisni/kolyady/spy-isuse-spy.html",
				SRC_PISNI_KYRIOS);
		sm.text(660, "Сумний Святий Вечір",
				"pisni/kolyady/sumnyj-svyatyj-vechir.html", SRC_PISNI_KYRIOS);
		sm.text(661, "Темненькая нічка",
				"pisni/kolyady/temnenkaya-nichka.html", SRC_PISNI_KYRIOS);
		sm.text(662, "Тиха ніч, свята ніч",
				"pisni/kolyady/tykha-nich-svyata-nich.html", SRC_PISNI_KYRIOS);
		sm.text(663, "Христос родився", "pisni/kolyady/khrystos-rodyvsya.html",
				SRC_PISNI_KYRIOS);
		sm.text(664, "Хто там по дорозі",
				"pisni/kolyady/khto-tam-po-dorozi.html", SRC_PISNI_KYRIOS);
		sm.text(665, "Чудо у Давида домі",
				"pisni/kolyady/chudo-u-davyda-domi.html", SRC_PISNI_KYRIOS);
		sm.text(667, "Шедше тріє царі",
				"pisni/kolyady/shedshe-triye-tsari.html", SRC_PISNI_KYRIOS);
		sm.text(668, "Що то за предиво",
				"pisni/kolyady/scho-to-za-predyvo.html", SRC_PISNI_KYRIOS);
		sm.text(669, "Яка ж радість", "pisni/kolyady/yaka-zh-radist.html",
				SRC_PISNI_KYRIOS);
		sm.text(670, "Ясна зоря", "pisni/kolyady/yasna-zorya.html",
				SRC_PISNI_KYRIOS);

		sm = menu.subMenu(595, "Щедрівки");
		sm.text(596, "Прийшли до вас щедрівці",
				"pisni/schedrivky/pryjshly-schedrivtsi.html", SRC_PISNI_KYRIOS);
		sm.text(597, "Щедрий вечір всім нам",
				"pisni/schedrivky/schedryj-vechir.html", SRC_PISNI_KYRIOS);
		sm.text(598, "Ой чи є, чи нема пан господар вдома",
				"pisni/schedrivky/oj-chy-ye-pan-hospodar.html",
				SRC_PISNI_KYRIOS);
		sm.text(599, "Щедрик, щедрик, щедрівочка",
				"pisni/schedrivky/schedryk.html", SRC_PISNI_KYRIOS);
		sm.text(600, "Ой сивая та і зозулечка",
				"pisni/schedrivky/zozulenka.html", SRC_PISNI_KYRIOS);
		sm.text(601, "Ой, господар, господарочку",
				"pisni/schedrivky/oj-hospodar.html", SRC_PISNI_KYRIOS);
		sm.text(602, "Стоїть явір зелененький", "pisni/schedrivky/yavir.html",
				SRC_PISNI_KYRIOS);
		sm.text(603, "Павочка ходить", "pisni/schedrivky/pavochka-khodyt.html",
				SRC_PISNI_KYRIOS);
		sm.text(671,
				"Ти, Йордане, приготовися",
				"pisni/schedrivky/ty-jordane.html",
				"Українські пісні - Ти, Йордане, приготовися http://www.pisni.org.ua/songs/2170624.html");

		sm = menu.subMenu(28, "Великопосні пісні");
		sm.text(29, "Люди мої, люди",
				"pisni/velykoposni/lyudy-moyi-lyudy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(30, "На Вавилонських ріках",
				"pisni/velykoposni/na-vavylonskykh-rikakh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(31, "Під хрест Твій стаю",
				"pisni/velykoposni/pid-khrest-tvij-stayu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(32, "Поклоняюся, мій Христе",
				"pisni/velykoposni/poklonyayusya-mij-khryste.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(33, "Страдальна Мати", "pisni/velykoposni/stradalna-maty.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(34, "Хрест на плечі накладають",
				"pisni/velykoposni/khrest-na-plechi-nakladayut.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(620, "Ісус перед стражданням",
				"pisni/velykoposni/isus-pered-strazhdannyam.html",
				SRC_PISNI_KYRIOS);
		sm.text(621, "Христос вийшов з храму",
				"pisni/velykoposni/khrystos-vyjshov-z-khramu.html",
				SRC_PISNI_KYRIOS);
		sm.text(622, "Прийди до мене", "pisni/velykoposni/pryjdy-do-mene.html",
				SRC_PISNI_KYRIOS);
		sm.text(623, "По святій горі",
				"pisni/velykoposni/po-svyatij-hori.html", SRC_PISNI_KYRIOS);
		sm.text(624, "Настала ніч", "pisni/velykoposni/nastala-nich.html",
				SRC_PISNI_KYRIOS);
		sm.text(625, "Голгофа. Хрест",
				"pisni/velykoposni/holhopha-khrest.html", SRC_PISNI_KYRIOS);
		sm.text(626, "Голгофа", "pisni/velykoposni/holhopha.html",
				SRC_PISNI_KYRIOS);
		sm.text(627, "Моліться батьки",
				"pisni/velykoposni/molitsya-batky.html", SRC_PISNI_KYRIOS);
		sm.text(628, "Маріє гляди", "pisni/velykoposni/mariye-hlyady.html",
				SRC_PISNI_KYRIOS);
		sm.text(629, "Коли на волю проситься сльоза",
				"pisni/velykoposni/koly-na-volyu-prosytsya-sljoza.html",
				SRC_PISNI_KYRIOS);

		sm = menu.subMenu(35, "Воскресні пісні");
		sm.text(36, "Сей день", "pisni/voskresni/sej-den.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(37, "Согласно заспіваймо",
				"pisni/voskresni/sohlasno-zaspivajmo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(38, "Христос воскрес! Радість з неба",
				"pisni/voskresni/khrystos-voskres.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(39, "Пісні до Христа");
		sm.text(40, "Витай між нами", "pisni/khrysta/vytaj-mizh-namy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(41, "Вірую, Господи", "pisni/khrysta/viruyu-hospody.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(42, "Ісуса в святих тайнах",
				"pisni/khrysta/isusa-v-svyatykh-tajnakh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(43, "О, Ісусе, Ти з лю6ови",
				"pisni/khrysta/o-isuse-ty-z-lyubovy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(44, "Пливи світами", "pisni/khrysta/plyvy-svitamy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(45, "Тіло Христове", "pisni/khrysta/tilo-khrystove.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(604,
				"На чудовій Україні",
				"pisni/khrysta/na-chudovij-ukrayini.html",
				"Українські пісні - На чудовій Україні http://www.pisni.org.ua/songs/472333.html");
		sm.text(605, "Хто Тебе приймає, Христе",
				"pisni/khrysta/khro-tebe-pryjmaye-khryste.html",
				SRC_PISNI_KYRIOS);
		sm.text(606, "Будь благословенний",
				"pisni/khrysta/bud-blahoslovennyj.html", SRC_PISNI_KYRIOS);
		sm.text(607, "В страсі і покорі",
				"pisni/khrysta/v-strasi-i-pokori.html", SRC_PISNI_KYRIOS);
		sm.text(608, "Святе Причастя", "pisni/khrysta/svyate-prychastya.html",
				SRC_PISNI_KYRIOS);
		sm.text(609, "Хочу я з Тобою жить",
				"pisni/khrysta/khochu-z-toboyu-zhyt.html", SRC_PISNI_KYRIOS);
		sm.text(610, "Люблю Тебе", "pisni/khrysta/lyublyu-tebe.html",
				SRC_PISNI_KYRIOS);
		sm.text(611, "Лине пісня про Ісуса",
				"pisni/khrysta/lyne-pisnya-pro-isusa.html", SRC_PISNI_KYRIOS);
		sm.text(612, "Зійшла зоря", "pisni/khrysta/zijshla-zorya.html",
				SRC_PISNI_KYRIOS);
		sm.text(613, "Боже – Ти Бог мій",
				"pisni/khrysta/bozhe-ty-boh-mij.html", SRC_PISNI_KYRIOS);

		sm = menu.subMenu(46, "Пісня до Святого Духа");
		sm.text(47, "Царю небесний", "pisni/dukha/tsaryu-nebesnyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(48, "Пісні до Богородиці");
		sm.text(49, "Богородице Діво",
				"pisni/bohorodytsi/bohorodytse-divo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(50, "Вихваляйте доли, гори",
				"pisni/bohorodytsi/vykhvalyajte-doly-hory.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(51, "Ген до неба", "pisni/bohorodytsi/hen-do-neba.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(52, "Достойно єсть", "pisni/bohorodytsi/dostojno-yest.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(53, "Коли втомлений", "pisni/bohorodytsi/koly-vtomlenyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(54, "Лугами піснь", "pisni/bohorodytsi/luhamy-pisen.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(55, "Маріє, Діво благословенна",
				"pisni/bohorodytsi/mariye-divo-blahoslovenna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(56, "Маріє Діво, Царице мая",
				"pisni/bohorodytsi/mariye-divo-tsarytse-maya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(57, "Нехай звенить", "pisni/bohorodytsi/nekhaj-dzvenyt.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(58, "О, Богородице Діво, Маріє",
				"pisni/bohorodytsi/o-bohorodytse-divo-mariye.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(59, "О, всепітая Мати",
				"pisni/bohorodytsi/o-vsepitaya-maty.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(60, "О, Маріє, Мати Божа",
				"pisni/bohorodytsi/o-mariye-maty-bozha.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(61, "О, Мати Божа, о, райський цвіте",
				"pisni/bohorodytsi/o-maty-bozha-o-rajskyj-tsvite.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(62, "О, Пречиста", "pisni/bohorodytsi/o-prechysta.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(63, "Пренебесна, пречудесна",
				"pisni/bohorodytsi/prenebesna-prechudesna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(64, "Пречистая Діво, Мати",
				"pisni/bohorodytsi/prechysta-divo-maty.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(65, "Просимо тя, Діво",
				"pisni/bohorodytsi/prosymo-tya-divo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(66, "Там, де в небі", "pisni/bohorodytsi/tam-de-v-nebi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(614, "Маріє, Діво Пресвята",
				"pisni/bohorodytsi/mariye-divo-presvyata.html",
				SRC_PISNI_KYRIOS);
		sm.text(615,
				"Пісня до Матері Божої на честь Її Ліської чудотворної ікони",
				"pisni/bohorodytsi/na-chest-liskoyi-ikony.html",
				SRC_PISNI_KYRIOS);
		sm.text(616, "Унівська Мати Божа",
				"pisni/bohorodytsi/univska-maty-bozha.html", SRC_PISNI_KYRIOS);
		sm.text(617, "О Царице", "pisni/bohorodytsi/o-tsarytse.html",
				SRC_PISNI_KYRIOS);
		sm.text(618, "О дивноє чудо з чудес",
				"pisni/bohorodytsi/o-dyvo-dyvneye-z-chudes.html",
				SRC_PISNI_KYRIOS);
		sm.text(619, "Ведуть в Зарваницю",
				"pisni/bohorodytsi/vedut-v-zarvanytsu.html", SRC_PISNI_KYRIOS);

		sm = menu.subMenu(67, "Пісні до святих");
		sm.text(68, "Святий Великий Володимире",
				"pisni/svyatykh/svyatyj-velykyj-volodymyre.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(69, "О, свята Ольго", "pisni/svyatykh/o-svyata-olho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(70, "Пісню слави", "pisni/svyatykh/pisnyu-slavy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(71, "Владико, отче", "pisni/svyatykh/vladyko-otche.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(72, "О, хто, хто Миколая любить",
				"pisni/svyatykh/o-khto-khto-mykolaya-lyubyt.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(73, "Похоронні пісні");
		sm.text(74, "В темну могилу", "pisni/pokhoronni/v-temnu-mohylu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(75, "Кругом життя", "pisni/pokhoronni/kruhom-zhyttya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(76, "Прощай, душе", "pisni/pokhoronni/proschaj-dushe.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(77, "Со святими упокой",
				"pisni/pokhoronni/so-svyatymy-upokoj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(630, "Моліться всі", "pisni/pokhoronni/molitsya-vsi.html",
				SRC_PISNI_KYRIOS);
		sm.text(631, "О, Боже Великий, з високого неба",
				"pisni/pokhoronni/o-bozhe-velykyj-z-vysokoho-neba.html",
				SRC_PISNI_KYRIOS);

		return menu;

	}

	private MenuItemSubMenu addVervytsya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(78, "Вервиця");
		menu.html(80, "Як молитися на Вервиці", "vervytsya/yak-molytysya.html",
				SRC_VERVYTSYA_MOLYTVA);
		menu.html(81, "Молитва на Вервиці",
				"vervytsya/molytva-na-vervytsi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addBohosluzhinnya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(82, "Богослужіння");

		MenuItemSubMenu sm = menu
				.subMenu(682,
						"Чин священної і Божественної Літургії святого Івана Золотоустого");
		sm.html(686, "Молитви перед Літургією",
				"bohosluzhinnya/liturhiya/molytvy-pered-liturhiyeyu.html",
				SRC_LITURHIYA_KYRIOS);
		sm.html(683, "Проскомидія",
				"bohosluzhinnya/liturhiya/proskomydiya.html",
				SRC_LITURHIYA_KYRIOS);
		sm.html(684, "Літургія Слова", "bohosluzhinnya/liturhiya/slova.html",
				SRC_LITURHIYA_KYRIOS);
		sm.html(685, "Літургія Жертви",
				"bohosluzhinnya/liturhiya/zhertvy.html", SRC_LITURHIYA_KYRIOS);

		sm = menu.subMenu(687, "Часослов");
		sm.web(688, "Вечірня", "bohosluzhinnya/chasoslov/vechirnya.html",
				SRC_MOLYTVOSLOV);
		sm.web(689, "Мале Повечір’я",
				"bohosluzhinnya/chasoslov/male-povechirya.html",
				SRC_MOLYTVOSLOV);
		sm.web(690, "Велике Повечір'я",
				"bohosluzhinnya/chasoslov/velyke-povechirya.html",
				SRC_MOLYTVOSLOV);
		sm.web(691, "Північна Щоденна",
				"bohosluzhinnya/chasoslov/pivnichna-schodenna.html",
				SRC_MOLYTVOSLOV);
		sm.web(692, "Північна Суботня",
				"bohosluzhinnya/chasoslov/pivnichna-subotnya.html",
				SRC_MOLYTVOSLOV);
		sm.web(693, "Північна Недільна",
				"bohosluzhinnya/chasoslov/pivnichna-nedilna.html",
				SRC_MOLYTVOSLOV);
		sm.web(694, "Утреня", "bohosluzhinnya/chasoslov/utrenya.html",
				SRC_MOLYTVOSLOV);
		sm.web(695, "Час Перший", "bohosluzhinnya/chasoslov/chas-pershyj.html",
				SRC_MOLYTVOSLOV);
		sm.web(696, "Час Третій", "bohosluzhinnya/chasoslov/chas-tretij.html",
				SRC_MOLYTVOSLOV);
		sm.web(697, "Час Шостий", "bohosluzhinnya/chasoslov/chas-shostyj.html",
				SRC_MOLYTVOSLOV);
		sm.web(698, "Час Дев’ятий",
				"bohosluzhinnya/chasoslov/chas-devyatyj.html", SRC_MOLYTVOSLOV);
		sm.web(699, "Обідниця", "bohosluzhinnya/chasoslov/obidnytsya.html",
				SRC_MOLYTVOSLOV);

		sm = menu.subMenu(437, "Молебні");
		sm.html(448, "Молебень до Христа Спасителя",
				"bohosluzhinnya/molebni/khrysta-spasytelya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(458,
				"Молебень до найсолодшого Господа нашого Ісуса Христа Чоловіколюбця",
				"bohosluzhinnya/molebni/isusa-khrysta-cholovikolyubtsya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(447, "Молебень до Божого милосердя",
				"bohosluzhinnya/molebni/bozhoho-myloserdya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(461, "Молебень до Пресвятої Богородиці",
				"bohosluzhinnya/molebni/presv-bohorodytsi.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(444, "Молебень до Пресвятої Богородиці на честь її Покрову",
				"bohosluzhinnya/molebni/bohorodytsi-pokrovu.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(446,
				"Параклис, або Канон молебний до Пресвятої Богородиці, який співається у всякій скорботі душевній",
				"bohosluzhinnya/molebni/bohorodytsi-skorboti.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(460,
				"Молебень до Матері Божої Непорочно Зачатої.",
				"bohosluzhinnya/molebni/materi-bozhoyi-neporochno-zachatoyi.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(440,
				"Молебень до блаженних новомучеників Української Греко-Католицької Церкви",
				"bohosluzhinnya/molebni/novomuchenykiv-ugcc.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(451,
				"Молебень до блаженних новомучеників Української Греко-Католицької Церкви (1)",
				"bohosluzhinnya/molebni/novomuchenykiv-ugcc-1.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(439,
				"Молебень до святого рівноапостольного князя Володимира Великого, хрестителя Русі-України",
				"bohosluzhinnya/molebni/volodymyra-velykoho.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(445, "Молебень до св. Миколая Чудотворця",
				"bohosluzhinnya/molebni/mykolaya-chudotvortsya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(441, "Молебень до преподобного Шарбеля Чудотворця",
				"bohosluzhinnya/molebni/scherbalya-chudotvortsya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(443,
				"Молебень до священномученика Теодора (Ромжі), єпископа Мукачівського",
				"bohosluzhinnya/molebni/teodora-romzhi.html",
				SRC_MOLEBNI_KYRIOS);
		sm.text(459, "Молебень до св. Йосифа",
				"bohosluzhinnya/molebni/sv-josypha.html", SRC_MOLEBNI_KYRIOS);
		sm.html(454,
				"Молебень до святого великомученика і чудотворця Юрія Побідоносця",
				"bohosluzhinnya/molebni/yuriya-pobidonostsya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(575, "Молебень до священомученика Йосафата Кунцевича",
				"bohosluzhinnya/molebni/josaphata-kuntsevycha.html",
				SRC_MOLEBNI_MOLYTVA);
		sm.html(438,
				"Чин молебня під час війни проти супротивників",
				"bohosluzhinnya/molebni/vijny.html",
				"Християнський портал КІРІОС http://kyrios.org.ua/spirituality/molebni/12477-chin-molebnja-pid-chas-vijni-proti-suprotivnikiv.html , за матеріалами оф.сайту храму свщм. Климентія Шептицького http://klymentij.blogspot.com/ .");
		sm.html(442, "Молебень в часі посухи",
				"bohosluzhinnya/molebni/posukhy.html", SRC_MOLEBNI_KYRIOS);
		sm.html(450, "Молебень на Святу Пасху",
				"bohosluzhinnya/molebni/sv-paskhu.html", SRC_MOLEBNI_KYRIOS);
		sm.html(452,
				"Молебень за примирення святої Церкви та визволення її від напастей єретиків і віровідступників",
				"bohosluzhinnya/molebni/prymyrennya-tserkvy.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(453, "Молебень на початок Нового року",
				"bohosluzhinnya/molebni/pochatok-novoho-roku.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(455, "Молебень перед початком навчання дітей",
				"bohosluzhinnya/molebni/pered-pochatkom-navch-ditey.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(456,
				"Молебень в часі губительного мору і смертоносної недуги (епідемії)",
				"bohosluzhinnya/molebni/mor-i-neduha.html", SRC_MOLEBNI_KYRIOS);
		sm.html(457, "Подячний молебень у дні громадянських і державних свят",
				"bohosluzhinnya/molebni/hrom-i-derzh-svyata.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(462, "Молебень за недужих",
				"bohosluzhinnya/molebni/neduzhykh.html", SRC_MOLEBNI_KYRIOS);

		menu.addSubItem(addTrebnyk());

		menu.html(467, "Чин утрені", "bohosluzhinnya/utrennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(466, "Чин вечірні", "bohosluzhinnya/vechirnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addMolytvyNaKozhenDen() {
		MenuItemSubMenu menu = new MenuItemSubMenu(85, "Молитви на кожен день");
		menu.html(86, "Молитва ранішнього намірення",
				"na-kozhen-den/ranizhnoho-namirennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(87, "Молитва св. Івана Золотоустого на кожну годину дня",
				"na-kozhen-den/iv-zlatoust-kozn-hod-dnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(88, "Неділя", "na-kozhen-den/nedilya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(89, "Понеділок", "na-kozhen-den/ponedilok.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(90, "Вівторок", "na-kozhen-den/vivtorok.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(91, "Середа і п’ятниця",
				"na-kozhen-den/sereda-i-pyatnytsya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(92, "Четвер", "na-kozhen-den/chetver.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(93, "Субота", "na-kozhen-den/subota.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addAkafisty() {
		MenuItemSubMenu menu = new MenuItemSubMenu(94, "Акафісти");
		menu.html(95, "Про Акафіст", "akafisty/pro-akafist.html",
				SRC_AKAFISTY_MOLYTVA);
		// Матері Божої
		menu.text(96, "Акафіст до Благовіщення Пречистої Діви Марії",
				"akafisty/blahovischennyu-bohorodytsi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(97, "Акафiст до Пресвятої Богородицi",
				"akafisty/do-presv-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(98, "Акафіст до Матері Божої на честь Її Покрову",
				"akafisty/pokrovu-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(99, "Акафіст на честь введення в храм Пресвятої Богородиці",
				"akafisty/vvedennyu-v-khram-bohorodytsi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(100, "Акафіст на честь Успення Богородиці",
				"akafisty/uspinnyu-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(101, "Акафіст до Матері Божої Неустанної Помочі",
				"akafisty/presv-bohorodytsi-neustannoyi-pomochi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				102,
				"Акафіст до Пресвятої Богородиці на честь Її ікони «Невипивана Чаша»",
				"akafisty/presv-bohorodytsi-nevypyvna-chasha.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				103,
				"Акафіст до Пресвятої Богородиці на честь Її ікони «Всецариця»",
				"akafisty/presv-bohorodytsi-vsecarytsya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				104,
				"Акафіст Пресвятій Богородиці на честь Її ікони «Годувальниця»",
				"akafisty/presv-bohorodytsi-hoduvalnytsya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				105,
				"Акафіст до Пресвятої Богородиці на честь її ікони «Цілителька»",
				"akafisty/presv-bohorodytsi-tsilytelnytsya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(106, "Акафіст до Зарваницької Матері Божої",
				"akafisty/materi-bozhoji-zarvanytskoji.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(107, "Акафіст до Зарваницької Матері Божої (нова версія)",
				"akafisty/materi-bozhoji-zarvanytskoji-nova.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(108, "Акафіст до Матері Божої Крилоської",
				"akafisty/materi-bozhoji-kryloskoji.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(109, "Акафіст до Самбірської Богородиці",
				"akafisty/do-sambirskoji-bohorodytsi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(110, "Акафіст до Унівської Богородиці",
				"akafisty/do-univskoji-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(
				111,
				"Акафіст до Матері Божої на честь Її ікони «Прибавлення розуму»",
				"akafisty/do-bohorodytsi-na-chest-ikony-prybavlennya-rozumu.html",
				SRC_AKAFISTY_MOLYTVA);
		// Пресвятої Тройці
		menu.text(112, "Акафіст до Пресвятої Трійці",
				"akafisty/presvyatoji-trojtsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(113, "Акафіст до Святого Духа",
				"akafisty/svyatoho-dukha.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(114, "Акафіст до Ісуса Христа",
				"akafisty/isusa-khrysta.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(116, "Акафіст до Христових Страстей",
				"akafisty/khrystovykh-strastej.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(117, "Акафіст до Живоносного Гробу і Воскресіння Христа",
				"akafisty/zhyvonosnoho-hrobu-i-voskresinnya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(118, "Акафіст до Воскресіння Христового",
				"akafisty/voskresinnya-khrystovoho.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(119, "Акафіст до Причастя Святих Христових Таїн",
				"akafisty/prychastya-tain.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(120, "Акафіст подяки Ісусу Христу «Слава Богові за все»",
				"akafisty/podyaky-isusu.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(121, "Акафіст «Абетка покаяння»",
				"akafisty/abetka-pokayannya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(122, "Акафіст - подяка Пресвятій Трійці",
				"akafisty/podyaka-trijtsi.html", SRC_AKAFISTY_MOLYTVA);
		// Святі і мученики
		menu.text(123, "Акафіст до Собору дванадцяти Апостолів Христових",
				"akafisty/do-soboru-apostoliv.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(124, "Акафіст до священомученика Йосафата",
				"akafisty/do-josaphata.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(125, "Акафіст до священомученика Йосафата (другий варіант)",
				"akafisty/do-josaphata-2.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(126, "Акафіст до святого київського Аскольда",
				"akafisty/kyivskoho-askolda.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(127, "Акафіст до святителя Миколая",
				"akafisty/mykolaya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(564, "Акафіст до святого Йосифа Обручника",
				"akafisty/josypha.html", SRC_AKAFISTY_KYRIOS);
		menu.text(128, "Акафіст до блаженного Омеляна Ковча",
				"akafisty/omelyana-kovcha.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(129, "Акафіст до святого отця Піо",
				"akafisty/otsya-pio.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(130, "Акафіст до святого Романа Солодкоспівця",
				"akafisty/romana-solodkospivtsya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(131, "Акафіст до святого Івана Предтечі",
				"akafisty/ivana-predtechi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(132, "Акафіст до святого Василія Великого",
				"akafisty/vasyliya-velykoho.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(133, "Акафіст до святого Агапіта", "akafisty/ahapita.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(134, "Акафіст до святих Апостолів Петра і Павла",
				"akafisty/petra-i-pavla.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(135, "Акафіст до святого Пантелеймона Цілителя",
				"akafisty/panteleymona-tsilytelya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(136, "Акафіст до святої Терези від Дитятка Ісус",
				"akafisty/terezy-vid-isusa.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(137, "Акафіст до святої Філомени", "akafisty/filomeny.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(138, "Акафіст до святої Варвари", "akafisty/varvary.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(139, "Акафіст до святої Параскеви",
				"akafisty/paraskevy.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(
				140,
				"Акафіст Дрогобицьким священомученикам Якиму, Віталію та Северіяну",
				"akafisty/drohobytskomu-yakymu.html", SRC_AKAFISTY_MOLYTVA);
		// Інші
		menu.text(141, "Акафіст до Архистратига Михаїла",
				"akafisty/mykhajila.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(142, "Акафіст до Архистратига Михаїла (другий варіант)",
				"akafisty/mykhajila-2.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(143,
				"Акафіст покаянний жінок, які вбили немовлят у своїй утробі",
				"akafisty/zhinok-vbyly-nemovlyat.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(144, "Акафіст за упокій померлих (багатьох)",
				"akafisty/dushi-pomerlykh.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(145, "Акафіст за померлого (одного)",
				"akafisty/pomerloho-odnoho.html", SRC_AKAFISTY_MOLYTVA);
		return menu;
	}

	private MenuItemSubMenu addKatekhPravdy() {
		MenuItemSubMenu menu = new MenuItemSubMenu(147, "Катехизмові правди");
		menu.html(146, "Заповіді", "kateh-pravdy/zapovidi.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(148, "Чесноти", "kateh-pravdy/chesnoty.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(149, "Пости та загальниці",
				"kateh-pravdy/posty-ta-zahalnytsi.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(150, "Гріхи", "kateh-pravdy/hrikhy.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(151, "Християнський привіт",
				"kateh-pravdy/khryst-pryvit.html", SRC_DODATOK_KATEKHYZMU_2012);
		return menu;
	}

	private MenuItemSubMenu addProSpovidIPrychastya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(401, "Сповідь та причастя");
		menu.html(152, "Про щастя людини", "pro-spovid/pro-schastya.html",
				SRC_PRO_SPOVID);
		menu.html(153, "Церква як Божа установа",
				"pro-spovid/cerkva-yak-bozha-ustanova.html", SRC_PRO_SPOVID);
		menu.html(154, "Сором у визнанні гріхів",
				"pro-spovid/sorom-u-vyznanni-hrikhiv.html", SRC_PRO_SPOVID);
		menu.html(155, "Чи всі сповіді є добрими?",
				"pro-spovid/chy-vsi-spovidi-dobri.html", SRC_PRO_SPOVID);
		menu.html(156, "Що потрібно знати, щоб сповідатися?",
				"pro-spovid/scho-potribno-znaty.html", SRC_PRO_SPOVID);
		menu.html(157, "Святе Причастя", "pro-spovid/svyate-prychastya.html",
				SRC_PRO_SPOVID);
		menu.html(158, "Приготування до сповіді",
				"pro-spovid/prohotuvannya.html", SRC_PRO_SPOVID);
		menu.html(159, "Деякі додаткові зауваги",
				"pro-spovid/dodatkovi-zauvahy.html", SRC_PRO_SPOVID);
		menu.html(160, "Сповідь", "pro-spovid/spovid.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(161, "Молитви в часі сповіді",
				"pro-spovid/molytvy-v-chasi-spovidi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(162, "Молитви перед святим причастям",
				"pro-spovid/molytvy-pered-prychastyam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(163, "Молитви по святім причастю",
				"pro-spovid/molytvy-pislya-prychastya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addMolytvyRizniPotreby() {
		MenuItemSubMenu menu = new MenuItemSubMenu(164,
				"Молитви на різні потреби");

		MenuItemSubMenu sm = menu.subMenu(165, "Молитви для вагітних");
		sm.text(166, "Молитва вагітної жінки",
				"rizni-potreby/dlya-vahitnykh/vahitnoi-zhinky.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.text(167,
				"Молитва – Благословення вагітних жінок",
				"rizni-potreby/dlya-vahitnykh/blahoslovennya-vahitnykh-zhinok.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(168, "Молитва матері, яка очікує дитину",
				"rizni-potreby/dlya-vahitnykh/materi-ochikuye-dytynu.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(169,
				"Молитва до Богородиці для допомоги жінкам у часі пологів",
				"rizni-potreby/dlya-vahitnykh/do-bohorodytsi-dopomohy-pry-polohakh.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(170, "Молитва вагітних жінок за успішні пологи",
				"rizni-potreby/dlya-vahitnykh/za-uspishni-polohy.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(171, "Молитва при важких пологах",
				"rizni-potreby/dlya-vahitnykh/pry-vazhkykh-polohakh.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.text(172,
				"Молитва – Благословення жінки після пологів",
				"rizni-potreby/dlya-vahitnykh/blahoslovennya-pislya-polohiv.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(173,
				"Молитва за ласку годування грудьми",
				"rizni-potreby/dlya-vahitnykh/za-lasku-hoduvannya-hruddyu.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(174,
				"Молитва – Благословення вагітних жінок після Літургії",
				"rizni-potreby/dlya-vahitnykh/blahoslovennya-pislya-liturhiyi.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.text(673,
				"Молитва матері, що прагне народження зачатої дитини, а батько штовхає на вбивство",
				"rizni-potreby/dlya-vahitnykh/materi-prahne-narodzh-batko-shtovkhaye-na-vbyvstvo.html",
				SRC_MOLYTVY_KYRIOS);

		sm = menu.subMenu(672, "Молитви батьків і дітей");
		sm.html(184, "Молитва батьків за дітей",
				"rizni-potreby/batky-dity/batkiv-za-ditey.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.html(185, "Молитва дітей за батьків",
				"rizni-potreby/batky-dity/ditey-za-batkiv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(674, "Молитва матері за дітей",
				"rizni-potreby/batky-dity/materi-za-ditej.html",
				SRC_MOLYTVY_KYRIOS);
		sm.text(675, "Молитва матері за неслухняних дітей",
				"rizni-potreby/batky-dity/materi-za-neslukhnyanykh-ditej.html",
				SRC_MOLYTVY_KYRIOS);
		sm.text(676,
				"Молитва подружжя, яке хоче, але не може мати дітей",
				"rizni-potreby/batky-dity/podruzhzhya-khoche-a-ne-mozhe-maty-ditej.html",
				SRC_MOLYTVY_KYRIOS);
		sm.text(677, "Молитва за дітей, яким важко дається навчання",
				"rizni-potreby/batky-dity/za-ditej-vazhko-navchannya.html",
				SRC_MOLYTVY_KYRIOS);
		sm.text(678, "Молитва при порушенні сну у дітей",
				"rizni-potreby/batky-dity/porushennya-snu-ditej.html",
				SRC_MOLYTVY_KYRIOS);
		sm.text(679, "Молитва матері за дітей до Пресвятої Богородиці",
				"rizni-potreby/batky-dity/materi-za-ditej-do-bohorodytsi.html",
				SRC_MOLYTVY_KYRIOS);
		sm.text(680, "Молитва за дітей на початок навчального року",
				"rizni-potreby/batky-dity/za-ditej-pochatok-navch-roku.html",
				SRC_MOLYTVY_KYRIOS);
		sm.html(681,
				"Молитва матері за ненароджених загублених дітей",
				"rizni-potreby/batky-dity/materi-za-nenarodzh-zahublenykh-ditej.html",
				SRC_MOLYTVY_KYRIOS);

		menu.html(4, "Молитви при трапезі", "molytvy-pry-trapezi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(175, "Молитва подяки", "rizni-potreby/podyaky.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(176, "Молитва на всяке прошення",
				"rizni-potreby/vsyake-proshennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(177, "Молитва до Пресвятої Богородиці",
				"rizni-potreby/do-presv-bohorod.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(178, "Молитва у терпінні", "rizni-potreby/u-terpinni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(179, "Молитва за недужих", "rizni-potreby/za-neduzhykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(180, "Молитва на всяку неміч",
				"rizni-potreby/na-vsyaku-nemich.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(181, "Молитва при вмираючих",
				"rizni-potreby/pry-vmyrayuchykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(182, "Молитва у годині смерти",
				"rizni-potreby/u-hodyni-smerti.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(434, "Молитва за померлих",
				"rizni-potreby/za-pomerlykh.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.html(183, "Молитва християнської родини",
				"rizni-potreby/khryst-rodyny.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(186, "Молитва за чоловіка (дружину)",
				"rizni-potreby/za-cholovika-druzhynu.html",
				"МОЛИТВА - Родинні молитви http://molytva.at.ua/index/rodinni_molitvi/0-151");
		menu.html(187, "Молитва учня (учениці)", "rizni-potreby/uchnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(433, "Молитва за вибір стану",
				"rizni-potreby/vybir-stanu.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.html(188, "Молитва за перемогу над спокусами",
				"rizni-potreby/peremoha-nad-spokusamy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(189, "Молитва про духа покори",
				"rizni-potreby/dukha-pokory.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(190, "Молитва в день іменин",
				"rizni-potreby/v-den-imenyn.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(191, "Молитва за того, хто хоче іти в путь",
				"rizni-potreby/ity-v-put.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(192, "Молитва на благословення дому",
				"rizni-potreby/blahoslovennya-domu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(193, "Молитва на освячення всякої речі",
				"rizni-potreby/osvyach-rechi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(194, "Молитви за з’єднання всіх християн",
				"rizni-potreby/zyednannya-khrystyyan.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(436, "Молитва за український народ",
				"rizni-potreby/za-ukr-narod.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(435, "Молитва на Новий рік",
				"rizni-potreby/na-novyj-rik.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		return menu;
	}

	private void addPsaloms(MenuItemSubMenu sm) {
		for (int i = 1; i <= 150; i++) {
			String name = "Псалом " + i;
			if (i >= 10) {
				name += " (" + (i - 1) + ")";
			}
			sm.text(195 + i, name, "psalmy/" + i + ".html", SRC_BIBLIA);
		}
	}

	public List<MenuItemBase> getTopMenuItems() {
		return topMenu;
	}

	private void verifyUniqueId() {
		if (Utils.isDebuggable()) {
			Set<Integer> usedIds = new TreeSet<Integer>();
			for (MenuItemBase mi : topMenu) {
				verifyUniqueId(usedIds, mi);
			}
		}
	}

	private void verifyUniqueId(Set<Integer> usedIds, MenuItemBase mi) {
		if (mi instanceof MenuItemSubMenu) {
			for (MenuItemBase item : ((MenuItemSubMenu) mi).getSubItems()) {
				verifyUniqueId(usedIds, item);
			}
		}
		if (usedIds.contains(mi.getId())) {
			throw new RuntimeException("Catalog already contains item with id "
					+ mi.getId());
		}
		usedIds.add(mi.getId());
		if (NEXT_ID_TO_ADD <= mi.getId()) {
			throw new RuntimeException(
					"Catalog: please increment NEXT_ID_TO_ADD to at least "
							+ (mi.getId() + 1));
		}
	}

	private MenuItemBase getMenuItemById(int id, List<MenuItemBase> items) {
		for (MenuItemBase mi : items) {
			if (mi.getId() == id) {
				return mi;
			}
			if (mi instanceof MenuItemSubMenu) {
				MenuItemBase item = getMenuItemById(id,
						((MenuItemSubMenu) mi).getSubItems());
				if (item != null) {
					return item;
				}
			}
		}
		return null;
	}

	public MenuItemBase getMenuItemById(int id) {
		MenuItemBase mi = menuItemsByIds.get(id);
		if (mi == null) {
			mi = getMenuItemById(id, topMenu);
			menuItemsByIds.put(id, mi);
		}
		return mi;
	}

	public Set<String> getAllSources() {
		Set<String> sources = new HashSet<String>();
		for (MenuItemBase mi : topMenu)
			addSources(sources, mi);
		return sources;
	}

	private void addSources(Set<String> sources, MenuItemBase mi) {
		if (mi instanceof MenuItemPrayer) {
			String src = ((MenuItemPrayer) mi).getSource();
			if (!TextUtils.isEmpty(src)) {
				sources.add(src);
			}
		} else if (mi instanceof MenuItemSubMenu) {
			for (MenuItemBase sm : ((MenuItemSubMenu) mi).getSubItems())
				addSources(sources, sm);
		}
	}
}

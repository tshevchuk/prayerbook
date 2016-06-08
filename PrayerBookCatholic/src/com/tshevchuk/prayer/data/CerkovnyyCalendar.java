package com.tshevchuk.prayer.data;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.tshevchuk.prayer.data.CalendarDay.PistType;

public class CerkovnyyCalendar {
	private static final int FLAG_RUKHOME_REPLACE = 20000;
	private static final int FLAG_RUKHOME_REPLACE_RED_DAY = 30000;
	private static final int FLAG_RUKHOME_RED_DAY = 40000;

	private static CerkovnyyCalendar instance;

	private int totalDaysInSupportedYears = 0;

	private Set<Integer> svyataNerukhomi = new HashSet<Integer>(50);
	private SparseArray<String> svyataRukhomi = new SparseArray<String>(50);
	private SparseIntArray dataVelykodnya = new SparseIntArray(50);
	private SparseArray<String> dni = new SparseArray<String>(400);

	public static synchronized CerkovnyyCalendar getInstance() {
		if (instance == null) {
			instance = new CerkovnyyCalendar();
		}
		return instance;
	}

	private CerkovnyyCalendar() {
		initVelykdenDate();
		initRukhomiSvyata();

		initVeresen();
		initZhovten();
		initLystopad();
		initHruden();
		initSichen();
		initLjutyy();
		initBerezen();
		initKviten();
		initTraven();
		initCherven();
		initLypen();
		initSerpen();

		svyataNerukhomi.addAll(Arrays.asList(914, 921, 927, 1014, 1121, 1204,
				1219, 1222, 101, 107, 108, 109, 114, 119, 212, 215, 407, 506,
				516, 521, 522, 707, 712, 806, 807, 819, 828, 911));
	}

	public CalendarDay getCalendarDay(Date day) {
		PistType pistType = PistType.Normal;
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);

		int dayHreh = cal.get(Calendar.DAY_OF_MONTH);
		int monthHreh = cal.get(Calendar.MONTH) + 1;
		int yearHreh = cal.get(Calendar.YEAR);
		int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

		cal.add(Calendar.DAY_OF_MONTH, -13);
		int dayJulian = cal.get(Calendar.DAY_OF_MONTH);
		int monthJulian = cal.get(Calendar.MONTH) + 1;
		Date dateJulian = cal.getTime();

		int dateVelykdnya = dataVelykodnya.get(yearHreh);
		Calendar calVelykden = Calendar.getInstance();
		calVelykden.setTime(day);
		calVelykden.set(Calendar.MONTH, dateVelykdnya / 100 - 1);
		calVelykden.set(Calendar.DAY_OF_MONTH, dateVelykdnya % 100);
		int velykdenDayOfYear = calVelykden.get(Calendar.DAY_OF_YEAR);

		boolean isDateRed = dayOfWeek == Calendar.SUNDAY
				|| svyataNerukhomi.contains(dayHreh + monthHreh * 100);
		String description = dni.get(dayJulian + monthJulian * 100);

		String rukhomeSvyato = svyataRukhomi.get(dayOfYear - velykdenDayOfYear);
		String rukhomeSvyateReplace = svyataRukhomi.get(dayOfYear
				- velykdenDayOfYear + FLAG_RUKHOME_REPLACE);
		String rukhomeSvyateReplaceRedDay = svyataRukhomi.get(dayOfYear
				- velykdenDayOfYear + FLAG_RUKHOME_REPLACE_RED_DAY);
		String rukhomeSvyatoRedDay = svyataRukhomi.get(dayOfYear
				- velykdenDayOfYear + FLAG_RUKHOME_RED_DAY);
		if (!TextUtils.isEmpty(rukhomeSvyato)) {
			description = rukhomeSvyato + "; " + description;
		} else if (!TextUtils.isEmpty(rukhomeSvyateReplace)) {
			description = rukhomeSvyateReplace;
		} else if (!TextUtils.isEmpty(rukhomeSvyateReplaceRedDay)) {
			description = rukhomeSvyateReplaceRedDay;
			isDateRed = true;
		} else if (!TextUtils.isEmpty(rukhomeSvyatoRedDay)) {
			description = rukhomeSvyatoRedDay + "; " + description;
			isDateRed = true;
		}
		description = description.replaceAll("<r>", "<font color=\"red\">")
				.replaceAll("</r>", "</font>");
		CalendarDay cd = new CalendarDay(day, dateJulian, pistType,
				description, isDateRed);
		return fixes(cd);
	}

	public int[] getYears(){
		int[] years = new int[dataVelykodnya.size()];
		for(int i = 0; i < years.length; ++i){
			years[i] = dataVelykodnya.keyAt(i);
		}
		return years;
	}
	
	private CalendarDay fixes(CalendarDay day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day.getDay());
		String dayStr = String.format(Locale.US, "%02d%02d",
				cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));

		String newDescr = null;
		if (cal.get(Calendar.YEAR) == 2014)
			if ("0221".equals(dayStr)) {
				newDescr = "<i>Віддання Стрітення</i>; Вмуч. Теодора Стратилата; прор. Захарії";
			} else if ("0222".equals(dayStr)) {
				newDescr = "<i>Заупокійна</i>; Муч. Никифора";
			} else if ("0628".equals(dayStr)) {
				newDescr = "Прор. Амоса; преп. Єроніма Стридонського";
			}

		if (newDescr != null) {
			day = new CalendarDay(day.getDay(), day.getDayJulian(),
					day.getPistType(), newDescr, day.isDateRed());
		}
		return day;
	}

	private void initSerpen() {
		dni.put(801, "Перенес. Чесного Хреста; мучч. Макавейських");
		dni.put(802, "Перенес. мощей первомуч. Стефана");
		dni.put(803, "Препп. Ісаака, Далмата й Фавста");
		dni.put(804, "7-ох мучч. Ефеських; препмуч. Євдокії");
		dni.put(805, "Муч. Євсигнія");
		dni.put(806, "<b><r>⊕ ПРЕОБРАЖЕННЯ ГОСПОДНЄ</r></b>");
		dni.put(807, "Препмуч. Дометія");
		dni.put(808, "Св. Еміліяна; мучч. Єлевтерія і Леоніда");
		dni.put(809, "<b>† Ап. Матія</b>");
		dni.put(810, "Муч. Лаврентія, архидиякона");
		dni.put(811, "Муч. Євпла");
		dni.put(812, "Мучч. Фотія і Аникити");
		dni.put(813, "<i>Віддання Преображення</i>; Преп. Максима, ісповідника");
		dni.put(814,
				"<i>Передсв. Успіння</i>; <b>† Перенес. мощей преп. Теодосія Печерськ.</b>");
		dni.put(815, "<b><r>⊕ УСПІННЯ ПРЕСВЯТОЇ БОГОРОДИЦІ</r></b>");
		dni.put(816, "Нерукотворн. Образа ГНІХ; муч. Діомида");
		dni.put(817, "Муч. Мирона");
		dni.put(818, "Мучч. Флора й Фавна");
		dni.put(819, "Муч. Андрія Стратилата й інших");
		dni.put(820, "Прор. Самуїла");
		dni.put(821, "Ап. Тадея; муч. Васси");
		dni.put(822, "Мучч. Агатоніка, Северіяна, Зенона та ін.");
		dni.put(823,
				"<i>Віддання Успіння</i>; Муч. Луппа; свщмч. Іринея Ліонського");
		dni.put(824, "Свщмч. Євтихія");
		dni.put(825, "Апп. Тита і Вартоломея");
		dni.put(826, "Мучч. Адріяна й Наталії");
		dni.put(827, "Преп. Пімена");
		dni.put(828, "Преп. Мойсея Мурина; св. Августина, єп. Іппонського");
		dni.put(829, "<b><r>⊕ Усікновення голови св. Йоана Христителя</r></b>");
		dni.put(830, "Свв. Олександра, Івана, Павла, патріярхів Царгородських");
		dni.put(831, "<b>† Покладення пояса Пресв. Богородиці</b>");
	}

	private void initLypen() {
		dni.put(701, "Безсрібн. і чудотв. Косми і Дам'яна");
		dni.put(702, "<b>† Покладення Ризи Пресв. Богородиці у Влахерні</b>");
		dni.put(703, "Муч. Якинта; свят. Анатолія, патр. Царгород.");
		dni.put(704,
				"Свщмч. Павла Гойдича; свят. Андрія, єп. Критського; преп. Марти");
		dni.put(705, "<b>† Преп. Атанасія Атонського</b>");
		dni.put(706, "Преп. Сісоя Великого");
		dni.put(707, "Препп. Томи й Акакія");
		dni.put(708, "Вмуч. Прокопія");
		dni.put(709, "Свщмч. Панкратія, єп. Тавроменійського");
		dni.put(710, "<b>† Преп. Антонія Печерського</b>");
		dni.put(711, "<b>Рівноап. Ольги, кн. Київської</b>, муч. Євфимії");
		dni.put(712, "Мучч. Прокла й Іларія; преп. Михаїла Малеїна");
		dni.put(713, "Преп. Стефана Саваїта; Собор арх. Гавриїла");
		dni.put(714, "Ап. Акили");
		dni.put(715, "<b>† Рівноап. Володимира Великого, князя Київського</b>");
		dni.put(716, "Свщмч. Атеногена й 10-х його учнів");
		dni.put(717, "Вмуч. Маріни");
		dni.put(718, "Мучч. Якинта й Еміліяна");
		dni.put(719, "Преп. Макрини, сестри св. Василія Вел.; преп. Дія");
		dni.put(720, "<b>⊕ Прор. Іллі</b>");
		dni.put(721, "Препп. Симеона й Івана; проп. Єзекиїла");
		dni.put(722, "Рівноап. Марії Магдалини");
		dni.put(723, "Мучч. Трохима, Теофіла та ін.");
		dni.put(724, "<b>† Мучч. Бориса і Гліба</b>, вмуч. Христини");
		dni.put(725, "<b>† Успіння св. Анни, матері Пресв. Богородиці</b>");
		dni.put(726, "Муч. Єрмолая й інших");
		dni.put(727, "<b>† Вмуч. Пантелеймона, лікаря</b>");
		dni.put(728, "Апп. Прохора, Ніканора, Тимона й Пармена");
		dni.put(729, "Муч. Калиніка");
		dni.put(730, "Апп. Сили, Силуана, Андроніка та ін.");
		dni.put(731, "Преп. Євдокима, праведного");
	}

	private void initCherven() {
		dni.put(601, "Муч. Юстина, філософа й ін.");
		dni.put(602, "Св. Никифора, патр. Царгородського");
		dni.put(603, "Муч. Лукиліяна і тих, що з ним");
		dni.put(604, "Св. Митрофана, патр. Царгородського");
		dni.put(605, "Свщмч. Доротея, єп. Тирського");
		dni.put(606, "Препп. Вісаріона й Іларіона");
		dni.put(607, "Свщмч. Теодота, єп. Анкірського");
		dni.put(608, "Перенес. мощей вмуч. Теодора Тирона");
		dni.put(609, "Св. Кирила, аєп. Олександрійського");
		dni.put(610, "Свщмч. Тимотея, єп. Пруського");
		dni.put(611, "<b>† Апп. Вартоломея і Варнави</b>");
		dni.put(612, "Препп. Онуфрія Великого і Петра Атонського");
		dni.put(613, "Муч. Акилини; св. Трифілія, єп. Левкусії");
		dni.put(614,
				"Свщмч. Миколая Чарнецького і 24-х укр. мучч.; муч. Омеляна Ковча; прор. Єлисея; св. Методія, патр. Царгород.");
		dni.put(615, "Прор. Амоса; преп. Єроніма Стридонського");
		dni.put(616, "Свят. Тихона, єп. Аматунського");
		dni.put(617, "Мучч. Мануїла, Савела та Ізмаїла");
		dni.put(618, "Муч. Леонтія");
		dni.put(619, "<b>† Ап. Юди, брата Господнього</b>");
		dni.put(620, "Свщмч. Методія, єп. Патарського");
		dni.put(621, "Муч. Юліяна Тарсійського");
		dni.put(622, "Свщмч. Євсевія, єп. Самосатського");
		dni.put(623, "Муч. Агрипини");
		dni.put(624, "<b><r>⊕ Різдво св. Йоана Христителя</r></b>");
		dni.put(625, "Препмуч. Февронії");
		dni.put(626, "Преп. Давида Солунського");
		dni.put(627, "Преп. Самсона, лікаря");
		dni.put(628, "Перенес. мощей безсрібн. Кира й Івана");
		dni.put(629, "<b><r>⊕ Верх. апп. ПЕТРА І ПАВЛА</r></b>");
		dni.put(630, "<b>† Собор 12-ох апп.</b>");
	}

	private void initTraven() {
		dni.put(501, "Прор. Єремії");
		dni.put(502, "Свят. Атанасія Великого. аєп. Олександр.");
		dni.put(503,
				"<b>† Преп. Теодосія Печерського</b>, Мучч. Тимотея і Маври");
		dni.put(504, "Муч. Пелагії");
		dni.put(505, "Муч. Ірини , Преп. Никифора, ігум. Мідійського");
		dni.put(506, "Праведного і многостражд. Йова");
		dni.put(507, "Муч. Акакія , Поява Чесного Хреста в Єрусалимі");
		dni.put(508, "<b>⊕ Ап. Йоана Богослова</b>, Преп. Арсенія Великого");
		dni.put(509,
				"<b>† Перенесення мощей Миколая Чудотворця</b>, Прор. Ісаї; муч. Христофора");
		dni.put(510, "<b>† Ап. Симона Зилота</b>");
		dni.put(511, "<b>† Рівноапп. Кирила і Методія, учит. слов'ян</b>");
		dni.put(512, "Святт. Єпіфанія, єп. Кіпру і Германа, патр. Єрусал.");
		dni.put(513, "Муч. Глікерії");
		dni.put(514, "Муч. Ісидора");
		dni.put(515, "Преп. Пахомія Великого");
		dni.put(516, "Преп. Теодора Освященного");
		dni.put(517, "Ап. Андроніка й інших із ним");
		dni.put(518, "Мучч. Теодота, Петра, Діонісія, Андрія й ін.");
		dni.put(519, "Свщмуч. Патрикія, єп. Пруського");
		dni.put(520, "Муч. Талалея");
		dni.put(521, "<b>† Рівноапп. Костянтина і Олени</b>");
		dni.put(522, "Муч. Василіска");
		dni.put(523, "Свят. Михаїла, єп.; преп. Єфросинії Полоцької");
		dni.put(524, "Препп. Симеона Дивногорця, Микити Переяслав.");
		dni.put(525, "<b>† Третє віднайдення голови св. Йоана Христителя</b>");
		dni.put(526, "Ап. Карпа, одного з семидесятьох");
		dni.put(527, "Свящ. Терапонта, єп. Сардійського");
		dni.put(528, "Свят. Микити, єп. Халкедонського");
		dni.put(529, "Препмуч. Теодосії, діви");
		dni.put(530, "Преп. Ісаакія Далматського");
		dni.put(531, "Ап. Єрмія; муч. Єрмея");
	}

	private void initKviten() {
		dni.put(401, "Преп. Марії Єгипетської");
		dni.put(402, "Преп. Тита, чудотворця");
		dni.put(403, "Преп. Микити, ігум. Мідікій­ського");
		dni.put(404, "Преп. Йосифа, піснописця, і Юрія, Що в Малеї");
		dni.put(405, "Муч. Теодула, Агатопода і тих, що з ними");
		dni.put(406, "+ Свят. Методія, учителя слов’янського; свят. Євтихія");
		dni.put(407, "Преп. Юрія, єп. Митилинського");
		dni.put(408, "Апп. Іродіона, Агава, Руфа та ін.");
		dni.put(409, "Муч. Євпсихія");
		dni.put(410, "Мучч. Терентія, Помпілія і тих, що з ними");
		dni.put(411, "Свщмч. Антипа, єп. Пергамського");
		dni.put(412, "Іспов. Василія, єп. Парійського");
		dni.put(413, "Свщмч. Артемона Лаодикійського");
		dni.put(414, "Іспов. Мартина, Папи Римського");
		dni.put(415, "Апп. Аристарха, Пуда і Трохима");
		dni.put(416, "Мучч. Агапії, Ірини й Хіонії");
		dni.put(417, "Святт. Симеона й Акакія, єпп.");
		dni.put(418, "Преп. Івана, учня Григорія Декаполіта");
		dni.put(419, "Преп. Івана, Печерника");
		dni.put(420, "Преп. Теодора Трихіни");
		dni.put(421, "Свщмч. Януарія й інших");
		dni.put(422, "Препп. Теодора Сікіота і Віталія");
		dni.put(423, "<b>⊕ Вмуч. Юрія Переможця</b>");
		dni.put(424, "Муч. Сави Стратилата");
		dni.put(425, "<b>† Ап. і єванг. Марка</b>");
		dni.put(426, "Свщмч. Василія, єп. Амасійського");
		dni.put(427, "Свщмч. Симеона; свят. Стефана Волинського");
		dni.put(428, "Апп. Ясона й Сосіпатра; мучч. Максима, Дади й ін.");
		dni.put(429, "9-х мучч. у Кизиці; преп. Мемнона, чудотворця");
		dni.put(430, "<b>† Ап. Якова, брата св. Йоана Богослова</b>");
	}

	private void initBerezen() {
		dni.put(301, "Препмуч. Євдокії");
		dni.put(302, "Свщмч. Теодота, єп. Кириней­ського");
		dni.put(303, "Мучч. Євтропія, Клеоніка й Василіска");
		dni.put(304, "Преп. Герасима, що в Йордані");
		dni.put(305, "Муч. Конона");
		dni.put(306, "42-х мучч. Аморійських");
		dni.put(307, "Свщмучч. Василія, Єфрема, Капітона й ін.");
		dni.put(308, "Свят. Теофілакта, єп. Нікомидій­ського");
		dni.put(309, "<b>† 40 мучч. Севастій­ських</b>");
		dni.put(310, "Мучч. Кондрата, Кипріяна, Анекта і Крискента");
		dni.put(311, "Свят. Софронія, патр. Єрусалим­ського");
		dni.put(312, "Іспов. Теофана Сигріян­ського; свят. Григорія, Папи");
		dni.put(313, "Перенес. мощей свят. Никифора, патр. Царгород.");
		dni.put(314, "Преп. Венедикта");
		dni.put(315, "Мучч. Агапія і шістьох ін.");
		dni.put(316, "Мучч. Савина й Папи");
		dni.put(317, "Преп. Олексія, чоловіка Божого");
		dni.put(318, "Свят. Кирила, аєп. Єрусалимського");
		dni.put(319, "Мучч. Хризанта і Дарії");
		dni.put(320, "Препп. Отців, убитих у монастирі св. Сави");
		dni.put(321, "Іспов. Якова, єп. Катанського");
		dni.put(322, "Свящмуч. Василія Анкірського");
		dni.put(323, "Мучч. Нікона та його учнів, з ним замучених");
		dni.put(324, "<i>Передсв. Благовіщення</i>; Преп. Захарії");
		dni.put(325, "<b><r>⊕ БЛАГОВІЩЕННЯ ПРЕЧИСТОЇ ДІВИ МАРІЇ</r></b>");
		dni.put(326, "<i>Віддання Благовіщення</i>; Собор арх. Гавриїла");
		dni.put(327, "Муч. Матрони Солунської");
		dni.put(328, "Преп. Іларіона Нового; преп. Стефана, чудотв.");
		dni.put(329, "Свщмчч. Марка й Кирила");
		dni.put(330, "Преп. Івана Ліста");
		dni.put(331, "Св. Іпатія, єп. Гангренського");
	}

	private void initLjutyy() {
		dni.put(201, "<i>Передсв. Стрітення</i>; Муч. Трифона");
		dni.put(202, "<r><b>⊕ СТРІТЕННЯ ГОСПОДНЄ</b></r>");
		dni.put(203, "Симеона Богоприємця і Анни Пророчиці");
		dni.put(204, "Преп. Ісидора Пилюсіотського");
		dni.put(205, "Муч. Агафії");
		dni.put(206, "Свят. Вукола, єп.; свящмуч. Сілвана, єп.");
		dni.put(207, "Свят. Партенія, єп. Лампсакійського");
		dni.put(208, "Вмуч. Теодора Тирона; прор. Захарії");
		dni.put(209, "<i>Віддання Стрітення</i>; Муч. Никифора");
		dni.put(210, "Муч. Харалампія");
		dni.put(211, "Свщмч. Власія, єп. Севастійського");
		dni.put(212, "Свят. Мелетія, аєп. Антіохійського");
		dni.put(213, "Преп. Мартиніяна");
		dni.put(214, "<b>† Рівноап. Кирила, учит. слов'ян</b>, преп. Авксентія");
		dni.put(215, "Ап. Онисима");
		dni.put(216, "Мучч. Памфіла, Порфірія та ін.");
		dni.put(217, "Вмуч. Теодора Тирона");
		dni.put(218, "Свят. Льва, Папи Римського");
		dni.put(219, "Ап. Архипа");
		dni.put(220, "Преп. Льва, єп. Катанського");
		dni.put(221, "Преп. Тимотея; свят. Євстатія, аєп. Антіох.");
		dni.put(222, "Віднайд. мощів мучч., що в Євгенії");
		dni.put(223, "Вмуч. Теодора Тирона, Свщмч. Полікарпа, єп. Смирнського");
		dni.put(224, "<b>† Перше і друге знайдення голови Йоана Христ.</b>");
		dni.put(225, "Свят. Тарасія, аєп. Царгород.");
		dni.put(226, "Свят. Порфірія, єп. Газького");
		dni.put(227, "Іспов. Прокопія Декаполіта");
		dni.put(228, "Іспов. Василія, посника");
		dni.put(229, "Преп. Касіяна");
	}

	private void initSichen() {
		dni.put(101, "<b><r>⊕ Обрізання ГНІХ і св. Василія Великого</r></b>");
		dni.put(102, "<i>Передсвяття Богоявління</i>; Св. Сильвестра, Папи");
		dni.put(103, "Прор. Малахії; муч. Гордія");
		dni.put(104,
				"<i>Царські часи</i>; Собор 70 апп.; преп. Теоктиста, ігумена");
		dni.put(105, "<i>Навечір'я Богоявління</i>; Мучч. Теопемпта й Теони");
		dni.put(106, "<b><r>⊕ БОГОЯВЛІННЯ ГОСПОДНЄ</r></b>");
		dni.put(107, "Собор св. Йоана Христителя");
		dni.put(108, "Преп. Юрія Хозевіта; преп. Домініки; іспов. Еміліяна");
		dni.put(109, "Муч. Полієвкта");
		dni.put(110,
				"Свщмучч. Пратулинських; Св. Григорія Ніс.; препп. Дометіяна і Маркіяна");
		dni.put(111, "<b>† Преп. Теодосія Великого</b>");
		dni.put(112, "Муч. Татіяни");
		dni.put(113, "Мучч. Єрмила і Стратоніка");
		dni.put(114,
				"<i>Віддання Богоявління</i>; Препмучч. Отців, убитих у Синаї");
		dni.put(115, "Препп. Павла Тебського та Івана Кущника");
		dni.put(116, "Поклоніння кайданам св. ап. Петра");
		dni.put(117, "<b>† Преп. Антонія Великого</b>");
		dni.put(118, "Свв. Атанасія і Кирила, аєпп. Олександрій­ських");
		dni.put(119, "Преп. Макарія Єгипетського");
		dni.put(120, "<b>† Преп. Євтимія Великого</b>");
		dni.put(121, "Іспов. Максима; муч. Неофіта");
		dni.put(122, "Ап. Тимотея; препмуч. Анастасія Перського");
		dni.put(123, "Свщмч. Климента, єп.; муч. Агатангела");
		dni.put(124, "Преп. Ксенії Римлянки");
		dni.put(125, "<b>Свят. Григорія Богослова, аєп. Царгород.</b>");
		dni.put(126, "Препп. Ксенофонта і дружини його Марії");
		dni.put(127, "<b>† Перенес. мощей свят. Йоана Золотоустого</b>");
		dni.put(128, "Преп. Єфрема Сирійця");
		dni.put(129, "Перенес. мощей свщмуч. Ігнатія Богоносця");
		dni.put(130,
				"<b><r>⊕ Трьох Святителів: Василія, Григорія, Йоана Золотоустого</r></b>");
		dni.put(131, "Безсрібн. і чудотв. Кира й Івана");
	}

	private void initHruden() {
		dni.put(1201, "Прор. Наума");
		dni.put(1202, "Прор. Авакума");
		dni.put(1203, "Прор. Софонії");
		dni.put(1204, "Вмуч. Варвари; преп. Івана Дамаскина");
		dni.put(1205, "<b>† Преп. Сави Освященного</b>");
		dni.put(1206, "<b><r>⊕ Миколая Чудотворця, аєп. Мир Ликійських</r></b>");
		dni.put(1207, "Св. Амвросія Медіоланського");
		dni.put(1208, "Преп. Патапія");
		dni.put(1209, "<b><r>⊕ Непорочне Зачаття Пресвятої Богородиці</r></b>");
		dni.put(1210, "Мучч. Мини, Єрмогена й Євграфа");
		dni.put(1211, "Преп. Даниїла Стовпника");
		dni.put(1212, "Св. Спиридона, єп. Трімітійського");
		dni.put(1213, "<b>† Мучч. Євстратія, Євгенія, Авксентія й Ореста</b>");
		dni.put(1214, "Мучч. Тирса, Левкія, Филимона, Аполонія, Калиніка");
		dni.put(1215, "Свщмч. Елевтерія; преп. Павла");
		dni.put(1216, "Прор. Агея");
		dni.put(1217, "Прор. Даниїла й Ананії, Азарії й Мисаїла");
		dni.put(1218, "Муч. Севастіяна й ін.");
		dni.put(1219, "Муч. Боніфатія");
		dni.put(1220,
				"<i>Передсвяття Різдва Христового</i>; Свщмч. Ігнатія Богоносця");
		dni.put(1221, "Муч. Юліяни");
		dni.put(1222, "Вмуч. Анастасії");
		dni.put(1223, "10-х мучч. Критських");
		dni.put(1224, "<i>Навечір'я Різдва Христового</i>; Препмуч Євгенії");
		dni.put(1225, "<b><r>⊕ РІЗДВО ХРИСТОВЕ</r></b>");
		dni.put(1226,
				"<b><r>⊕ Собор Пресвятої Богородиці і св. Йосифа Обручника</r></b>");
		dni.put(1227, "<r>Первомученика і архидиякона Стефана</r>");
		dni.put(1228, "Мучч., спалених у Нікомидії");
		dni.put(1229, "Св. дітей, убитих у Вифлеємі; преп. Маркела");
		dni.put(1230, "Муч. Анісії; преп. Зотика, пресвіт.");
		dni.put(1231,
				"<i>Віддання Різдва Христового</i>; Преп. Меланії Римлянки");
	}

	private void initLystopad() {
		dni.put(1101, "Безсрібників і чудотв. Косми і Дам'яна");
		dni.put(1102, "Мучч. Акиндіна, Пигасія, Афтонія, Елпидифора й ін.");
		dni.put(1103, "Мучч. Акепсима, Йосифа й Айтала");
		dni.put(1104, "Преп. Йоанікія Великого; мучч. Нікандра та Єрмея");
		dni.put(1105, "Мучч. Галактіона й Епістими");
		dni.put(1106, "Іспов. Павла, аєп. Царгород.");
		dni.put(1107, "Преп. Йосафати Гордашевської; 33-х мучч., що в Мелітині");
		dni.put(1108, "<b><r>⊕ Собор архистратига Михаїла</r></b>");
		dni.put(1109, "Препп. Матрони і Теоктисти");
		dni.put(1110, "Апп. Ераста, Олімпа, Родіона і тих, що з ними");
		dni.put(1111, "Мучч. Мини, Віктора і Вінкентія; преп. Теодора Студ.");
		dni.put(1112, "<b>⊕ Свщмч. Йосафата, аєп. Полоцького</b>");
		dni.put(1113, "<b>† Св. Йоана Золотоустого, аєп. Царгород.</b>");
		dni.put(1114, "<b>† Ап. Пилипа</b>");
		dni.put(1115, "Іспов. Гурія, Самона й Авіва");
		dni.put(1116, "<b>† Ап. і Єванг. Матея</b>");
		dni.put(1117, "Св. Григорія, єп. Неокесарійського");
		dni.put(1118, "Мучч. Платона і Романа");
		dni.put(1119, "Прор. Авдія; муч. Варлаама");
		dni.put(1120, "<i>Передсв. Введення</i>; Преп. Григорія Декаполіта");
		dni.put(1121, "<b><r>⊕ ВВЕДЕННЯ В ХРАМ ПРЕСВЯТОЇ БОГОРОДИЦІ</r></b>");
		dni.put(1122, "Ап. Филимона і тих, що з ним");
		dni.put(1123, "Свв. Амфілохія і Григорія, єпп.");
		dni.put(1124, "Вмуч. Катерини; вмуч. Меркурія");
		dni.put(1125, "<i>Віддання Введення</i>; Свщмч. Климента, Папи");
		dni.put(1126, "Преп. Аліпія, стовпника");
		dni.put(1127, "Вмуч. Якова Персіянина; преп. Паладія");
		dni.put(1128, "Препмуч. Стефана Нового; муч. Іринарха");
		dni.put(1129, "Мучч. Парамона і Філумена");
		dni.put(1130, "<b>† Ап. Андрія Первозванного</b>");
	}

	private void initZhovten() {
		dni.put(1001, "<b><r>⊕ Покров Пресвятої Богородиці</r></b>");
		dni.put(1002, "Свщмч. Кипріяна; муч. Юстини; преп. Андрія");
		dni.put(1003, "Свщмч. Діонісія Ареопатіга");
		dni.put(1004, "Свщмч. Єротея, єп. Атен.; преп. Франціска Асиж.");
		dni.put(1005, "Муч. Харитини");
		dni.put(1006, "<b>† Ап. Томи</b>");
		dni.put(1007, "Мучч. Сергія і Вакха");
		dni.put(1008, "Преп. Пелагії");
		dni.put(1009, "<b>† Ап. Якова Алфеєва</b>");
		dni.put(1010, "Мучч. Євлампія та Євлампії, сестри його");
		dni.put(1011, "Ап. Филипа, дияк.; преп. Теофана, єп. Нікейського");
		dni.put(1012, "Мучч. Прова, Тараха й Андроніка; преп. Косми");
		dni.put(1013, "Мучч. Карпа, Папіли й Агатоніки");
		dni.put(1014, "Преп. Параскеви Терновської; мучч. Назарія й ін.");
		dni.put(1015, "Преп. Євтимія; препмуч. Лукіяна");
		dni.put(1016, "Муч. Лонгіна, сотника");
		dni.put(1017, "Прор. Осії; препмуч. Андрія Крит.");
		dni.put(1018, "<b>† Ап. і єванг. Луки</b>");
		dni.put(1019, "Свщмч. Теодора Ромжі; прор. Йоіла; муч. Уара");
		dni.put(1020, "Вмуч. Артемія");
		dni.put(1021, "Преп. Іларіона Великого");
		dni.put(1022, "Св. Аверкія, єп. Єрапольського");
		dni.put(1023, "Ап. Якова, брата Господнього");
		dni.put(1024, "Мучч. Арети й інших");
		dni.put(1025, "Мучч. Маркіяна й Мартирія");
		dni.put(1026, "<b>⊕ Вмуч. Димитрія Мироточця</b>");
		dni.put(1027, "Муч. Нестора; мучч. Капітоліни й Єротіїди");
		dni.put(1028, "Мучч. Терентія і Неоніли; муч. Параскеви, що в Іконії");
		dni.put(1029, "Преп. Анастасії; преп. Аврамія Ростовського");
		dni.put(1030, "Мучч. Зиновія і Зиновії, сестри його");
		dni.put(1031, "Апп. Стахія, Амплія й інших; муч. Елімаха");
	}

	private void initVeresen() {
		dni.put(901,
				"<b>† Початок Церковного року</b>; преп. Симеона Стовпника");
		dni.put(902, "Муч. Маманта; св. Івана Посника, патр. Царгород.");
		dni.put(903, "Свщмч. Антима, єп. Никодимії; преп. Теоктиста");
		dni.put(904, "Свщмч. Вавили, єп. Антіохії; прор. Мойсея Боговидця");
		dni.put(905, "Прор. Захарії, батька св. Йоана Христ.");
		dni.put(906, "Чудо арх. Михаїла; мучч. Євдоксія й ін.");
		dni.put(907, "<i>Передсв. Різдва Пресв. Богородиці</i>; Муч. Созонта");
		dni.put(908, "<b><r>⊕ РІЗДВО ПРЕСВЯТОЇ БОГОРОДИЦІ</r></b>");
		dni.put(909, "Праведних Йоакима й Анни; муч. Северіяна");
		dni.put(910, "Мучч. Минодори, Митродори й Німфодори");
		dni.put(911, "Преп. Теодори Олександрійської");
		dni.put(912,
				"<i>Віддання Різдва Пресв. Богородиці</i>; Свщмч. Автонома");
		dni.put(913, "<i>Передсвяття Воздвиження</i>; Свщмч. Корнилія");
		dni.put(914, "<b><r>⊕ ВОЗДВИЖЕННЯ ХРЕСТА ГОСПОДНЬОГО</r></b>");
		dni.put(915, "Вмуч. Микити");
		dni.put(916, "Вмуч. Євфимії");
		dni.put(917, "Мучч. Софії та її дочок: Віри, Надії й Любові");
		dni.put(918, "Преп. Євменія, єп. Гортинського");
		dni.put(919, "Мучч. Трохима, Саватія і Дорімедонта");
		dni.put(920, "Муч. Євстратія й ін.; мучч. Михаїла і Теодора Чернігів.");
		dni.put(921, "<i>Віддання Воздвиження</i>; Ап. Кондрата");
		dni.put(922, "Свщмч. Фоки, єп. Синопійського; прор. Йони");
		dni.put(923, "Зачаття св. Йоана Христителя");
		dni.put(924, "Первомуч. Теклі, рівноап.");
		dni.put(925, "Преп. Єфросинії");
		dni.put(926, "<b>⊕ Ап. Йоана Богослова</b>");
		dni.put(927, "Муч. Калістрата та ін.; преп. Ніла Ґроттафер.");
		dni.put(928, "Іспов. Харитона; Вячеслава");
		dni.put(929, "Преп. Киріяка, самітника");
		dni.put(930, "Свщмч. Григорія, єп. Великої Вірменії");
	}

	private void initRukhomiSvyata() {
		svyataRukhomi.put(-70, "<i><r>Митаря і Фарисея</r></i>");
		svyataRukhomi.put(-63, "<i><r>Блудного сина</r></i>");
		svyataRukhomi.put(-57, "<i>Заупокійна</i>");
		svyataRukhomi.put(-56, "<i><r>М'ясопусна</r></i>");
		svyataRukhomi.put(-49, "<i><r>Сиропусна</r></i>");
		svyataRukhomi.put(-48, "<i>Початок Великого Посту</i>");
		svyataRukhomi.put(-42, "<i><r>1 посту. Неділя Православ'я</r></i>");
		svyataRukhomi.put(-35, "<i><r>2 Посту</r></i>");
		svyataRukhomi.put(-28, "<i><r>3 Посту. Хрестопоклінна</r></i>");
		svyataRukhomi.put(-21, "<i><r>4 Посту</r></i>");
		svyataRukhomi.put(-18, "<i>Поклони</i>");
		svyataRukhomi.put(-15, "<i>Акафістова</i>");
		svyataRukhomi.put(-14, "<i><r>5 посту</r></i>");
		svyataRukhomi.put(-8, "<i>Лазарева</i>");
		svyataRukhomi.put(-7,
				"<b><r>КВІТНА; † ВХІД ГОСПОДНІЙ В ЄРУСАЛИМ</r></b>");
		svyataRukhomi.put(-3 + FLAG_RUKHOME_REPLACE,
				"ВЕЛ. ЧЕТВЕР; <i>страсті</i>");
		svyataRukhomi.put(-2 + FLAG_RUKHOME_REPLACE_RED_DAY,
				"<r><b>ВЕЛ. П'ЯТНИЦЯ</b>; <i>Плащаниця</i></r>");
		svyataRukhomi.put(-1 + FLAG_RUKHOME_REPLACE, "ВЕЛ. СУБОТА");
		svyataRukhomi.put(0 + FLAG_RUKHOME_REPLACE_RED_DAY,
				"<b><r>⊕ ВОСКРЕСІННЯ ХРИСТОВЕ</r></b>");
		svyataRukhomi.put(1, "<b><r>СВ. ПОНЕДІЛОК</r></b>");
		svyataRukhomi.put(2, "<b><r>СВ. ВІВТОРОК</r></b>");
		svyataRukhomi.put(7, "<i><r>Томина</r></i>");
		svyataRukhomi.put(14, "<i><r>Мироносиць</r></i>");
		svyataRukhomi.put(21, "<i><r>Розслабленого</r></i>");
		svyataRukhomi.put(24, "<i>Переполовення</i>");
		svyataRukhomi.put(28, "<i><r>Самарянки</r></i>");
		svyataRukhomi.put(35, "<i><r>Сліпородженого</r></i>");
		svyataRukhomi.put(39 + FLAG_RUKHOME_REPLACE_RED_DAY,
				"<b><r>⊕ ВОЗНЕСІННЯ ГОСПОДНЄ</r></b>");
		svyataRukhomi.put(42, "<i><r>Св. Отців І Вселенського Собору</r></i>");
		svyataRukhomi.put(48, "<i>Заупокійна</i>");
		svyataRukhomi.put(49 + FLAG_RUKHOME_REPLACE_RED_DAY,
				"<b><r>⊕ ЗІСЛАННЯ СВЯТОГО ДУХА</r></b>");
		svyataRukhomi.put(50 + FLAG_RUKHOME_REPLACE_RED_DAY,
				"<b><r>⊕ ПРЕСВЯТОЇ ТРОЙЦІ</r></b>");
		svyataRukhomi.put(56, "<i><r>Всіх святих</r></i>");
		svyataRukhomi.put(60 + FLAG_RUKHOME_RED_DAY,
				"<b><r>⊕ Пресвятої Євхаристії</r></b>");
		svyataRukhomi.put(68 + FLAG_RUKHOME_RED_DAY,
				"<b><r>† Христа Чоловіколюбця</r></b>");
		svyataRukhomi.put(69, "<b>† Співстр. Пресв. Богородиці</b>");
	}

	private void initVelykdenDate() {
		dataVelykodnya.put(2003, 427);
		dataVelykodnya.put(2004, 411);
		dataVelykodnya.put(2005, 501);
		dataVelykodnya.put(2006, 423);
		dataVelykodnya.put(2007, 408);
		dataVelykodnya.put(2008, 427);
		dataVelykodnya.put(2009, 419);
		dataVelykodnya.put(2010, 404);
		dataVelykodnya.put(2011, 424);
		dataVelykodnya.put(2012, 415);
		dataVelykodnya.put(2013, 505);
		dataVelykodnya.put(2014, 420);
		dataVelykodnya.put(2015, 412);
		dataVelykodnya.put(2016, 501);
		dataVelykodnya.put(2017, 416);
		dataVelykodnya.put(2018, 408);
		dataVelykodnya.put(2019, 428);
		dataVelykodnya.put(2020, 419);
		dataVelykodnya.put(2021, 502);
		dataVelykodnya.put(2022, 424);
		dataVelykodnya.put(2023, 416);
		dataVelykodnya.put(2024, 505);
		dataVelykodnya.put(2025, 420);
		dataVelykodnya.put(2026, 412);
		dataVelykodnya.put(2027, 505);
		dataVelykodnya.put(2028, 416);
		dataVelykodnya.put(2029, 408);
		dataVelykodnya.put(2030, 428);
		dataVelykodnya.put(2031, 413);
		dataVelykodnya.put(2032, 502);
		dataVelykodnya.put(2033, 424);
		dataVelykodnya.put(2034, 409);
		dataVelykodnya.put(2035, 429);
		dataVelykodnya.put(2036, 420);
		dataVelykodnya.put(2037, 405);
		dataVelykodnya.put(2038, 425);
		dataVelykodnya.put(2039, 417);
		dataVelykodnya.put(2040, 506);
		dataVelykodnya.put(2041, 421);
		dataVelykodnya.put(2042, 413);
		dataVelykodnya.put(2043, 503);
		dataVelykodnya.put(2044, 424);
		dataVelykodnya.put(2045, 409);
		dataVelykodnya.put(2046, 429);
		dataVelykodnya.put(2047, 421);
		dataVelykodnya.put(2048, 405);
		dataVelykodnya.put(2049, 425);
		dataVelykodnya.put(2050, 417);
	}

}
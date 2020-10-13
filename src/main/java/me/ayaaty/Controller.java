package me.ayaaty;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Controller {
    MediaPlayer alGhamdiMediaPlayer;
    MediaPlayer abbadMediaPlayer;
    MediaPlayer alAjmyMediaPlayer;
    boolean isSaadMPPlaying = false;
    boolean isFaresMPPlaying = false;
    boolean isAhmedMPPlaying = false;
    @FXML
    Label prayer1, prayer2, prayer3, prayer4, prayer5, quranicSupplication, greatDhikr;
    @FXML
    Button listenToQuranicSurahAbbad, listenToQuranicSurahAlGhamdi, listenToQuranicSurahAlAjmy;

    String[] quranicSupplications = new String[]{"ربنا أفرغ علينا صبرا وثبت أقدامنا وانصرنا على القوم الكافرين", "ربنا لا تؤاخذنا إن نسينا أو أخطأنا ربنا ولا تحمل علينا إصرا كما حملته على الذين من قبلنا ربنا ولا تحملنا ما لا طاقة لنا به واعف عنا واغفر لنا وارحمنا أنت مولانا فانصرنا على القوم الكافرين", "ربنا لا تزغ قلوبنا بعد إذ هديتنا وهب لنا من لدنك رحمة إنك أنت الوهاب", "ربنا إننا آمنا فاغفر لنا ذنوبنا وقنا عذاب النار", "رب هب لي من لدنك ذرية طيبة إنك سميع الدعاء", "ربنا آمنا بما أنزلت واتبعنا الرسول فاكتبنا مع الشاهدين", "ربنا اغفر لنا ذنوبنا وإسرافنا في أمرنا وثبت أقدامنا وانصرنا على القوم الكافرين", "ربنا ما خلقت هذا باطلا سبحانك فقنا عذاب النار ربنا إنك من تدخل النار فقد أخزيته وما للظالمين من أنصار ربنا إننا سمعنا مناديا ينادي للإيمان أن آمنوا بربكم فآمنا ربنا فاغفر لنا ذنوبنا وكفر عنا سيئاتنا وتوفنا مع الأبرار ربنا وآتنا ما وعدتنا على رسلك ولا تخزنا يوم القيامة إنك لا تخلف الميعاد", "ربنا ظلمنا أنفسنا وإن لم تغفر لنا وترحمنا لنكونن من الخاسرين", "ربنا لا تجعلنا مع القوم الظالمين", "ربنا أفرغ علينا صبرا وتوفنا مسلمين", "ربنا لا تجعلنا فتنة للقوم الظالمين ونجنا برحمتك من القوم الكافرين", "رب إني أعوذ بك أن أسألك ما ليس لي به علم وإلا تغفر لي وترحمني أكن من الخاسرين", "رب اجعلني مقيم الصلاة ومن ذريتي ربنا وتقبل دعاء", "ربنا اغفر لي ولوالدي وللمؤمنين يوم يقوم الحساب", "رب أدخلني مدخل صدق وأخرجني مخرج صدق واجعل لي من لدنك سلطانا نصيرا", "ربنا آتنا من لدنك رحمة وهيئ لنا من أمرنا رشدا", "رب اشرح لي صدري ويسر لي أمري واحلل عقدة من لساني يفقهوا قولي", "رب زدني علما", "لا إله إلا أنت سبحانك إني كنت من الظالمين", "رب لا تذرني فردا وأنت خير الوارثين", "رب أعوذ بك من همزات الشياطين وأعوذ بك رب أن يحضرون", "ربنا آمنا فاغفر لنا وارحمنا وأنت خير الراحمين", "رب اغفر وارحم وأنت خير الراحمين"};
    String[] greatAdhkaar = new String[]{"سبحان الله", "سبحان الله وبحمده", "سبحان الله والحمد لله", "سبحان الله العظيم وبحمده", "سبحان الله وبحمده ، سبحان الله العظيم", "لا إله إلا الله وحده لا شريك له، له الملك وله الحمد وهو على كل شيء قدير", "لا حول ولا قوة إلا بالله", "الحمد لله رب العالمين", "اللهم صل وسلم وبارك على سيدنا محمد", "أستغفر الله", "سبحان الله، والحمد لله، ولا إله إلا الله، والله أكبر", "لا إله إلا الله", "الله أكبر", "سبحان الله ، والحمد لله ، ولا إله إلا الله ، والله أكبر ، اللهم اغفر لي ، اللهم ارحمني ، اللهم ارزقني", "الحمد لله حمدا كثيرا طيبا مباركا فيه", "الله أكبر كبيرا ، والحمد لله كثيرا ، وسبحان الله بكرة وأصيلا", "اللهم صل على محمد وعلى آل محمد كما صليت على إبراهيم , وعلى آل إبراهيم إنك حميد مجيد , اللهم بارك على محمد وعلى آل محمد كما باركت على إبراهيم وعلى آل إبراهيم إنك حميد مجيد"};

    public void initialize() {
        Document document = null;
        try {
            document = Jsoup.connect("http://www.habous.gov.ma/horaire%20de%20priere/horaire-pub.php?ville=1").get();
            Elements newsHeadlines = document.select(".horaire");
            Iterator iterator = newsHeadlines.iterator();
            while (iterator.hasNext()) {
                Element headline = (Element) iterator.next();
                String all = headline.html();
                String[] lines = all.split("\n");
                all = "";
                String[] prayers = lines;
                String line;
                for (int i = 0; i < lines.length; i += 1) {
                    line = prayers[i];
                    if (line.contains("td")) {
                        all = all + line + "\n";
                    }
                }

                lines = all.split("\n");
                all = "";
                prayers = lines;

                for (int i = 0; i < lines.length; i += 1) {
                    line = prayers[i];
                    char[] lineArray = line.toCharArray();
                    boolean canCopy = false;
                    for (int j = 0; j < lineArray.length; j += 1) {
                        char Char = lineArray[j];
                        if (Char == '<') {
                            canCopy = false;
                        }
                        if (Char == '>') {
                            canCopy = true;
                        }
                        if (canCopy) {
                            all = all + Char;
                        }
                    }
                    all = all + "\n";
                }
                all = all.replace("<", "");
                all = all.replace(">", "");
                all = all.replace(" ", "");
                prayers = all.split("\n");
                this.prayer1.setText(prayers[1]);
                this.prayer2.setText(prayers[5]);
                this.prayer3.setText(prayers[7]);
                this.prayer4.setText(prayers[9]);
                this.prayer5.setText(prayers[11]);
                this.showAnotherGreatDhikr();
                this.showAnotherQuranicSupplication();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void saad() {
        if (this.alAjmyMediaPlayer != null && this.alAjmyMediaPlayer.getStatus().equals(Status.PLAYING)) {
            this.alAjmyMediaPlayer.stop();
        }
        if (this.abbadMediaPlayer != null && this.abbadMediaPlayer.getStatus().equals(Status.PLAYING)) {
            this.abbadMediaPlayer.stop();
        }
        this.isSaadMPPlaying = !this.isSaadMPPlaying;
        if (this.isSaadMPPlaying) {
            Random random = new Random();
            int number = random.nextInt(114);
            ++number;
            String name = String.format("%03d", number);
            String link = "https://download.tvquran.com/download/TvQuran.com__Al-Ghamdi/" + name + ".mp3";
            Media media = new Media(link);
            this.alGhamdiMediaPlayer = new MediaPlayer(media);
            this.alGhamdiMediaPlayer.play();
        } else {
            this.alGhamdiMediaPlayer.stop();
        }

    }

    public void fares() {
        if (this.alAjmyMediaPlayer != null && this.alAjmyMediaPlayer.getStatus().equals(Status.PLAYING)) {
            this.alAjmyMediaPlayer.stop();
        }

        if (this.alGhamdiMediaPlayer != null && this.alGhamdiMediaPlayer.getStatus().equals(Status.PLAYING)) {
            this.alGhamdiMediaPlayer.stop();
        }

        this.isFaresMPPlaying = !this.isFaresMPPlaying;
        if (this.isFaresMPPlaying) {
            Random random = new Random();
            int number = random.nextInt(114);
            ++number;
            String name = String.format("%03d", number);
            String link = "https://download.tvquran.com/download/TvQuran.com__Fares.Abbad/" + name + ".mp3";
            Media media = new Media(link);
            this.abbadMediaPlayer = new MediaPlayer(media);
            this.abbadMediaPlayer.play();
        } else {
            this.abbadMediaPlayer.stop();
        }

    }

    public void ahmed() {
        if (this.alGhamdiMediaPlayer != null && this.alGhamdiMediaPlayer.getStatus().equals(Status.PLAYING)) {
            this.alGhamdiMediaPlayer.stop();
        }
        if (this.abbadMediaPlayer != null && this.abbadMediaPlayer.getStatus().equals(Status.PLAYING)) {
            this.abbadMediaPlayer.stop();
        }
        this.isAhmedMPPlaying = !this.isAhmedMPPlaying;
        if (this.isAhmedMPPlaying) {
            Random random = new Random();
            int number = random.nextInt(114);
            ++number;
            String name = String.format("%03d", number);
            String link = "https://download.tvquran.com/download/TvQuran.com__Al-Ajmy/" + name + ".mp3";
            Media media = new Media(link);
            this.alAjmyMediaPlayer = new MediaPlayer(media);
            this.alAjmyMediaPlayer.play();
        } else {
            this.alAjmyMediaPlayer.stop();
        }

    }

    public void setTextIn(Label label, String text) {
        int wordsCounter = 0;
        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i += 1) {
            String word = words[i];
            if (wordsCounter != 0 && wordsCounter % 10 == 0) {
                label.setText(label.getText() + word + " \n");
            } else {
                label.setText(label.getText() + word + " ");
            }
            ++wordsCounter;
        }

    }

    public void showAnotherQuranicSupplication() {
        this.quranicSupplication.setText("");
        Random random = new Random();
        int randomNumber = random.nextInt(24);
        this.setTextIn(this.quranicSupplication, this.quranicSupplications[randomNumber]);
    }

    public void showAnotherGreatDhikr() {
        this.greatDhikr.setText("");
        Random random = new Random();
        int randomNumber = random.nextInt(17);
        this.setTextIn(this.greatDhikr, this.greatAdhkaar[randomNumber]);
    }
}

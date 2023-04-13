package main.java.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdurmont.emoji.EmojiParser;
import main.java.dto.*;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;


public class StartApp {

    static HttpClient client = HttpClient.newHttpClient();
    static HttpRequest request;
    static HttpResponse response;

    public static void run() {

        setBotDescriptions(client);
        setBotCommands(client);

        while (true) {


            try {
                request = HttpRequest.newBuilder()
                        .uri(new URI("https://api.telegram.org/bot6252522173:AAGIXaRzgosBXwka5yr0RWwLLa2I3bldjGY/getUpdates"))
                        .headers("Content-Type", "application/json")
                        .GET()
                        .build();

                Thread.sleep(500);

            } catch (Exception e) {
                System.out.println("Something happened in request" + e);
                throw new RuntimeException(e);
            }

            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body().toString());
            } catch (Exception e) {
                System.out.println("Something happened in response" + e);
                throw new RuntimeException(e);
            }

            ObjectMapper mapper = new ObjectMapper();
            ResponseTelegram responseTelegram;
            SendMessage message = null;
            try {
                responseTelegram = mapper.readValue((String) response.body(), ResponseTelegram.class);
            } catch (Exception e) {
                System.out.println("Something happened in mapper");
                throw new RuntimeException(e);
            }

            String markAsProcessed = "";

            for (int i = 0; i < responseTelegram.updates.size(); i++) {
                Update update = responseTelegram.updates.get(i);
                System.out.println(update);
                if (update.message == null) {

                    if (update.callbackQuery.data.equals("/start")) {
                        message = startMessageCallBack(update);
                    }
                    if (update.callbackQuery.data.equals("/menu_from_sheff")) {
                        message = menuFromSheffCallBack(update);
                    }
                    if (update.callbackQuery.data.equals("/contact_info")) {
                        message = showContactInfoCallBack(update);
                    }
                    if (update.callbackQuery.data.equals("/stock")) {
                        message = showStockCallBack(update);
                    }
                    if (update.callbackQuery.data.equals("/discount")) {
                        message = showDiscountStock(update);
                    }
                    if (update.callbackQuery.data.equals("/pork_steak")) {
                        message = showPorkSteak(update);
                    }
                    if (update.callbackQuery.data.equals("/order")) {
                        message = doOrder(update);
                    }
                    if (update.callbackQuery.data.equals("/beef_steak")) {
                        message = showBeefSteak(update);
                    }
                    if (update.callbackQuery.data.equals("/salad_beef")) {
                        message = showBeefSalad(update);
                    }
                    if (update.callbackQuery.data.equals("/salad_cesar")) {
                        message = showCesarSalad(update);
                    }
                    if (update.callbackQuery.data.equals("/breakfast")) {
                        message = showBreakfastCallback(update);
                    }
                    if (update.callbackQuery.data.equals("/breakfast_grandal")) {
                        message = showBreakfastGrandal(update);
                    }
                    if (update.callbackQuery.data.equals("/orange_fresh")) {
                        message = showFresh(update);
                    }
                    if (update.callbackQuery.data.equals("/breakfast_egs")) {
                        message = showBreakfastEgs(update);
                    }
                    if (update.callbackQuery.data.equals("/phone_number")) {
                        message = showPhoneNumber(update);
                    }
                    if (update.callbackQuery.data.equals("/happy_receipt")) {
                        message = showHappyReceipt(update);
                    }
                    if (update.callbackQuery.data.equals("/delivery")){
                        message = showDeliveryRulesCallback(update);
                    }
                    if (update.callbackQuery.data.equals("/work_time")){
                        message = showWorkTimeCallback(update);
                    }
                    if (update.callbackQuery.data.equals("/work_time")){
                        message = showHelpCallback(update);
                    }

                } else if (update.message.text != null) {
                    if (update.message.text.equals("/start")) {
                        message = showStartMessage(update);
                    }
                    if (update.message.text.equals("/menu_from_sheff")) {
                        message = showMenuFromSheff(update);
                    }
                    if (update.message.text.equals("/contact_info")) {
                        message = showContactInfo(update);
                    }
                    if (update.message.text.equals("/stock")) {
                        message = showStock(update);
                    }
                    if (update.message.text.equals("/breakfast")) {
                        message = showBreakfast(update);
                    }
                    if (update.message.text.equals("/delivery")) {
                        message = showDeliveryRules(update);
                    }
                    if (update.message.text.equals("/work_time")) {
                        message = showWorkTime(update);
                    }
                    if (update.message.text.equals("/help")) {
                        message = showHelp(update);
                    }

                } else {
                    message = new SendMessage(update.message.user.id, "Дана функція наразі не підтрімується");
                }

                try {
                    String messageForRequest = mapper.writeValueAsString(message);

                    try {
                        request = HttpRequest.newBuilder()
                                .uri(new URI("https://api.telegram.org/bot6252522173:AAGIXaRzgosBXwka5yr0RWwLLa2I3bldjGY/sendMessage"))
                                .headers("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.ofString(messageForRequest))
                                .build();
                        System.out.println("send");
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        System.out.println(response.body().toString());
                    } catch (Exception e) {
                        System.out.println("Something happened in response" + e);
                        throw new RuntimeException(e);
                    }

                    if (i == responseTelegram.updates.size() - 1) {
                        markAsProcessed = String.valueOf(update.update_id + 1);
                        System.out.println(markAsProcessed);
                    }

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                request = HttpRequest.newBuilder()
                        .uri(new URI("https://api.telegram.org/bot6252522173:AAGIXaRzgosBXwka5yr0RWwLLa2I3bldjGY/getUpdates?offset=" + markAsProcessed))
                        .headers("Content-Type", "application/json")
                        .GET()
                        .build();

            } catch (Exception e) {
                System.out.println("Something happened in request" + e);
                throw new RuntimeException(e);
            }

            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("This is response" + response.body().toString());
            } catch (Exception e) {
                System.out.println("Something happened in response" + e);
                throw new RuntimeException(e);
            }
        }
    }
    private static SendMessage showHelpCallback (Update update){
        String answerToUser = EmojiParser.parseToUnicode("З метою взаємодії з чатом необхідно обирати ті пункти " +
                "меню, які зацікавили та натискати на екрані відповідну кнопку. В разі натискання на кнопку \"Замовити\" наші співробітніки, як найскоріше" +
                " зв'яжуться та обов'язково зможуть допомогти");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Повернутися до головного меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);

        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }
    private static SendMessage showHelp(Update update){
        String answerToUser = EmojiParser.parseToUnicode("З метою взаємодії з чатом необхідно обирати ті пункти " +
                "меню, які зацікавили та натискати на екрані відповідну кнопку. В разі натискання на кнопку \"Замовити\" наші співробітніки, як найскоріше" +
                " зв'яжуться та обов'язково зможуть допомогти");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Повернутися до головного меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);

        return new SendMessage(update.message.user.id, answerToUser, markup);
    }
    private static SendMessage showWorkTimeCallback (Update update){

        String answerToUser = EmojiParser.parseToUnicode(":clock9:  :clock9:  :clock9:  :clock9:  :clock9:\n" +
                "Ми працюємо з 9 години ранку до 17 години вечора з понеділка по п'ятницю і будемо щиро раді " +
                "вітати тебе у нас\n" +
                ":clock5:  :clock5:  :clock5:  :clock5:  :clock5:");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Повернутися до головного меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);

        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);


    }
    private static SendMessage showWorkTime(Update update){

        String answerToUser = EmojiParser.parseToUnicode(":clock9:  :clock9:  :clock9:  :clock9:  :clock9:\n" +
                "Ми працюємо з 9 години ранку до 17 години вечора з понеділка по п'ятницю і будемо щиро раді " +
                "вітати тебе у нас\n" +
                ":clock5:  :clock5:  :clock5:  :clock5:  :clock5:");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Повернутися до головного меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);

        return new SendMessage(update.message.user.id, answerToUser, markup);

    }
    private static SendMessage showDeliveryRulesCallback(Update update){

        String answerToUser = EmojiParser.parseToUnicode(":shopping_trolley:   :shopping_trolley:   :shopping_trolley:   :shopping_trolley:   :shopping_trolley:\n" +
                "Якщо зголоднів та зовсім не має часу, натисни замовити і ми із задоволенням :iphone: зателефонуемо та підберемо " +
                "актуальну зброю, щоб не залишити голоду жодного шансу\n" +
                "Доставляемо у БЦ \"Глорія\" безкоштовно");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Замовити", "/order"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);

    }
    private static SendMessage showDeliveryRules(Update update) {

        String answerToUser = EmojiParser.parseToUnicode(":shopping_trolley:   :shopping_trolley:   :shopping_trolley:   :shopping_trolley:   :shopping_trolley:\n" +
                "Якщо зголоднів та зовсім не має часу, натисни замовити і ми із задоволенням :iphone: зателефонуемо та підберемо " +
                "актуальну зброю, щоб не залишити голоду жодного шансу\n" +
                "Доставляемо у БЦ \"Глорія\" безкоштовно");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Замовити", "/order"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        return new SendMessage(update.message.user.id, answerToUser, markup);

    }

    private static SendMessage showBreakfastEgs(Update update) {
        try {

            sendPhoto(update, new URL("https://images.unian.net/photos/2020_09/thumb_files/1000_545_1600367863-5570.jpg").toURI().toString());

        } catch (URISyntaxException | MalformedURLException e) {
            System.out.println("dawn here");
            throw new RuntimeException(e);
        }

        String answerToUser = EmojiParser.parseToUnicode(":fork_knife_plate:   :fork_knife_plate:   :fork_knife_plate:   :fork_knife_plate:   :fork_knife_plate:\n" +
                "Класичний сніданок на всі часи\n" +
                "Вага - 190 г\n" +
                "Вартість - 39 гр\n\n" +
                "Можливо замовити додатки:\n" +
                "Зелень\t\t\t\t\t\t\t\t\t\t\t\t5г   \t\t\t\t9 гр\n" +
                "Томат  \t\t\t\t\t\t\t\t\t\t\t\t50г\t\t\t\t14 гр\n" +
                "Бекон  \t\t\t\t\t\t\t\t\t\t\t\t50г\t\t\t\t19 гр\n" +
                "Сальсічіо        70г \t\t\t\t29 гр\n" +
                "Моцарела     \t\t50г\t\t  19 гр\n" +
                "Яйце              \t\t1шт  \t\t14 гр\n" +
                "Тост              \t\t\t1шт\t\t  9  гр\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Замовити", "/order"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);

        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);

    }

    private static SendMessage showBreakfastCallback(Update update) {

        String answerToUser = EmojiParser.parseToUnicode(":male_cook:  :male_cook:  :male_cook:  :male_cook:  :male_cook:\n" +
                "Меню діє з 9.00 до 12.00\n" +
                "Готуємо для Вас за 8-15 хвилин або на обраний та зручний час\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Сніданок \"Grandal\" ", "/breakfast_grandal"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Омлет класичний або яєчня з двох яєць", "/breakfast_egs"));
        ArrayList<InlineKeyboardButton> buttonsInThirdRow = new ArrayList<>();
        buttonsInThirdRow.add(new InlineKeyboardButton("Фреш з апельсинів", "/orange_fresh"));
        ArrayList<InlineKeyboardButton> buttonsInFourthRow = new ArrayList<>();
        buttonsInFourthRow.add(new InlineKeyboardButton("Повернутися до головного меню", "/start"));

        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        markup.inline_keyboard.add(buttonsInThirdRow);
        markup.inline_keyboard.add(buttonsInFourthRow);

        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static SendMessage showBreakfast(Update update) {

        String answerToUser = EmojiParser.parseToUnicode(":male_cook:  :male_cook:  :male_cook:  :male_cook:  :male_cook:\n" +
                "Меню діє з 9.00 до 12.00\n" +
                "Готуємо для Вас за 8-15 хвилин або на обраний та зручний час\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Сніданок \"Grandal\" ", "/breakfast_grandal"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Омлет класичний або яєчня з двох яєць", "/breakfast_egs"));
        ArrayList<InlineKeyboardButton> buttonsInThirdRow = new ArrayList<>();
        buttonsInThirdRow.add(new InlineKeyboardButton("Фреш з апельсинів", "/orange_fresh"));
        ArrayList<InlineKeyboardButton> buttonsInFourthRow = new ArrayList<>();
        buttonsInFourthRow.add(new InlineKeyboardButton("Повернутися до головного меню", "/start"));

        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        markup.inline_keyboard.add(buttonsInThirdRow);
        markup.inline_keyboard.add(buttonsInFourthRow);


        return new SendMessage(update.message.user.id, answerToUser, markup);

    }

    private static SendMessage showBreakfastGrandal(Update update) {

        try {

            sendPhoto(update, new URL("https://ibb.co/JW1PJH5").toURI().toString());

        } catch (URISyntaxException | MalformedURLException e) {
            System.out.println("dawn here");
            throw new RuntimeException(e);
        }

        String answerToUser = EmojiParser.parseToUnicode(":bacon:   :egg:   :baguette_bread:   :egg:   :bacon:\n" +
                "Яєчня з трьох яєць, сальчіо,печериці, бекон та томат-гриль,квасоля,тости\n" +
                "Вага страви - 270 гр\n" +
                "Вартість - 129 гр\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Замовити", "/order"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);

        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static SendMessage showFresh(Update update) {
        try {

            sendPhoto(update, new URL("https://media.istockphoto.com/id/120027056/photo/tumbler-glass-of-orange-juice-resting-on-a-white-surface.jpg?s=612x612&w=0&k=20&c=0DzmVhWawclIqlCs35XhZ9ff9Y0abtlMyj0kZ27-eGU=").toURI().toString());

        } catch (URISyntaxException | MalformedURLException e) {
            System.out.println("dawn here");
            throw new RuntimeException(e);
        }

        String answerToUser = EmojiParser.parseToUnicode(":tangerine:   :tangerine:   :tangerine:   :tangerine:   :tangerine:\n" +
                "Свіжовичавлений сік зі стиглих апельсинів для Вас\n" +
                "Об'єм - 200 мл\n" +
                "Вартість - 59 гр\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Замовити", "/order"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);

        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);

    }

    private static SendMessage showDiscountStock(Update update) {
        String answerToUser = EmojiParser.parseToUnicode("Бажаємо приємного та легкого вечора, а тому " +
                "пропонуємо придбати будь-які наші кулінарні страви зі знижкою у 30% після 16 години кожного " +
                "робочого дня\n Завжди Ваші чарівники :smile:");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInSecondRow);
        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static SendMessage showStockCallBack(Update update) {
        String answerToUser = "Наразі діючі акції наступні:";
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Знижка після 16-00", "/discount"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Щастя у чеці", "/happy_receipt"));
        ArrayList<InlineKeyboardButton> buttonsInThirdRow = new ArrayList<>();
        buttonsInThirdRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        markup.inline_keyboard.add(buttonsInThirdRow);

        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static SendMessage showHappyReceipt(Update update) {
        String answerToUser = EmojiParser.parseToUnicode(":gift:  :gift:  :gift:  :gift:  :gift:\n" +
                "Звертай увагу на кожен чек і замість чарівного та " +
                "справжнього побажання, ти можеш виграти від нас безкоштовний сніданок, любий десерт на вибір " +
                "або 50% знижку на будь-яку страву з меню від шефу\n" +
                "Будь певен, тобі неодмінно повезе :wink:\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInSecondRow);
        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static SendMessage showStock(Update update) {
        String answerToUser = "Наразі діючі акції наступні:";
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Знижка після 16-00", "/discount"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Щастя у чеці", "/happy_receipt"));
        ArrayList<InlineKeyboardButton> buttonsInThirdRow = new ArrayList<>();
        buttonsInThirdRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        markup.inline_keyboard.add(buttonsInThirdRow);

        return new SendMessage(update.message.user.id, answerToUser, markup);
    }

    private static SendMessage showContactInfo(Update update) {

        String answerToUser = "Зв'язатися з нами можно за допомогою Instagram або по телефону";
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton("Наш аккаунт в Instagram", "/instagram").setUrl("instagram.com/grandalcafe/?igshid=YmMyMTA2M2Y%3D");
        buttonsInFirstRow.add(button);
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Телефон для зв'язку", "/phone_number"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);

        return new SendMessage(update.message.user.id, answerToUser, markup);
    }

    private static SendMessage showPhoneNumber(Update update) {

        String answerToUser = EmojiParser.parseToUnicode("На будь-які питання будемо раді Вам відповісти " +
                "за :iphone: +380503902030 або   :iphone: +380953300078\n\n" +
                "Із задоволенням чекаємо на Ваш дзвінок");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);

        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static SendMessage showContactInfoCallBack(Update update) {

        String answerToUser = "Зв'язатися з нами можно за допомогою Instagram або по телефону";
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton("Наш аккаунт в Instagram", "/instagram").setUrl("instagram.com/grandalcafe/?igshid=YmMyMTA2M2Y%3D");
        buttonsInFirstRow.add(button);
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Телефон для зв'язку", "/phone_number"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);

        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static SendMessage menuFromSheffCallBack(Update update) {
        String answerToUser = EmojiParser.parseToUnicode(":male_cook:  :male_cook:  :male_cook:  :male_cook:  :male_cook:\n" +
                "Меню від шефу діє з 12.00 до 16.00\n" +
                "Готуємо для Вас за 15-20 хвилин або на обраний та зручний час\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Стейк зі свинини з овочами", "/pork_steak"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Рібай стейк", "/beef_steak"));
        ArrayList<InlineKeyboardButton> buttonsInFourthRow = new ArrayList<>();
        buttonsInFourthRow.add(new InlineKeyboardButton("Салат \"Цезар\" з індичкою", "/salad_cesar"));
        ArrayList<InlineKeyboardButton> buttonsInThirdRow = new ArrayList<>();
        buttonsInThirdRow.add(new InlineKeyboardButton("Теплий салат з яловичиною", "/salad_beef"));
        ArrayList<InlineKeyboardButton> buttonsInFifthRow = new ArrayList<>();
        buttonsInFifthRow.add(new InlineKeyboardButton("Повернутися до головного меню", "/start"));

        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        markup.inline_keyboard.add(buttonsInThirdRow);
        markup.inline_keyboard.add(buttonsInFourthRow);
        markup.inline_keyboard.add(buttonsInFifthRow);

        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static SendMessage showStartMessage(Update update) {

        String answerToUser = "Вітаю, " + update.message.user.first_name + ", у інформаційному боті " +
                "підтрімки cafe Grandal. Чим можу допомогти? Оберіть необхідний пункт нижче та натисніть";

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Меню від шефа :man_cook:"), "/menu_from_sheff"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Сніданки :croissant:"), "/breakfast"));
        buttonsInSecondRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Зв'язок :telephone_receiver:"), "/contact_info"));
        ArrayList<InlineKeyboardButton> buttonsInThirdRow = new ArrayList<>();
        buttonsInThirdRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Акції :star:"), "/stock"));
        buttonsInThirdRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Доставка у БЦ :classical_building:"), "/delivery"));
        ArrayList<InlineKeyboardButton> buttonsInFourthRow = new ArrayList<>();
        buttonsInFourthRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Час роботи :alarm_clock:"), "/work_time"));
        buttonsInFourthRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Допомога з чатом :sos:"), "/help"));
        ArrayList<InlineKeyboardButton> buttonsInFifthRow = new ArrayList<>();
        buttonsInFifthRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Побажання/зауваження :black_nib:"), "/advice"));

        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        markup.inline_keyboard.add(buttonsInThirdRow);
        markup.inline_keyboard.add(buttonsInFourthRow);
        markup.inline_keyboard.add(buttonsInFifthRow);

        return new SendMessage(update.message.user.id, answerToUser, markup);
    }

    private static SendMessage startMessageCallBack(Update update) {

        String answerToUser = "Вітаю, " + update.callbackQuery.user.first_name + ", у інформаційному боті " +
                "підтрімки cafe Grandal. Чим можу допомогти? Оберіть необхідний пункт нижче та натисніть";

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Меню від шефа :man_cook:"), "/menu_from_sheff"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Сніданки :croissant:"), "/breakfast"));
        buttonsInSecondRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Зв'язок :telephone_receiver:"), "/contact_info"));
        ArrayList<InlineKeyboardButton> buttonsInThirdRow = new ArrayList<>();
        buttonsInThirdRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Акції :star:"), "/stock"));
        buttonsInThirdRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Доставка у БЦ :classical_building:"), "/delivery"));
        ArrayList<InlineKeyboardButton> buttonsInFourthRow = new ArrayList<>();
        buttonsInFourthRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Час роботи :alarm_clock:"), "/work_time"));
        buttonsInFourthRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Допомога з чатом :sos:"), "/help"));
        ArrayList<InlineKeyboardButton> buttonsInFifthRow = new ArrayList<>();
        buttonsInFifthRow.add(new InlineKeyboardButton(EmojiParser.parseToUnicode("Побажання/зауваження :black_nib:"), "/advice"));

        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        markup.inline_keyboard.add(buttonsInThirdRow);
        markup.inline_keyboard.add(buttonsInFourthRow);
        markup.inline_keyboard.add(buttonsInFifthRow);

        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static SendMessage doOrder(Update update) {
        String answerToUser = EmojiParser.parseToUnicode("Протягом найближчого часу, " +
                update.callbackQuery.user.first_name + ", наші співробітники :telephone_receiver: зв'яжуться з метою уточнення бажаного часу " +
                "замовлення та всіх маленьких нюансів.\n" +
                "Щиро дякуємо за замовлення :receipt: :smiling_face_with_smiling_eyes_and_three_hearts:");

        System.out.println(update.callbackQuery.data);

        orderNotification(update);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static void orderNotification(Update update) {

        ObjectMapper mapper = new ObjectMapper();
        String message = "Замовлення від " + update.callbackQuery.user.first_name + " " + update.callbackQuery.user.last_name +
                "\nзв'язатися за юзернеймом @" + update.callbackQuery.user.username +
                ", якщо видсутній юзернейм шукати по імені та прізвищу в телеграмі";
        String messageForRequest = "";
        try {
            messageForRequest = mapper.writeValueAsString(new SendMessage((long) 341959403, message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.telegram.org/bot6252522173:AAGIXaRzgosBXwka5yr0RWwLLa2I3bldjGY/sendMessage"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(messageForRequest))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body().toString());
        } catch (Exception e) {
            System.out.println("Something happened in response" + e);
            throw new RuntimeException(e);
        }

    }

    private static SendMessage showPorkSteak(Update update) {

        try {

            sendPhoto(update, new URL("https://ibb.co/SrwjncM").toURI().toString());

        } catch (URISyntaxException | MalformedURLException e) {
            System.out.println("dawn here");
            throw new RuntimeException(e);
        }

        String answerToUser = EmojiParser.parseToUnicode(":pig2:   :pig2:   :pig2:   :pig2:   :pig2:\n" +
                "Свинина на кістці та овочі-гриль з вершково-гірчичним соусом\n" +
                "Вага страви - 340 гр\n" +
                "Вартість - 169 гр\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Замовити", "/order"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static SendMessage showBeefSteak(Update update) {

        try {

            sendPhoto(update, new URL("https://ibb.co/fCmWMTM").toURI().toString());

        } catch (URISyntaxException | MalformedURLException e) {
            System.out.println("dawn here");
            throw new RuntimeException(e);
        }

        String answerToUser = EmojiParser.parseToUnicode(":cow2:   :cow2:   :cow2:   :cow2:   :cow2:\n" +
                "Стейк обсмажений за Вашим бажанням на подушці з вершкового шпинату, квасолі та " +
                "трюфельної олії\n" +
                "Вага страви - 320 гр\n" +
                "Вартість - 199 грн\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Замовити", "/order"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static void sendPhoto(Update update, String photoName) {
        ObjectMapper mapper = new ObjectMapper();

        String messageForRequest = "";
        try {
            messageForRequest = mapper.writeValueAsString(new SendPhoto(update.callbackQuery.user.id, photoName));
            System.out.println("lkz postman " + messageForRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.telegram.org/bot6252522173:AAGIXaRzgosBXwka5yr0RWwLLa2I3bldjGY/sendPhoto"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(messageForRequest))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body().toString());
        } catch (Exception e) {
            System.out.println("Something happened in response" + e);
            throw new RuntimeException(e);
        }

    }

    private static SendMessage showBeefSalad(Update update) {

        try {

            sendPhoto(update, new URL("https://ibb.co/C0dk7Sf").toURI().toString());

        } catch (URISyntaxException | MalformedURLException e) {
            System.out.println("dawn here");
            throw new RuntimeException(e);
        }

        String answerToUser = EmojiParser.parseToUnicode(":cow2:   :cow2:   :cow2:   :cow2:   :cow2:\n" +
                "Слайси стейку обсмажені за Вашим бажанням, печериці-гриль, мед, бальзамік та " +
                "трюфельний соус\n" +
                ":cow2:   :cow2:   :cow2:   :cow2:   :cow2:\n" +
                "Вага страви - 245 гр\n" +
                "Вартість - 129 грн\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Замовити", "/order"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static SendMessage showCesarSalad(Update update) {

        try {

            sendPhoto(update, new URL("https://static.1000.menu/res/640/img/content-v2/eb/79/19516/salat-cezar-klassicheskii-s-kuricei_1611309331_16_max.jpg").toURI().toString());

        } catch (URISyntaxException | MalformedURLException e) {
            System.out.println("dawn here");
            throw new RuntimeException(e);
        }


        String answerToUser = EmojiParser.parseToUnicode(":turkey:   :turkey:   :turkey:   :turkey:   :turkey:\n" +
                "Класичний салат з соусом  \"Цезар\" та запечене філе індички \n" +
                ":turkey:   :turkey:   :turkey:   :turkey:   :turkey:\n" +
                "Вага страви - 230 гр\n" +
                "Вартість - 119 грн\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Замовити", "/order"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Повернутися в головне меню", "/start"));
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        return new SendMessage(update.callbackQuery.user.id, answerToUser, markup);
    }

    private static SendMessage showMenuFromSheff(Update update) {
        String answerToUser = EmojiParser.parseToUnicode(":male_cook:  :male_cook:  :male_cook:  :male_cook:  :male_cook:\n" +
                "Меню від шефу діє з 12.00 до 16.00\n" +
                "Готуємо для Вас за 15-20 хвилин або на обраний та зручний час\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttonsInFirstRow = new ArrayList<>();
        buttonsInFirstRow.add(new InlineKeyboardButton("Стейк зі свинини з овочами", "/pork_steak"));
        ArrayList<InlineKeyboardButton> buttonsInSecondRow = new ArrayList<>();
        buttonsInSecondRow.add(new InlineKeyboardButton("Рібай стейк", "/beef_steak"));
        ArrayList<InlineKeyboardButton> buttonsInThirdRow = new ArrayList<>();
        buttonsInThirdRow.add(new InlineKeyboardButton("Теплий салат з яловичиною", "/salad_beef"));
        ArrayList<InlineKeyboardButton> buttonsInForthRow = new ArrayList<>();
        buttonsInForthRow.add(new InlineKeyboardButton("Салат \"Цезар\" з індичкою", "/salad_cesar"));
        ArrayList<InlineKeyboardButton> buttonsInFifthRow = new ArrayList<>();
        buttonsInFifthRow.add(new InlineKeyboardButton("Повернутися до головного меню", "/start"));


        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttonsInFirstRow);
        markup.inline_keyboard.add(buttonsInSecondRow);
        markup.inline_keyboard.add(buttonsInThirdRow);
        markup.inline_keyboard.add(buttonsInForthRow);
        markup.inline_keyboard.add(buttonsInFifthRow);


        return new SendMessage(update.message.user.id, answerToUser, markup);
    }

    private static void setBotDescriptions(HttpClient client) {

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> botDescription = new HashMap<>();
        String description = "Вітаємо Вас у інформаційному боті підтримки cafe Grandal. Оберіть пункт у меню, " +
                "що Вас цікавить. Завжди раді допомогти";
        botDescription.put("description", description);

        try {
            description = mapper.writeValueAsString(botDescription);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(botDescription);


        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.telegram.org/bot6252522173:AAGIXaRzgosBXwka5yr0RWwLLa2I3bldjGY/setMyDescription"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(description))
                    .build();

        } catch (Exception e) {
            System.out.println("Something happened when set bot commands" + e);
            throw new RuntimeException(e);
        }

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body().toString());
        } catch (Exception e) {
            System.out.println("Something happened when bot commands create" + e);
            throw new RuntimeException(e);
        }

    }

    private static void setBotCommands(HttpClient client) {

        ArrayList<BotCommand> commandsFirstRow = new ArrayList<>();
        commandsFirstRow.add(new BotCommand("/start", "Головне меню"));
        commandsFirstRow.add(new BotCommand("/menu_from_sheff", "Вибір страв від шефу"));
        commandsFirstRow.add(new BotCommand("/breakfast", "Сніданок від шефу"));
        commandsFirstRow.add(new BotCommand("/contact_info", "Контакти та соціальні мережі"));
        commandsFirstRow.add(new BotCommand("/stock", "Діючі акції"));
        commandsFirstRow.add(new BotCommand("/delivery", "Доставка замовлень у БЦ Глорія"));
        commandsFirstRow.add(new BotCommand("/work_time", "Час роботи закладу"));
        commandsFirstRow.add(new BotCommand("/help", "Допомога щодо роботи боту"));
        commandsFirstRow.add(new BotCommand("/advise", "Пропозиції та зауваження щодо меню та роботи"));

        HashMap<String, ArrayList<BotCommand>> botCom = new HashMap<>();
        botCom.put("commands", commandsFirstRow);


        ObjectMapper mapper = new ObjectMapper();
        String botCommands = "";

        try {
            botCommands = mapper.writeValueAsString(botCom);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(botCommands);

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.telegram.org/bot6252522173:AAGIXaRzgosBXwka5yr0RWwLLa2I3bldjGY/setMyCommands"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(botCommands))
                    .build();

        } catch (Exception e) {
            System.out.println("Something happened when set bot commands" + e);
            throw new RuntimeException(e);
        }

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body().toString());
        } catch (Exception e) {
            System.out.println("Something happened when bot commands create" + e);
            throw new RuntimeException(e);
        }

    }
}

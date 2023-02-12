package org.example.bot;

import org.example.films.Film;
import org.example.storage.Storage;
import org.example.weather.Weather;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    private final String BOT_NAME = "FirstGuzevkaBot";
    private final String BOT_TOKEN = "5768623612:AAHWze8RakmB104MdUxZk5SRZP-2W_0_Mlo";
    public Storage storage = new Storage();
    public Weather weather = new Weather();

    public Film film = new Film();

    public ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public Bot() {
        initKeyboard();
    }

    @Override
    public String getBotUsername() {
        return this.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return this.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                //Извлекаем из объекта сообщение пользователя
                Message inMess = update.getMessage();
                //Достаем из inMess id чата пользователя
                String chatId = inMess.getChatId().toString();
                //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                String response = parseMessage(inMess.getText());
                //Создаем объект класса SendMessage - наш будущий ответ пользователю
                SendMessage outMess = new SendMessage();

                //Добавляем в наше сообщение id чата а также наш ответ
                outMess.setChatId(chatId);
                outMess.setText(response);
                outMess.setReplyMarkup(replyKeyboardMarkup);

                //Отправка в чат
                execute(outMess);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String parseMessage(String textMsg) throws Exception{
        String response;

        //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if (textMsg.equals("/start"))
            response = "Привет! Меня зовут Бот Дашушка. \n\nЯ умею вкинуть рандомную цитату, сказать погоду, " +
                    "а также подсказать фильмец на вечер из топового списка 'IMDb Top 250 Movies' \n\n " +
                    "Жми на любую из кнопок и получай инфу :))))))";
        else if(textMsg.equals("/get") || textMsg.equals("Просвяти цитаткой"))
            response = storage.getRandQuote();
        else if(textMsg.equals("Погодку бы узнать..."))
            response = weather.getWeather();
        else if(textMsg.equals("Подскажи фильмец"))
            response = film.getRandFilm();
        else if (textMsg.equals("Привет"))
            response = "Хеллоу";
        else if(textMsg.equals("Как дела?"))
            response = "Лучше всех.";
        else
            response = "What do u mean ?";

        return response;
    }

    public void initKeyboard() {
        //Создаем объект будущей клавиатуры и выставляем нужные настройки
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(false); //скрываем после использования

        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        //Создаем один ряд кнопок и добавляем его в список
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRows.add(keyboardRow);
        //Добавляем одну кнопку с текстом "Просвяти" наш ряд
        keyboardRow.add(new KeyboardButton("Просвяти цитаткой"));
        keyboardRow.add(new KeyboardButton("Погодку бы узнать..."));
        keyboardRow.add(new KeyboardButton("Подскажи фильмец"));
        //добавляем лист с одним рядом кнопок в главный объект
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }
}



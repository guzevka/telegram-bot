package org.example.films;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Film {
    /*public String getFilm() throws Exception {
        Document doc = Jsoup.connect("https://www.imdb.com/chart/top").get();
        String nameOfSite = doc.title();
        String area = doc.getElementsByClass("titleColumn").text();
        String rating = doc.getElementsByClass("ratingColumn imdbRating").text();

        String[] arrArea = area.split(" ");
        String[] arrRating = rating.split(" ");
        String res = "";
        for (int i = 0; i < arrArea.length - 50; i++) {
            res += (arrArea[i] + " " + arrRating[i]) + "\n";
        }

        return "Название сайта: " + nameOfSite + "\n\n" + "Информация актуальна на сегодняшний день." + "\n\n" + res;
    }*/

    public Film() {
        parser("https://www.imdb.com/chart/top");
    }

    private ArrayList<String> filmList = new ArrayList<>();
    public String getRandFilm()
    {
        //получаем случайное значение в интервале от 0 до самого большого индекса
        int randValue = (int)(Math.random() * filmList.size());
        //Из коллекции получаем цитату со случайным индексом и возвращаем ее
        return filmList.get(randValue);
    }

    void parser(String strURL)
    {
        String classNmae = "titleColumn";
        Document doc = null;
        try {
            //Получаем документ нужной нам страницы
            doc = Jsoup.connect(strURL).maxBodySize(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Получаем группу объектов, обращаясь методом из Jsoup к определенному блоку
        Elements elFilm = doc.getElementsByClass(classNmae);

        for(Element el: elFilm) {
            filmList.add(el.text());
        }
    }
}

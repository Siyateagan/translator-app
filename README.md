# translator-app
Клиент Яндекс.Переводчика.

## Описание приложения
Три экрана, представляют собой acitvities. (Single activity и Navigation component не использованны, так как хотелось попробовать что-то новое)
1. Экран выбора языка: получает список доступных языков для перевода, помещает их в адаптер и сортирует в алфавитном порядке. SearchView позволяет найти нужный язык. При клике на язык возвращает на экран перевода. 
<img src="images/Screenshot_2020-04-15-18-26-09-083_siyateagan.example.translatorapp.jpg" width="300" >

2. Экран перевода: отправляет на сервер запрос, с задержкой 500 мс, чтобы сократить количество запросов, обрабатывает ошибки с помощью RxJava. Поддерживает Text-to-speech (Сделано скорее для галочки, чтобы приложение не было сухим). После получения ответа сверяется с базой данных и отображает, есть ли введенный текст в закладках. Можно очистить поля ввода и вывода текста, удалять и добавлять пару текстов в закладки.

<img src="images/Screenshot_2020-04-15-18-23-48-599_siyateagan.example.translatorapp.jpg" width="300" > <img src="images/Screenshot_2020-04-15-18-24-02-587_siyateagan.example.translatorapp.jpg" width="300" >

3. Экран закладок: отображает добавленные в дазу данных пары текстов, которые можно удалить.
<img src="images/Screenshot_2020-04-15-18-26-04-286_siyateagan.example.translatorapp.jpg" width="300" >

Цель проекта - изучить следующие технологии:
* Dagger
* Retrofit

И попрактиковать Rxjava. Также была использованна Room.




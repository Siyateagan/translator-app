# translator-app
❗Currently not working due to api changes❗

Yandex.Translate client.

## Application Description
1. Language selection screen: Gets a list of available languages for translation, puts them in the adapter, and sorts them alphabetically. SearchView allows you to find the desired language. Clicking on a language returns you to the translation screen.
<img src="images/Screenshot_2020-04-15-18-26-09-083_siyateagan.example.translatorapp.jpg" width="300" >

2. Translation screen: sends a request to the server, with a delay of 500ms to reduce the number of requests, handles errors using RxJava. Supports text-to-speech.

<img src="images/Screenshot_2020-04-15-18-23-48-599_siyateagan.example.translatorapp.jpg" width="300" > <img src="images/Screenshot_2020-04-15-18-24-02-587_siyateagan.example.translatorapp.jpg" width="300" >

3. Bookmarks screen: displays pairs of texts added to the database that can be deleted.
<img src="images/Screenshot_2020-04-15-18-26-04-286_siyateagan.example.translatorapp.jpg" width="300" >

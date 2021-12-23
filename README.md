# NasaAPIKotlin

This application I've made in order to better understand MVVM, kotlin coroutines as well as dependency injection with Hilt. 
I've also decided to do paging on my own without help of library, which lead to quite... awkward result.
I've also had a plan to implement a video player, but after several days of my fight with exoplayer, I've decided to scrap this idea.
This may result in Nasa Picture of the Day not loading properly on certian days, as it sometimes is a video instead of an image.
I'm aware that I've made a lot of questionable choices in regards to coding or even including some of the features,
but most of the time It was driven by trying to challenge myself in order to push my craft. That of course resulted in much poorer code,
than I'd ideally want. On the flipside this approach tought me a lot of new things, which was the main goal of this app.

In order to run this app, you have to generate your own api key from https://api.nasa.gov/ and put it in gradle.properties file as API_KEY=

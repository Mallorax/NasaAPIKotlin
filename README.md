# NasaAPIKotlin

This application I've made to better understand MVVM, kotlin coroutines as well as dependency injection. 
I've also decided to do paging on my own without help of library, which lead to quite... awkward result.
I've also had a plan to implement a video player, but after several days of my fight with exoplayer, I've decided to scrap this idea.
This may result in Nasa Picture of the Day not loading properly on certian days, as it sometimes is a video instead of an image.

In order to run this app, you have to gen your own api key from https://api.nasa.gov/ and put it in gradle.properties file as API_KEY=

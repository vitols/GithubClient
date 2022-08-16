# GithubClient Android App

The repo contains a client application for GitHub written in Kotlin for Android devices.

This application is a continuation of the first version written in Java (check out [GithubUsers repository](https://github.com/vitols/GithubUsers)).

The app has two operating modes - anonymous and authorized.
With the anonymous access it is allowed to have access to user profiles and search through them.
While when authorized, one could access their profile and private repositories, edit personal info.

## Getting Started

To be able to use authorization, it is required to register the app on Github.
The issued `CLIENT_ID` and `CLIENT_SECRET` should be added to `com.example.android.githubclient.base.ConstValues.kt` file in order to enable authorized requests.

#### Built With

* [GitHub's OAuth](https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps) 
* [Kotlin](https://kotlinlang.org/) - Kotlin programming lang (v.1.7.10)
* [RxJava](https://github.com/ReactiveX/RxJava) - A library for composing asynchronous and event-based programs
* [Glide](https://github.com/bumptech/glide) - Image loading framework
* [ThreeTenABP](https://github.com/JakeWharton/ThreeTenABP) - Working with time
* [CircleImageView](https://github.com/hdodenhof/CircleImageView) - A library for creating circle image views
* [Retrofit](http://square.github.io/retrofit/) - Http client

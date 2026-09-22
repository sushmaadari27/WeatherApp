# WeatherApp 🌤️

A modern Android weather application built with **Kotlin** and **Jetpack Compose** that uses the **OpenWeather API** and device location services to display current weather information.

## 📱 Features

* 📍 Get weather information using the device's current location
* 🌡️ Display current temperature in Celsius
* ☁️ Display weather conditions
* 💧 Show humidity information
* 🌬️ Display wind speed
* 🔄 Fetch real-time weather data from the OpenWeather API
* 🎨 Modern UI built with Jetpack Compose
* ⚠️ User-friendly error handling

## 🛠️ Technologies Used

* **Kotlin**
* **Jetpack Compose**
* **Android SDK**
* **Retrofit**
* **Gson Converter**
* **Google Play Services Location**
* **OpenWeather API**
* **Gradle**
* **Git & GitHub**

## 🏗️ Project Structure

```text
WeatherApp/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/weatherapp/
│           │   ├── MainActivity.kt
│           │   ├── WeatherApi.kt
│           │   ├── WeatherResponse.kt
│           │   └── ui/theme/
│           └── AndroidManifest.xml
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── .gitignore
```

## 🌐 API

This application uses the **OpenWeather API** to retrieve weather information.

The application sends the device's latitude and longitude to the weather API and displays the returned weather data in the Android UI.

## 🔑 API Key Setup

For security, the API key is **not stored directly in the source code or committed to GitHub**.

Create or edit the `local.properties` file in the project root and add:

```properties
OPEN_WEATHER_API_KEY=YOUR_API_KEY
```

The project reads this value during the Gradle build and makes it available to the application through `BuildConfig`.

> **Note:** Never commit your `local.properties` file or expose your API key publicly.

## 🚀 How to Run

### Prerequisites

* Android Studio
* Android SDK
* JDK 11 or compatible configuration
* Android device or emulator
* OpenWeather API key

### Steps

1. Clone the repository:

```bash
git clone https://github.com/sushmaadari27/WeatherApp.git
```

2. Open the project in **Android Studio**.

3. Add your OpenWeather API key to `local.properties`:

```properties
OPEN_WEATHER_API_KEY=YOUR_API_KEY
```

4. Allow the application to access device location when prompted.

5. Sync the project with Gradle.

6. Connect an Android device or start an emulator.

7. Click **Run ▶** in Android Studio.

## 📸 Screenshots

### Weather Screen

![WeatherApp Screenshot](weatherapp-screenshot.jpg)

## 🎯 Learning Outcomes

Through this project, I practiced:

* Android application development using Kotlin
* Jetpack Compose UI development
* REST API integration using Retrofit
* JSON data handling using Gson
* Device location services
* API error handling
* Gradle configuration
* Secure handling of local API configuration
* Git version control and GitHub repository management

## 👩‍💻 Developer

**Adari Kumanika Sushma**


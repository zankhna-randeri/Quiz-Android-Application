# Quiz Application - Android
## Introduction
- Quiz application allows user to play quiz. User can select category for which he/she wants to
play a quiz. After category selection, user will be redirected to actual quiz screen where they
are presented with appropriate multiple-choice questions.  
- Quiz questions are fetched from opensource API.  
- Based on answer selection, scores are maintained and stored in database at the end of the
quiz.  
- User can view his/her score history.  

## SDK Build Version  

- Compile SDK version – 28  
- Target SDK version – 28  
- Min SDK version - 15

## Build Tools  - Opensource & Support libraries
1. ButterKnife – 8.8.1
2. Retrofit – 2.5.0
3. gson-converter – 2.5.0
4. Quiz opensource API - [API Link](https://opentdb.com/)
5. Icons Designed by
   - [Link](https://www.flaticon.com/authors/popcorns-arts), [Link](https://www.freepik.com/), [Link](https://www.flaticon.com/authors/turkkub), [Link](https://www.flaticon.com/authors/pixelmeetup)
6. Support Libraries used
   - appcompat-v7
   - support-v4
   - recyclerview-v7
7. Android Architecture Components used
   - lifecycle-viewmodel & lifecycle-extension:  
     - It is a viewmodel component to handle data on device orientation changes
   - Room-runtime & room-compiler
     - It is used to store data in SQLite Database using Room persistence library to provide isolation between data layer and view
   
   
## Architecture Diagram  

![Architecture diagram](https://user-images.githubusercontent.com/42704669/57896842-93b92780-7807-11e9-99ad-2781f3012ec0.png)

## Screenshots  
#### Launch/Home Screen
![Picture1](https://user-images.githubusercontent.com/42704669/57897772-417a0580-780b-11e9-83bc-4240fa190d78.png)

#### Category Screen
![Picture2](https://user-images.githubusercontent.com/42704669/57897773-417a0580-780b-11e9-8145-bcc3bf3146d4.png)

#### Quiz Screen
![Picture3](https://user-images.githubusercontent.com/42704669/57897775-42129c00-780b-11e9-908b-c8c4c106dbba.png)
![Picture4](https://user-images.githubusercontent.com/42704669/57897776-42129c00-780b-11e9-9bb0-5d4aaf625c98.png)

#### Score Screen
![Picture5](https://user-images.githubusercontent.com/42704669/57897778-42129c00-780b-11e9-9a3f-19318be2ca0e.png)

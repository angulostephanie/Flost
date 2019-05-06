# Flost
An Android app that centralizes all lost and found things.

Made by Stephanie Angulo, Di Hu, Emily Tran.

## Instructions

### Run Server
1. CONNECT WITH EC2 INSTANCE: In your terminal, run the following command in the same directory as the provided pem file 
```
ssh -i flostkeypair.pem ec2-user@ec2-52-52-100-133.us-west-1.compute.amazonaws.com
```
2. START SERVER: Run the following command
```

### Install APK
1. Install Android Studio
2. Open AVD Manager and turn on an emulator (with Google Play Services)
3. In the emulator, go to Google Play store and update Google Play Services 
4. Drag the given APK file into the emulator screen. Your emulator will install the Flost app

### Add SHA 1 key 
You won't be able to use Firebase authentication until you add your project's SHA1 key to the firebase project. We will add you as a project Firebase member of Flost. 
1. Go to your Android Project
2. On the right hand side, click on Gradle
3. Navigate to :app -> Tasks -> android -> signingReport. Double click on it
4. Copy the generated SHA1 key under the variant name "debug"
5. Go to the Firebase project (you should have gotten an email about gaining access to this)
6. Go to Settings and scroll all the way down. Click on "Add Fingerprint"
7. Paste the SHA1 from step 4 and save

### Using the App
After all of the previous steps, you should be able to run the Android app from Android Studio!
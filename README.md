# cse535-mc-assignment2 code


## Assignment Details

Develop a music player app
The app will have control to
(1) to start a foreground service to start playing music (store some music files in appropriate
resource directory)
(2) stop playing the music
(3) start another activity that checks if the Internet connection is available, if so it talks a music player
server and downloads the music files and save in appropriate files (we have hosted a sample audio
file at http://faculty.iiitd.ac.in/~mukulika/s1.mp3 , download and save this audio file) that are private to
the app
(4) It will have broadcast receivers for the following actions BATTERY_LOW,
POWER_DISCONNECTED, BATTERY_OKAY
Design the UI of the app using a fragment.
In the demo, we will evaluate your assignment on the following aspects:
(1) start the service to play a song: 2 marks
(2) stop the service: 2 marks
(3) check for network and download song: 3 marks (1 mark for checking network availability, 1 mark
for connecting to the webserver, 1 mark for downloading)
(4) saving the downloaded song into appropriate file: 1 mark
(5) correctly implement all broadcast receivers: 2 marks
(6) UI design using fragments: 2 marks
(7) viva (all related concepts): 2 marks
(8) app functions properly (doesn't crash): 1 mark
Bonus mark: If your app is able to play the downloaded file (1 mark)
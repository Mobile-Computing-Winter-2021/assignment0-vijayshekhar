

##Assignment 5: Indoor Localization using WIFi
● Implement an indoor localization app that will provide room level accuracy.
● Get the WiFi scan results to know the list of access points nearby and their RSSI values
(Hint: https://developer.android.com/reference/android/net/wifi/WifiManager
https://developer.android.com/reference/android/net/wifi/WifiInfo#getRssi()
https://developer.android.com/reference/android/net/wifi/ScanResult
).
● Showcase the list of APs that you can hear with their RSSI values (think of a nice UI) to
show this. For example using bars as can be seen from this Fig. You can think of other
alternatives as well.
♦   
● Wardive inside your home and get RSSI measurements of the APs from different rooms
of your home using WiFi scan results. Store this information. Design this wardriving
using appropriate functionality in the UI. For example, you have one UI control like
button that says now start your wardriving.
● Given a test RSSI measurements of these APs, return your location. Think of a nice UI
to develop this. For example, again having one UI control says a button that lets 1
test. During testing it will get RSSI of the APs nearby by getting the WiFi scan results.
Then, it will match it to stored information with a single point that is most similar to the
test data.
● Optional1: implementing the matching with KNN
● Optional2: remove wardriving by utilizing IMU sensors
Rubric:
1. Get the list of WiFi APs: 2
2. Showcasing this appropriate UI: 3 marks
3. Collect training data: UI+ getting the scan data with RSSI+ saving: 5 marks
4. Testing of localization app: UI+ returning the correct location 5
5. Bonus marks optional 1: 3
6. Bonus marks optional 2: 3
Note: You are free to design the UI as per your choice. You are encouraged to design a clean
and pleasing UI.
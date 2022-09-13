# MyUtilsLibrary
Shortcut functions to use in your project


Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  
Add the dependency

	dependencies {
	        implementation 'com.github.rohitm0907:MyUtilsLibrary:1.0.0'
	}
	
	
	
For Activity:
	
        /** to show toast **/
        showToast("hello everyone")
	
	
        /** for checking fast click **/
        if (isNotFastClicks()) {
            // Work here if not fast clicks
        } else {
            showToast("Fast Clicking")
        }


        /** check network connected or not **/
        if (isNetworkConnected()) {
            // work here if internet connected
        } else {
            showToast("Please check your internet connection")
        }


        /** show progess and stop progresss **/
        showProgess()
        Handler().postDelayed({
            stopProgress()
        }, 3000)


        /** Save Values in sharedPreference storage **/
        saveString("myString", "rohit")
        saveInt("myInt", 1)
        saveBoolean("myBoolean", true)
        saveModel("myModel", Data("rohit", 1))


        /** get Values from sharedPreference storage **/
        var saveStringValue = getString("myString")
        var saveIntValue = getInt("myInt")
        var saveBooleanValue = getBoolean("myBoolean")
        var saveModelValue = getModel("myModel", Data::class.java)


        // clear all sharedPreference data 
        clearPreferenceData()
	
	
For Fragments:

        Use requireActivity() with function name to call the functions.
	   
	    For e.g:  
	    requireActivity.showToast("hello everyone")
	    requireActivity.showProgress()
	

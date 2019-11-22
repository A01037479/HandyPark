Team Name: Parking Pals
App Name:  HandyPark
Members:   Christy Yau, Eric Zhang, Justin Quan
Date:	   November 22, 2019
==============================================================================================

Open data: https://opendata.vancouver.ca/explore/dataset/disability-parking/information/

Purpose:  To help individuals with mobility issues find designated disability parking zones
	  and disability meter spaces around the City of Vancouver.

*** NOTE: App needs to run on API 28 to properly display the addresses and use waypoints in Google Maps. ***
How to use:  Search for a specific destination within the City of Vancouver and all designated
	     disability parking zones and meters around the destination will be sorted and 
	     displayed.  User can further filter by radius values of 1 Km, 3 Km, 5 Km.
	     Upon clicking one of the available parking spots, the User's geolocation, 
	     coordinates of the parking spot and coordinates of the destination are passed
	     to Google Maps to plot a navigation route that guides the User from their current
	     location to the parking spot waypoint then from the waypoint to the desired
	     destination.

Future
features:   Initially we created our own maps activity that accepts the extra values of 
	    coordinates for the 3 points.  We implemented a way to plot pins to represent
	    the three locations and draw a polyline the display the route between them.
	    However, after discussing with Armaan, we came to an agreement that since
	    we would already be using Google Maps for the navigation feature, the user 
	    experience can be streamlined by directly launching Google Maps to draw the route 
            and start the navigation.  Especially for individuals with disabilities, their 
  	    fine motor skills may be limited so it was important to us to create an app
	    that was easy to use for our targeted audience.

*Toasts have been commented out as they were used primarily for developing purposes.



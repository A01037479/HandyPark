package ca.bcit.handypark;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Parking implements Serializable
{
    private String description;
    private String notes;
    private int spaces;
    private double[] coordinates;
    //e.g. North Side 1600 Kitchener St
    private String location;
    //e.g. Marpole
    private String geoLocalArea;

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public int getSpaces() { return spaces; }
    public void setSpaces(int spaces) { this.spaces = spaces; }
    public double[] getCoordinates() { return coordinates; }
    public void setCoordinates(double[] coordinates) { this.coordinates = coordinates; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getGeoLocalArea() { return geoLocalArea; }
    public void setGeoLocalArea(String geoLocalArea){ this.geoLocalArea = geoLocalArea; }

}



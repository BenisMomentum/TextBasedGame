package main.Location;

import main.Entities.Monster;
import main.Items.Item;
import main.TextConstants;

import java.io.File;
import java.util.*;

public class Location {
    private final int locationID;
    private final String description;
    private final Map<String,Integer> exits;
    private final List<Item> items;
    private final Monster m;


    /*
    Essentially this is how Locations are meant to be managed:

    There will be a description, a set of exits, and perhaps a set of items in the room.

    Items in the room will be revealed by a SCAN

    Exits work as the following: Usually a cardinal direction (N-W-S-E). The numbers are what the exits lead towards

    As well a monster in the room too.

    Writing format will likely be the following:

    LOCATIONID | DESCRIPTION | EXITS | ITEMS | MONSTER

     */

    public Location(int locationID, String description, Map<String, Integer> exits, List<Item> items, Monster monster) {
        this.locationID = locationID;
        this.description = description;
        this.exits = (exits == null) ? new HashMap<>() : new HashMap<>(exits);
        this.items = (items == null) ? new ArrayList<>() : new ArrayList<>(items);
        this.m = monster;

        //NOTE, IF THERE IS NO MONSTER IN THE ROOM, PUT NULL
    }

    public int getLocationID() {
        return locationID;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Integer> getExits() {
        return exits;
    }

    public String toWriteable(){
        /*
        NOTE: You will have to call String.trim() just to protect against trailing spaces.
         */

        StringBuilder sB = new StringBuilder();

        final String seperator = TextConstants.LOCATION_BIG_REGEX;

        sB.append(this.locationID + seperator + this.description + seperator);

        String[] cardinalDirection = exits.keySet().toArray(new String[0]);
        Integer[] exitLead = exits.values().toArray(new Integer[0]);

        //The following If-Else statements reduce program efficiency of the toWriteable function
        //It is used so that there are less trails to parse out.

        if(cardinalDirection.length == 1){

            sB.append(cardinalDirection[0] + TextConstants.EXIT_REGEX + exitLead[0]);

        }else{
            //Format is --> [DIRECTION]-[LOCATION ID]
            for(int i = 0; i < cardinalDirection.length - 1; i++){
                sB.append(cardinalDirection[i] + TextConstants.EXIT_REGEX + exitLead[i]);
                sB.append(" ");
            }
            sB.append(cardinalDirection[cardinalDirection.length - 1] + TextConstants.EXIT_REGEX + exitLead[exitLead.length - 1]);
        }

        sB.append(seperator);

        if(items.size() == 1){
            sB.append(items.get(0));
        } else{
            for(int i = 0; i < items.size() - 1; i++){
                sB.append(items.get(i));
                sB.append(TextConstants.ITEM_REGEX);
            }
            sB.append(items.get(items.size() - 1));
        }

        sB.append(seperator + m);

        return sB.toString();
    }

    public void read(String rawInput){ //supposed to read from file
        //EMPTY FOR NOW
        //FUNCTION IS BEING MOVED TO SINGLETON Locations.java

    }

}

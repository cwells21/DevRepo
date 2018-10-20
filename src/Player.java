import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * @author Kevin
 */
public class Player {
    private LinkedList<String> currentInventory;
    private String name;
    private int currentRoom;
    private Direction currentDirection;
    private PrintWriter replyWriter = null;
    private DataOutputStream outputWriter = null;
    private String lastPlayer = "";

    public Player(String name) {
        this.currentRoom = 1;
        this.currentDirection = Direction.NORTH;
        this.name = name;
        this.currentInventory = new LinkedList<>();
    }

    private HashSet<Player> ignoredPlayers = new HashSet<Player>();
    // missed Messages - not yet in uses
    private HashSet<Message> missedMessages = new HashSet<Message>();

    //Collection of words to be filtered from game chat
    private HashSet<String> filteredWords = new HashSet<String>();

    public boolean addFilteredWord(String wordToAdd) {
        return filteredWords.add(wordToAdd);
    }

    public boolean removeFilteredWord(String wordToRemove) {
        return filteredWords.remove(wordToRemove);
    }

    public boolean isFiltering(String word) {
        return filteredWords.contains(word);
    }



    /**
     * Adds a player's reference to set ignoredPlayers.
     * @param playerToIgnore
     * @return - whether player reference was successfully added to set ignorePlayer.
     */
    public boolean ignorePlayer(Player playerToIgnore) {
        // if(!ignoredPlayers.contains(playerToIgnore)){
        //     System.out.println(playerToIgnore.name + " has been ignored.");
        return ignoredPlayers.add(playerToIgnore);
        // } else {
        //     System.out.println(playerToIgnore.name + " is already being ignored.");
        //     return false;
        // }
    }

    //Feature 408. Unignore Player.
    /**
     *
     * Removes a given player form the set ignoredPlayers
     * @param playerToUnIgnore - player to remove from set
     * @return - whether the player reference was successfully removed
     *
     */
    public boolean unIgnorePlayer(Player playerToUnIgnore) {
        if(ignoredPlayers.contains(playerToUnIgnore)){
            return ignoredPlayers.remove(playerToUnIgnore);
        }else {
            return false;
        }
    }

    /**
     * Checks a given other player to see if they're on the THIS player's ignore list.
     * @param otherPlayer - other player this player may or may not be ignoring.
     * @return - whether the other player is being ignored by this player.
     */
    public boolean isIgnoring(Player otherPlayer) {
        return ignoredPlayers.contains(otherPlayer);
    }

    /**
     * Access the list of players this player is ignoring.
     * @return - Returns a String of all player names this player is ignoring
     */
    public String getIgnoredPlayersList() {
        StringBuilder ignoredPlayersList = new StringBuilder();
        ignoredPlayersList.append("\nIgnored Players: ");
        if(ignoredPlayers.isEmpty()) { ignoredPlayersList.append(" Your ignore list is empty.\n"); }
        else {
            int count = 1;
            for(Player ignored : ignoredPlayers) {
                ignoredPlayersList.append(ignored.name);
                if(count == ignoredPlayers.size()) {
                    ignoredPlayersList.append(".\n");
                } else {
                    count++;
                    ignoredPlayersList.append(", ");
                }
            }
        }
        return ignoredPlayersList.toString();
    }

    /**
     *
     * @param sentMessage - the Message being sent to this player.
     * @return - whether or not the sent message was successfully added to the set of received messages.
     */

    public boolean receiveMessage(Message sentMessage) {
        boolean received = false;

        //put ignore detection here

        /*if(recievedMessages.add(sentMessage)) {

            sentMessage.SetReceived();
            received = true;
        }*/

        // if recipient is offline, detect here

        return received;
    }

    /**
     *
     * @param textOfMessage - The actual input message from the user.
     * @param intendedRecipient - A reference to the user the message is being sent to.
     * @return - Whether the recipient successfully received the sent message.
     */
    public boolean sendMessage(String textOfMessage, Player intendedRecipient) {

        Message newMessage = new Message(textOfMessage, this, intendedRecipient);
        return intendedRecipient.receiveMessage(newMessage);

    }

    /**Reeds changes end here**/

    public void turnLeft() {
        switch(this.currentDirection.toString()) {
            case "North":
                this.currentDirection = Direction.WEST;
                break;
            case "South":
                this.currentDirection = Direction.EAST;
                break;
            case "East":
                this.currentDirection = Direction.NORTH;
                break;
            case "West":
                this.currentDirection = Direction.SOUTH;
                break;
        }
    }

    public void turnRight() {
        switch(this.currentDirection.toString()) {
            case "North":
                this.currentDirection = Direction.EAST;
                break;
            case "South":
                this.currentDirection = Direction.WEST;
                break;
            case "East":
                this.currentDirection = Direction.SOUTH;
                break;
            case "West":
                this.currentDirection = Direction.NORTH;
                break;
        }
    }

    public String getLastPlayer() {
        return lastPlayer;
    }

    public void setLastPlayer(String lastPlayer) {
        this.lastPlayer = lastPlayer;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<String> getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(LinkedList<String> currentInventory) {
        this.currentInventory = currentInventory;
    }

    public void addObjectToInventory(String object) {
        this.currentInventory.add(object);
    }

    public void setReplyWriter(PrintWriter writer) {
        this.replyWriter = writer;
    }

    public PrintWriter getReplyWriter() {
        return this.replyWriter;
    }

    public void setOutputWriter(DataOutputStream writer) {
        this.outputWriter = writer;
    }

    public DataOutputStream getOutputWriter() {
        return this.outputWriter;
    }

    public int getCurrentRoom() {
        return this.currentRoom;
    }

    public void setCurrentRoom(int room) {
        this.currentRoom = room;
    }

    public String getCurrentDirection() {
        return this.currentDirection.name();
    }

    public Direction getDirection() {
        return this.currentDirection;
    }

    public String viewInventory() {
        String result = "";
        if(this.currentInventory.isEmpty() == true) {
            return "nothing.";
        }
        else {
            for(String obj : this.currentInventory) {
                result += " " + obj;
            }
            result += ".";
        }
        return result;
    }

    @Override
    public String toString() {
        return "Player " + this.name + ": " + currentDirection.toString();
    }
}
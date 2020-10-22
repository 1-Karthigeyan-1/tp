package seedu.duke.command;

import seedu.duke.ActivityList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static seedu.duke.Ui.displayEmptyActivityCounterMessage;

/**
 * Initialises Command to find all description tags.
 */
public class FindEitherCommand extends Command {
    protected LocalDate date;
    protected String userInput;

    /**
     * Find matching results based on input tags.
     * @param userInput keywords to be matched
     */
    public FindEitherCommand(String userInput) {
        this.date = LocalDateTime.now().toLocalDate();
        this.userInput = userInput;
    }

    @Override
    public void execute() {
        ActivityList activities = dayMap.getActivityList(date.atStartOfDay());
        int activityCounter = activities.getNumberOfActivities();
        if (activityCounter == 0) {
            displayEmptyActivityCounterMessage();
        } else {
            System.out.println("Here are the matching results based on keywords typed (either):");
            boolean hasOneWord;
            for (int i = 0; i < activityCounter; i++) {
                String currentLine = activities.getActivity(i).toString();
                hasOneWord = checkEitherWords(currentLine);
                if (hasOneWord) {
                    System.out.println(currentLine);
                }
            }
        }
    }

    /**
     * Checks if one of the keywords inputted by user is present in entry.
     * @param currentLine current entry to be checked
     * @return  hasOneWord true if just one word is present, false otherwise
     */
    private boolean checkEitherWords(String currentLine) {
        ArrayList<String> wordsToCheck = new ArrayList<>();
        wordsToCheck = getAllTags(userInput);
        for (String word : wordsToCheck) {
            if (currentLine.contains(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses all keywords inputted by user into an arraylist.
     * @param userInput String user typed into CLI
     */
    private ArrayList<String> getAllTags(String userInput) {
        ArrayList<String> tags = new ArrayList<>();
        while (userInput.contains("e/")) {
            if (!userInput.contains(" ")) {
                userInput = userInput.substring(2);
                tags.add(userInput);
                break;
            }
            int spaceIndex = userInput.indexOf(" ");
            String firstWord = userInput.substring(0, spaceIndex);
            userInput = userInput.substring(spaceIndex).trim();
            firstWord = firstWord.substring(2);
            tags.add(firstWord);
        }
        return tags;
    }
}
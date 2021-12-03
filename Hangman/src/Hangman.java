import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

    private static final ArrayList<String>wordList = new ArrayList<>();
    private int maxLives;
    private int missedLettersCounter;
    private String wordToFind;
    private char[] hiddenWord;
    private char[] charWord;
    private ArrayList<Character> letters = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);


    private void setWordList(){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("C:\\Users\\Ingrid\\Desktop\\Hangman\\Hangman\\src\\words.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()){
            wordList.add(scanner.next());
        }
        scanner.close();
    }

    public ArrayList<String> getWordList(){
        return wordList;
    }

    private static int randomIndexGenerator(){
        Random random = new Random();
        return random.nextInt(wordList.size());
    }

    private void selectRandomWord(){
        wordToFind = wordList.get(randomIndexGenerator()).toLowerCase();
        charWord = wordToFind.toCharArray();
        hiddenWord = new char[charWord.length];
        for(int i = 0; i < charWord.length; i++){
            hiddenWord[i] = '*';
        }
        System.out.println(hiddenWord);
    }

    private void newGame(){
        selectLevel();
        setWordList();
        missedLettersCounter = 0;
        letters.clear();
        selectRandomWord();

    }

    private void findWord(char letter){

        String letterToString = Character.toString(letter);

        if(!(letters.contains(letter))){
            for(int i = 0; i < charWord.length; i++){
                if(charWord[i] == letter){
                    hiddenWord[i] = letter;
                }
            }
            if((!wordToFind.contains(letterToString))){
                missedLettersCounter++;
            }

            letters.add(letter);
        }
    }

    private void printStatus(){
        System.out.println(hiddenWord);
        System.out.println("Entered letters: " + letters);
        System.out.println("Number of lives: " + (maxLives - missedLettersCounter));
    }

    private boolean isFound(){
        int count = 0;
        for(int i = 0; i < hiddenWord.length; i++){
            if(hiddenWord[i] != '*'){
                count++;
                if(count == hiddenWord.length){
                    return true;
                }

            }
        }
        return false;
    }

    private void selectLevel(){
        System.out.println("Please select a level: ");
        System.out.println("easy");
        System.out.println("medium");
        System.out.println("hard");
        String level = scanner.nextLine();
        switch (level.toLowerCase()){
            case "easy":
                maxLives = 5;
                break;
            case "medium":
                maxLives = 4;
                break;
            case "hard":
                maxLives = 3;
                break;
            default:
                System.out.println("Please select a valid level");
                break;
        }

    }

    public void play(){

        newGame();

        while (missedLettersCounter < maxLives){
            System.out.println("\nEnter a letter:");
            String str = scanner.nextLine();

            while (str.length() > 1){
                System.out.println("Please enter a single letter:");
                str = scanner.nextLine();
            }

            char letter = str.toLowerCase().charAt(0);

            findWord(letter);

            printStatus();

            if(isFound()){
                System.out.println("\nYOU WON!");
                break;
            }

            if(missedLettersCounter == maxLives){
                System.out.println("\nYOU LOST!");
                System.out.println("\nThe word was: " + wordToFind);
            }

        }
    }
}

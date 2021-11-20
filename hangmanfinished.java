import java.util.Arrays;
import java.util.Scanner;

public class hanged {
    static String[] myWord = {"hola", "adios"}; //va a estar siempre accesible.
    static String usedChars = ""; //va a estar siempre accesible y lleva la cuenta de las letras usadas.
    static int tries = 1; //vamos a tener seis intentos
    static String secretWord;
    static String asteriskWord;
    static int wonMatches=0;
    static int playedMatches=0;

    /**
     * La función setWord sirve para elegir una palabra al azar y la define como static.
     */

    public static void setSecretWord() {
        int random = (int) (Math.random() * (myWord.length - 1));
        secretWord = myWord[random];

    }

    /**
     * la función encryptedWord oculta la palabra elegida con asteriscos
     *
     * @param word String que toma la palabra de secretWord.
     * @return Devolvemos un String con asteriscos
     */


    public static void encryptedWord(String word) {

        String hiddenWord = "";
        for (int i = 0; i < word.length(); i++) {
            hiddenWord += "*";
        }

        asteriskWord = hiddenWord;

        //System.out.println(palabraOculta);
        //Comprobar que aparecían los asteriscos


    }

    /**
     * @param character toma una letra y verifica si está repetida o no.
     * @return devuelve un boolean dependiendo de si la encuentra o no.
     */
    public static boolean verifyLetter(String character) {
        character = character.toLowerCase();

        if (!usedChars.contains(character)) {
            usedChars += character; //letrasUsadas = letrasUsadas + letra;
            return false;
        }

        return true;
    }

    public static void hangmanImage(){
        char [][]images = {
                {'_','_','_',' '  },
                {'|',' ','|',' '  },
                {'|',' ','O',' '  },
                {'|','_','|','_'  },
                {'|',' ','|',' '  },
                {'|','/',' ','\\' },
                {'_','_','_',' '  }
        };

        for (int i = 0; i <= tries; i++) {
            for (int j = 0; j < images[i].length; j++) {
                System.out.print(images[i][j]);
            }

            System.out.println();
        }

        System.out.println("¡Respuesta incorrecta! ¡Prueba otra vez!");

        if (tries == 6) {
                System.out.println("GAME OVER! La palabra era " + secretWord);
                playedMatches++;
        }
    }

    /**
     * la función theGame es donde se desarrolla el juego
     *
     * @param asteriskWord recoge la palabra cifrada
     */

    public static void theGame(String asteriskWord) {
        char[] secretWordArray = secretWord.toCharArray(); //era para comparar ambos arrays y que así parase el bucle-
        char[] guessWord = asteriskWord.toCharArray();
        Scanner input=new Scanner(System.in);


        System.out.println("¡Estupendo! ¡Empecemos!");


        while (tries <= 5){
            System.out.println(guessWord);


            if(Arrays.equals(guessWord, secretWordArray)){
                System.out.println("¡Felicidades! ¡Has ganado el juego! La palabra era: "+secretWord);
                playedMatches++;
                wonMatches++;
                break;
            }

            //se mete de nuevo a pedir escribir resolver porque al comparar los arrays no tiene instrucción sobre
            //qué hacer cuando no son iguales.
            System.out.println("¿Te ves capaz de adivinar la palabra? Escribe resolver");
            String solve=input.nextLine().toLowerCase();

            if (solve.contains("resolver")) {
                System.out.println("Introduce la palabra: ");
                String tryWord = input.nextLine();
                if (Arrays.equals(tryWord.toCharArray(), secretWordArray)) {
                    System.out.println("¡Felicidades! ¡Has adivinado la palabra!");
                    playedMatches++;
                    wonMatches++;
                    break;
                }else {
                    System.out.println("¡Oh, no! ¡Has fallado!");
                    tries += 2;
                    hangmanImage();
                    continue;
                }
            }

            System.out.println("Dime una letra:");
            String currentCharacter = input.nextLine();

            if (currentCharacter.charAt(0) < 'a' || currentCharacter.charAt(0) > 'z') {
                System.out.println("No es una letra válida, debe ser entre 'a' y 'z'");
                continue;
            }

            if (currentCharacter.length() > 1) {
                System.out.println("No puedes poner más de un carácter");
                continue;
            }
            if (!verifyLetter(currentCharacter)) {
                if (secretWord.contains(currentCharacter)) {

                    System.out.println("La palabra contiene la letra " + currentCharacter);

                    for (int i = 0; i < secretWord.length(); i++) {
                        if (secretWord.charAt(i) == currentCharacter.charAt(0)) {
                            guessWord[i] = currentCharacter.charAt(0);
                        }
                    }
                } else {
                    tries++;
                    hangmanImage();
                }

            } else if (verifyLetter(currentCharacter)) {
                System.out.println("Ya habías utilizado la letra " + currentCharacter + " antes!");

            } else {
                System.out.println("Error de entrada");
            }


        }
    }

    /**
     * la función bestThree equivale al modo de juego Mejor de tres.
     * @param asteriskWord recoge la palabra cifrada.
     */

    public static void bestThree(String asteriskWord){
        do{
            hanged.theGame(asteriskWord);

        }while(playedMatches>2 && wonMatches<2);
        if(playedMatches>2 && wonMatches<2) {
            System.out.println("Has perdido");
            System.out.println();
            System.out.println("Partidas jugadas: " + playedMatches + " Partidas ganadas: " + wonMatches);
        }
        if(playedMatches>=2 && wonMatches>=2){
            System.out.println("¡Has ganado en modo el mejor de tres!");
            System.out.println("Partidas jugadas: "+playedMatches+ " Partidas ganadas: "+wonMatches);
        }
    }

    /**
     *La función menu es el menú del juego
     */

    public static void menu() {
        boolean stop=false;
        do {
            System.out.println("¡Bienvenido!");
            System.out.println("¿Deseas jugar al ahorcado? S/N");
            Scanner input = new Scanner(System.in);

            char choice = input.nextLine().toUpperCase().charAt(0);

            switch (choice) {
                case 'S':
                    // Reiniciamos el estado del juego:
                    tries = 0;

                    //implementar aqui el método de juego mejor de tres
                    System.out.println("¿Quieres iniciar una partida normal o el Mejor de tres? P/T ");
                    char choiceMatch = input.nextLine().toUpperCase().charAt(0);

                    if(choiceMatch=='T'){
                        hanged.setSecretWord();
                        hanged.encryptedWord(secretWord);
                        hanged.bestThree(asteriskWord);
                    }else {
                        hanged.setSecretWord();
                        hanged.encryptedWord(secretWord);
                        hanged.theGame(asteriskWord);
                    }
                    break;
                case 'N':
                    System.out.println("¡Hasta Luego!");
                    stop=true;
                    break;
            }
        }while(!stop);
    }

    public static void main (String[]args){

        hanged.menu();

    }
}

package menu;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * @author tcpassos99@gmail.com
 * @since 14/06/2020
 */
public class Menu {
    
    private String title;
    private final ArrayList<Method> options;
    private boolean running;

    public Menu() {
        options = new ArrayList<>();
        for (final Method method : this.getClass().getDeclaredMethods())
            if (method.isAnnotationPresent(MenuOption.class))
                options.add(method);
    }

    public Menu(String title) {
        this();
        this.title = title;
    }
    
    /**
     * Displays menu options
     */
    private void showOptions() {
        System.out.printf("\n------------------------------\n");
        if(this.title != null) {
            System.out.println(this.title);
            System.out.printf("------------------------------\n");
        }
        options.forEach(option -> {
            MenuOption annotation = option.getAnnotation(MenuOption.class);
            System.out.printf("[%s] %s\n", annotation.input(), annotation.description());
        });
    }
    
    /**
     * Waits for valid user input
     * @return Method Method to be executed
     */
    private Method selectOption() {
        // Store the acceptable value inputs for the menu
        ArrayList<String> validInputs = new ArrayList<>();
        options.forEach(option -> {
           validInputs.add(option.getAnnotation(MenuOption.class).input());
        });
        // Accept the user input
        Scanner scanner = new Scanner(System.in);
        String input = null;
        while (!validInputs.contains(input)) {
            System.out.print(">> ");
            if (scanner.hasNextLine()) {
                input = scanner.nextLine();
            } else {
                scanner.next();
            }
        }
        return options.get(validInputs.indexOf(input));
    }
    
    /**
     * Exits main menu loop
     */
    protected void exit() {
        this.running = false;
    }
    
    /**
     * Starts the menu
     */
    public void execute() {
        this.running = true;
        while(running) {
            this.showOptions();
            try {
                this.selectOption().invoke(this);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

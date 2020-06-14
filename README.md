# Java CLI Menu
This is a simple way to create and execute command line menus in Java.
Here is an example of implementation:
'''
public class MenuDemo extends Menu {

    public MenuDemo() {
        super("Demostration");
    }

    @MenuOption(input = "1", description = "Display a hello message")
    public void sayHello() {
        System.out.println("Hello there!");
        System.out.println("...");
        System.out.println("General Kenobi!");
    }
    
    @MenuOption(input = "X", description = "Exits application")
    public void exitApplication() {
        super.exit();
    }
    
}
'''

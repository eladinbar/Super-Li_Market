import Presentation_Layer_Trucking.Menu_Printer;

public class Main {

        public static void main(String[] args) {
            Menu_Printer menu_printer = Menu_Printer.getInstance();
            menu_printer.putInitialTestState();
            menu_printer.startMenu();
        }
    }

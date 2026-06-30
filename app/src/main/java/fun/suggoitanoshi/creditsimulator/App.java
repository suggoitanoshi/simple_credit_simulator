package fun.suggoitanoshi.creditsimulator;

import java.util.Scanner;

import fun.suggoitanoshi.creditsimulator.controller.SimulatorController;
import fun.suggoitanoshi.creditsimulator.model.Menu;
import fun.suggoitanoshi.creditsimulator.view.CLI;

public class App {
  public static void main(String[] args){
    CLI cli = new CLI();
    Menu menu = new Menu();
    SimulatorController controller = new SimulatorController(cli, menu);
    Scanner scanner = new Scanner(System.in);
    cli.RenderMenu();
    while(menu.getState() != Menu.MenuState.EXIT && scanner.hasNextLine()){
      controller.HandleCommand(scanner.nextLine());
    }
    scanner.close();
  }
}

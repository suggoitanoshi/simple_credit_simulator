package fun.suggoitanoshi.creditsimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import fun.suggoitanoshi.creditsimulator.controller.SimulatorController;
import fun.suggoitanoshi.creditsimulator.model.Menu;
import fun.suggoitanoshi.creditsimulator.view.CLI;

public class App {
  public static void main(String[] args){
    CLI cli = new CLI();
    Menu menu = new Menu();
    SimulatorController controller = new SimulatorController(cli, menu);
    Scanner scanner = null;
    System.out.println(args.length);
    System.out.println(args[0]);
    if(args.length == 1){
      // input from file
      try{
        File inputFile = new File(args[0]);
        scanner = new Scanner(inputFile);
      } catch(FileNotFoundException e){
        System.out.println(e);
        scanner = new Scanner(System.in);
      }
    }
    if(scanner == null){
      scanner = new Scanner(System.in);
    }
    cli.RenderMenu();
    while(menu.getState() != Menu.MenuState.EXIT && scanner.hasNextLine()){
      controller.HandleCommand(scanner.nextLine());
    }
    scanner.close();
  }
}

package fun.suggoitanoshi.creditsimulator.controller;

import fun.suggoitanoshi.creditsimulator.model.Loan;
import fun.suggoitanoshi.creditsimulator.model.Menu;
import fun.suggoitanoshi.creditsimulator.view.CLI;

public class SimulatorController{
  private Loan loanModel;
  private Loan.Builder loanBuilder;
  private Menu menuModel;
  private CLI view;

  public SimulatorController(CLI view, Menu menuModel){
    loanModel = null;
    loanBuilder = new Loan.Builder();
    this.menuModel = menuModel;
    this.view = view;
  }

  public void HandleCommand(String command){
    if(menuModel.getState() == Menu.MenuState.MAIN_MENU){
      if(command.equals("menu")){
        view.RenderMenu();
      } else if(command.equals("mulai")){
        menuModel.transitionTo(Menu.MenuState.CREDIT_SIMULATE_TYPE);
        view.RenderPromptLeader();
        view.RenderPromptType();
      } else if(command.equals("hitung")){
        if(loanModel != null)
          view.RenderCalculation(loanModel);
        view.RenderMenu();
      } else if(command.equals("load")){
        // TODO: JSON Web Service
        view.RenderMenu();
      } else if(command.equals("exit")){
        view.RenderExit();
        menuModel.transitionTo(Menu.MenuState.EXIT);
      } else {
        view.RenderMenu();
      }
    } else {
      if(command.equals("batal")){
        menuModel.transitionTo(Menu.MenuState.MAIN_MENU);
        view.RenderMenu();
      } else {
        switch(menuModel.getState()){
          case CREDIT_SIMULATE_TYPE:
            try{
              loanBuilder.setVehicleType(command);
              menuModel.transitionTo(Menu.MenuState.CREDIT_SIMULATE_AGE);
              view.RenderPromptAge();
            } catch(IllegalArgumentException e){
              view.RenderUnknownType();
              view.RenderPromptType();
            }
            break;
          case CREDIT_SIMULATE_AGE:
            try {
              loanBuilder.setVehicleAge(command);
              menuModel.transitionTo(Menu.MenuState.CREDIT_SIMULATE_YEAR);
              view.RenderPromptYear();
            } catch(IllegalArgumentException e){
              view.RenderUnknownAge();
              view.RenderPromptAge();
            }
            break;
          case CREDIT_SIMULATE_YEAR:
            try{
              int year = Integer.parseInt(command);
              loanBuilder.setVehicleYear(year);
              menuModel.transitionTo(Menu.MenuState.CREDIT_SIMULATE_LOAN);
              view.RenderPromptLoan();
            } catch(IllegalArgumentException e){
              view.RenderInvalidYear();
              view.RenderPromptYear();
            }
            break;
          case CREDIT_SIMULATE_LOAN:
            try{
              int loan = Integer.parseInt(command);
              loanBuilder.setTotalLoan(loan);
              menuModel.transitionTo(Menu.MenuState.CREDIT_SIMULATE_TENOR);
              view.RenderPromptTenor();
            } catch(IllegalArgumentException e){
              view.RenderInvalidLoan();
              view.RenderPromptLoan();
            }
            break;
          case CREDIT_SIMULATE_TENOR:
            try{
              int tenor = Integer.parseInt(command);
              loanBuilder.setTenor(tenor);
              menuModel.transitionTo(Menu.MenuState.CREDIT_SIMULATE_DP);
              view.RenderPromptDownPayment();
            } catch(IllegalArgumentException e){
              view.RenderInvalidTenor();
              view.RenderPromptTenor();
            }
            break;
          case CREDIT_SIMULATE_DP:
            try {
              int dp = Integer.parseInt(command);
              loanBuilder.setDownPayment(dp);
              menuModel.transitionTo(Menu.MenuState.MAIN_MENU);
              loanModel = loanBuilder.build();
              if(loanModel == null){
                view.RenderInvalidDownPayment();
                view.RenderPromptDownPayment();
              } else {
                menuModel.transitionTo(Menu.MenuState.MAIN_MENU);
                view.RenderCalculation(loanModel);
                view.RenderMenu();
              }
            } catch(IllegalArgumentException e){
              view.RenderInvalidDownPayment();
              view.RenderPromptDownPayment();
            }
          default:
            view.RenderPromptLeader();
        }
      }
    }
  }
}

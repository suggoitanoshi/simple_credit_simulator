package fun.suggoitanoshi.creditsimulator.model;

public class Menu {
  public enum MenuState {
    MAIN_MENU,
    CREDIT_SIMULATE_TYPE,
    CREDIT_SIMULATE_AGE,
    CREDIT_SIMULATE_YEAR,
    CREDIT_SIMULATE_LOAN,
    CREDIT_SIMULATE_TENOR,
    CREDIT_SIMULATE_DP,
    EXIT
  }
  private MenuState state;

  public Menu(){
    this.state = MenuState.MAIN_MENU;
  }

  public void transitionTo(MenuState newState){
    this.state = newState; // no transition validation?
  }

  public MenuState getState(){
    return state;
  }
}

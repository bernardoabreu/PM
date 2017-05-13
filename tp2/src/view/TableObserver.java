package view;

import model.Card;
import model.Table;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by math on 5/12/17.
 */
public class TableObserver implements Observer {
    private TableObserver(){ }

    private static class TableObserverHolder{
        private static final TableObserver INSTANCE = new TableObserver();
    }

    public static TableObserver getInstance(){
        return TableObserverHolder.INSTANCE;
    }
    @Override
    public void update(Observable observable, Object o) {
        TerminalDisplay.getInstance().printString("As cartas na mesa são: ");
        Table table = (Table) observable;
        String message = "";
        for(Card card : table){
            message += card + "  ";
        }
        TerminalDisplay.getInstance().printString(message);
    }
}

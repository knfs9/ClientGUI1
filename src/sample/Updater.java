package sample;

import javafx.application.Platform;
import javafx.scene.control.TableView;

import java.util.Date;
import java.util.TimerTask;

/**
 * Created by RTCCD on 30.10.2014.
 */
public class Updater extends TimerTask {
    Date now = new Date();
    Controller contr;
    Updater(Controller contr){
        this.contr = contr;
    }
    @Override
    public void run(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    contr.updateTable();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}

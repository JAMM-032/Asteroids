import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;


public class menuBar {

    private String VERSION;

    public menuBar(String VERSION){
        this.VERSION = VERSION;
    }

    public void makeMenuBar(Pane parent) {

        MenuBar menubar = new MenuBar();
        parent.getChildren().add(menubar);

        Menu fileMenu = new Menu("File");

        MenuItem quitItem = new MenuItem("Quit");
        quitItem.setOnAction(this::quitAction);
        quitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));

        fileMenu.getItems().addAll(quitItem);

        /// //// //// //// ////
        Menu helpMenu = new Menu("Help");

        MenuItem aboutItem = new MenuItem("About Game");
        aboutItem.setOnAction(this::aboutAction);

        MenuItem controlItem = new MenuItem("Controls");
        controlItem.setOnAction(this::controlAction);

        helpMenu.getItems().addAll(aboutItem, controlItem);
        menubar.getMenus().addAll(fileMenu, helpMenu);

    }

    private void quitAction(ActionEvent event){
        System.exit(0);
    }

    private void aboutAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Software");
        alert.setHeaderText("Asteroidz");

        alert.setContentText(VERSION + " A game based on the Atari Classic \n" +
                " created by : Aria\t\tDmitrij\t\tJanit");

        alert.showAndWait();
    }

    private void controlAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Controls");
        alert.setHeaderText("controls");
        alert.setContentText("W/S (up/down arrow keys): Spaceship Thrust & Reverse \n" +
                "A/D (right/left arrow keys) : Rotation of Spaceship \nSpacebar : Fire Button \n");
        alert.showAndWait();
    }

}